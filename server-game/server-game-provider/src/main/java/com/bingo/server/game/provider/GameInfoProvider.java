package com.bingo.server.game.provider;

import com.bingo.server.msg.RESP;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface GameInfoProvider {

    RESP.DdzGetRuleResponse getDdzRule();

    /**
     * 获取牌桌信息
     * @param deskId
     */
    void getDeskInfo(long deskId) ;

    /**
     * 获取某用户信息
     * @param deskId
     * @param userId
     */
    void getUserInfo(long deskId, long userId) ;

}
