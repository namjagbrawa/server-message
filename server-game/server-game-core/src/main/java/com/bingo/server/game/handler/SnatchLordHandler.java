package com.bingo.server.game.handler;

import com.bingo.server.game.bean.Desk;

/**
 * Created by ZhangGe on 2017/7/27.
 */
public interface SnatchLordHandler {

    void nextMark(Desk desk);

    void mark(Desk desk, int mark, int chairId);
}
