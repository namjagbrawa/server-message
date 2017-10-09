package com.bingo.server.game.provider;

import com.bingo.server.database.model.DdzGameScore;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/24.
 */
public interface ScoreProvider {

    DdzGameScore addGameScore(long deskId, int round, long userId1, long userId2, long userId3, int score1, int score2, int score3, long winner);

    int getRoundNumber(long userId);

    List<DdzGameScore> getScoreByUser(long userId);

    Map<Long, Integer> getScoreMap(List<Long> userIds);

    DdzGameScore getScore(long scoreId);

}
