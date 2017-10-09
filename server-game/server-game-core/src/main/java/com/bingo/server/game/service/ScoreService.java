package com.bingo.server.game.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.DdzGameScoreMapper;
import com.bingo.server.database.model.DdzGameScore;
import com.bingo.server.database.model.DdzGameScoreExample;
import com.bingo.server.game.provider.ScoreProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/24.
 */
@Service
public class ScoreService implements ScoreProvider {

    private final Logger logger = LoggerFactory.getLogger(ScoreService.class);

    @Autowired
    private DdzGameScoreMapper ddzGameScoreMapper;

    public DdzGameScore addGameScore(long deskId, int round, long userId1, long userId2, long userId3, int score1, int score2, int score3, long winner) {
        DdzGameScore ddzUserScore = new DdzGameScore();
        ddzUserScore.setId(IDGen.getId());
        ddzUserScore.setUserId1(userId1);
        ddzUserScore.setUserId2(userId2);
        ddzUserScore.setUserId3(userId3);
        ddzUserScore.setUserScore1(score1);
        ddzUserScore.setUserScore2(score2);
        ddzUserScore.setUserScore3(score3);
        ddzUserScore.setDeskId(deskId);
        ddzUserScore.setCreateTime(new Date());
        ddzUserScore.setRound(round);
        ddzUserScore.setWinner(winner);
        ddzGameScoreMapper.insert(ddzUserScore);
        return ddzUserScore;
    }

    public int getRoundNumber(long userId) {
        DdzGameScoreExample ddzGameScoreExample = new DdzGameScoreExample();
        ddzGameScoreExample.or().andUserId1EqualTo(userId);
        ddzGameScoreExample.or().andUserId2EqualTo(userId);
        ddzGameScoreExample.or().andUserId3EqualTo(userId);
        return (int) ddzGameScoreMapper.countByExample(ddzGameScoreExample);
    }

    @Override
    public List<DdzGameScore> getScoreByUser(long userId) {
        DdzGameScoreExample ddzGameScoreExample = new DdzGameScoreExample();
        ddzGameScoreExample.or().andUserId1EqualTo(userId);
        ddzGameScoreExample.or().andUserId2EqualTo(userId);
        ddzGameScoreExample.or().andUserId3EqualTo(userId);
        return ddzGameScoreMapper.selectByExample(ddzGameScoreExample);
    }

    @Override
    public Map<Long, Integer> getScoreMap(List<Long> userIds) {
        Map<Long, Integer> userScoreMap = new HashMap<>();
        DdzGameScoreExample ddzGameScoreExample = new DdzGameScoreExample();
        for (Long userId : userIds) {
            ddzGameScoreExample.createCriteria().andWinnerEqualTo(userId);
            long count = ddzGameScoreMapper.countByExample(ddzGameScoreExample);
            userScoreMap.put(userId, (int) count);
        }
        return userScoreMap;
    }

    @Override
    public DdzGameScore getScore(long scoreId) {
        return ddzGameScoreMapper.selectByPrimaryKey(scoreId);
    }

}
