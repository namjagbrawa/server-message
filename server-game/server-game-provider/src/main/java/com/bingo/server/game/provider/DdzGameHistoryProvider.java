package com.bingo.server.game.provider;

import com.bingo.server.database.model.DdzGameHistory;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/26.
 */
public interface DdzGameHistoryProvider {

    List<DdzGameHistory> getLastestOperation(long deskId, int round, int limit);

}
