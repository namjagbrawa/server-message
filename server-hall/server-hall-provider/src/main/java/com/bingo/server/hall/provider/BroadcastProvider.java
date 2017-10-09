package com.bingo.server.hall.provider;

import com.bingo.server.database.model.HallBroadcast;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/24.
 */
public interface BroadcastProvider {

    HallBroadcast addBroadcast(int order, String content, String detail);

    List<HallBroadcast> getBroadcasts();

    void updateBroadcast(HallBroadcast hallBroadcast);

    HallBroadcast getBroadcast(long id);

    String getBroadcast();
}
