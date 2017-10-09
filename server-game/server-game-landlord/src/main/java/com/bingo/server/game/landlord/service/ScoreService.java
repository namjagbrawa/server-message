package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.cache.GameCache;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.game.landlord.entity.bo.Game;
import com.bingo.server.game.landlord.entity.po.RoleGameInfo;
import com.bingo.server.game.landlord.constant.FightConstant;
import com.bingo.server.game.landlord.template.ObserveBaseService;
import com.bingo.server.game.landlord.template.Observer;
import com.bingo.server.msg.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService extends ObserveBaseService implements ScoreProvider {

    @Autowired
    private FightProvider fightService;

    @Override
    public void initService() {
        fightService.addObserver(this);
    }

    @Override
    public void update(Observer observer, String msg, Object... args) {
        if (msg.equals(FightConstant.SEND_CARD)) {
            /*long gameId = (int) args[0];
			String gameRoleId = (String) args[1];
			CardList sendCardList = (CardList) args[2];

			this.updateScore(gameId, gameRoleId);*/
        }
    }

    @Override
    public void updateScore(long gameId, String gameRoleId) {
        // 每次出牌后,计算分数
        Game game = GameCache.getGameMap().get(gameId);
        boolean landLordWin = gameRoleId.equals(game.getLandlordGameRoleId());

        int mark = (int) (game.getCallLandlordScore() * game.getGameConfig().getDi() * Math.pow(2, game.getMultiple())) * (landLordWin ? (game.isLandLordSpring() ? 2 : 1) : (game.isFarmerSpring() ? 2 : 1));
        System.out.println("Mark:" + mark + "Times:" + game.getMultiple());
        for (RoleGameInfo info : game.getRoleIdMap().values()) {
            info.currentMark = mark * (game.getLandlordGameRoleId().equals(info.gameRoleId) ? (landLordWin ? 2 : -2) : (landLordWin ? -1 : 1));
            info.allMark += info.currentMark;
            if (game.getGameType() == Entity.GameType.GAME_TYPE_MATCH) {
                Role role = RoleCache.getRoleMap().get(info.roleId);
                if (role != null) {
                    role.setMoney(role.getMoney() + info.currentMark);
                }
            }
        }
    }
}
