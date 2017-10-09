package com.bingo.server.game.handler;

import com.bingo.server.game.bean.Desk;

/**
 * Created by ZhangGe on 2017/7/27.
 */
public interface CallLordHandler {

    /**
     * 发完牌的叫地主
     * @param desk
     */
    void startCall(Desk desk);

    /**
     * 叫地主完的叫地主
     * @param desk
     */
    void nextCall(Desk desk);
}
