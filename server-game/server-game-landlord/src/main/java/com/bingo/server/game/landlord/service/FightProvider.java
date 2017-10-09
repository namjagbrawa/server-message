package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.template.ObserveBaseServiceInterface;
import com.bingo.server.msg.ServerMessage;

import java.util.List;

public interface FightProvider extends ObserveBaseServiceInterface {
    void readyGame(Role role);

    ServerMessage.SC exitGame(Role role);

    ServerMessage.SC agreeExit(Role role, boolean agree);

    /**
     * 真实玩家出牌
     *
     * @param role
     * @param paiList
     */
    void sendCard(Role role, List<Integer> paiList);

    /**
     * 获得最好的牌
     *
     * @param role
     * @author wcy 2017年5月31日
     */
    void getBestCardList(Role role);

    /**
     * 分牌
     *
     * @param gameId
     * @author wcy 2017年5月31日
     */
    void dispatchCard(long gameId);

    /**
     * 叫地主
     *
     * @param role
     * @param fen
     * @author wcy 2017年5月31日
     */
    void callLandlord(Role role, int fen);

    ServerMessage.SC recommandCardList(Role role);

    void mingPai(Role role);

    ServerMessage.SC getLastRecord(long gameId);
}
