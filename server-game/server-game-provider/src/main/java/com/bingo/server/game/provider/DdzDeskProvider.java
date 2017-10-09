package com.bingo.server.game.provider;

import com.bingo.server.database.model.DdzDesk;
import com.bingo.server.game.provider.bean.DdzRule;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/19.
 */
public interface DdzDeskProvider {

    DdzDesk getDeskById(long deskId);

    DdzDesk getDeskByDeskNo(int deskNo);

    int getDeskNo(long deskId);

    DdzDesk newDesk(String name, int roomCard, DdzRule ddzRule);

    void update(DdzDesk ddzDesk);

}
