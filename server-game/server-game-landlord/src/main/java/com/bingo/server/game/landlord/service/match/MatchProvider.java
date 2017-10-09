package com.bingo.server.game.landlord.service.match;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.template.ObserveBaseServiceInterface;
import com.bingo.server.msg.Entity;
import com.bingo.server.msg.ServerMessage;
import com.bingo.server.game.landlord.entity.po.RoleGameInfo;

public interface MatchProvider extends ObserveBaseServiceInterface {
	/**
	 * 创建游戏
	 * 
	 * @param role
	 * @return
	 * @author wcy 2017年5月25日
	 */
	ServerMessage.SC createRoom(Role role, Entity.GameConfig gameConfig);

	/**
	 * 加入游戏
	 * 
	 * @param role
	 * @param lockString
	 * @return
	 * @author wcy 2017年5月25日
	 */
	 ServerMessage.SC joinGame(Role role, String lockString);

	/**
	 * 获得游戏玩家标识符
	 * 
	 * @param gameId
	 * @param roleId
	 * @return
	 * @author wcy 2017年5月25日
	 */
	String getGameRoleId(long gameId, long roleId);

	void match(Role role);

	void matchAI(Role role);

	Role getRoleFromRoleGameInfo(RoleGameInfo info);

}
