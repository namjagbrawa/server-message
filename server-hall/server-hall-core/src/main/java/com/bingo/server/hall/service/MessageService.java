package com.bingo.server.hall.service;

import com.bingo.server.database.dao.MessageMapper;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.DdzGameScore;
import com.bingo.server.database.model.Message;
import com.bingo.server.database.model.MessageExample;
import com.bingo.server.hall.bean.MessageType;
import com.bingo.server.hall.provider.MessageProvider;
import com.bingo.server.game.provider.ScoreProvider;
import com.bingo.server.msg.BASE;
import com.bingo.server.msg.RESP;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/24.
 */
@Service
public class MessageService implements MessageProvider {
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private ScoreProvider scoreProvider;
    @Autowired
    private CuUserProvider cuUserProvider;

    @Override
    public RESP.GetMessageResponse getMessages(long userId) {
        MessageExample messageExample = new MessageExample();
        messageExample.createCriteria().andToUserIdEqualTo(userId);
        List<Message> messages = messageMapper.selectByExample(messageExample);

        RESP.GetMessageResponse.Builder response = RESP.GetMessageResponse.newBuilder();
        if (Check.isNull(messages)) {
            return response.build();
        }

        for (Message message : messages) {
            MessageType type = MessageType.valueOf(message.getMessageType());
            switch (type) {
                case system:
                    BASE.SystemMessage.Builder system = BASE.SystemMessage.newBuilder();
                    system.setContent(message.getContent());
                    system.setMessageType(type.name());
                    system.setRead(Boolean.valueOf(message.getIsRead()));
                    system.setTime(message.getCreateTime().getTime());
                    system.setTitle(message.getTitle());
                    response.addSystem(system);
                    break;
                case score:
                    BASE.ScoreMessage.Builder score = BASE.ScoreMessage.newBuilder();
                    score.setMessageType(type.name());
                    score.setTitle(message.getTitle());
                    score.setTime(message.getCreateTime().getTime());
                    score.setScoreId(message.getScoreId()); // 即时获取牌局详细信息
                    DdzGameScore gameScore = scoreProvider.getScore(message.getScoreId());
                    if (Check.isNull(gameScore)) {
                        continue;
                    }
                    CuUser user1 = cuUserProvider.getUserById(gameScore.getUserId1());
                    CuUser user2 = cuUserProvider.getUserById(gameScore.getUserId2());
                    CuUser user3 = cuUserProvider.getUserById(gameScore.getUserId3());
                    score.addScoreRecord(getScoreRecordMessage(user1, gameScore.getUserScore1(), gameScore.getWinner()));
                    score.addScoreRecord(getScoreRecordMessage(user2, gameScore.getUserScore2(), gameScore.getWinner()));
                    score.addScoreRecord(getScoreRecordMessage(user3, gameScore.getUserScore3(), gameScore.getWinner()));
                    break;
                // 暂时没有用户消息获取
            }
        }
        return response.build();
    }

    private BASE.ScoreRecordMessage getScoreRecordMessage(CuUser cuUser, int score, long winUserId) {
        BASE.ScoreRecordMessage.Builder scoreRecord = BASE.ScoreRecordMessage.newBuilder();
        scoreRecord.setNickName(cuUser.getNickName());
        scoreRecord.setHeaderUrl(cuUser.getHeadImgUrl());
        scoreRecord.setWin(cuUser.getId() == winUserId ? true : false);
        scoreRecord.setScore(score);
        return scoreRecord.build();
    }

}
