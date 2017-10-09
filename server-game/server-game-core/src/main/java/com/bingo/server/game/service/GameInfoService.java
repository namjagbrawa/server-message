package com.bingo.server.game.service;

import com.bingo.server.database.model.DdzDesk;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.ddz.service.DdzRuleService;
import com.bingo.server.game.provider.*;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.RESP;
import com.bingo.server.utils.Check;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class GameInfoService implements GameInfoProvider{
    private final Logger logger = LoggerFactory.getLogger(GameInfoService.class);

    @Autowired
    private DdzRuleService ddzRuleService;
    @Autowired
    private DdzDeskProvider ddzDeskProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private ScoreProvider scoreProvider;

    @Override
    public RESP.DdzGetRuleResponse getDdzRule() {
        byte[] bytes = ddzRuleService.getDdzGetRuleResponse();
        if (Check.isNull(bytes)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "斗地主规则获取错误");
        }
        try {
            RESP.DdzGetRuleResponse ddzGetRuleResponse = RESP.DdzGetRuleResponse.parseFrom(bytes);
            return ddzGetRuleResponse;
        } catch (InvalidProtocolBufferException e) {
            logger.error("斗地主规则解析为消息错误 bytes : " + bytes, e);
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "斗地主规则解析错误");
        }
    }

    @Override
    public void getDeskInfo(long deskId) {

    }

    @Override
    public void getUserInfo(long deskId, long userId) {

    }


}
