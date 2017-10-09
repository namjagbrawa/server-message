package com.bingo.server.game.provider;

import com.bingo.server.msg.RESP;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface StartGameProvider {

    RESP.DdzConfirmRuleResponse confirmRule(long userId, String deskName, String rule);

    RESP.DdzCheckInDeskResponse checkInDesk(long userId);

    RESP.EnterDeskResponse enterDesk(long userId, int deskNo);

    RESP.ReconnectDeskResponse reconnect(long userId, long deskId);

    void ready(long userId, long deskId);

}
