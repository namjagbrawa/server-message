package com.bingo.server.game.landlord.service.match;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.RoleMapper;
import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.cache.GameCache;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.game.landlord.entity.bo.Game;
import com.bingo.server.game.landlord.entity.po.RoleGameInfo;
import com.bingo.server.game.landlord.entity.po.RoleMatchRule;
import com.bingo.server.game.landlord.service.MoneyExchangeService;
import com.bingo.server.game.landlord.service.RoleService;
import com.bingo.server.game.landlord.service.SendToService;
import com.bingo.server.game.landlord.template.ObserveBaseService;
import com.bingo.server.game.landlord.util.Tool;
import com.bingo.server.game.landlord.util.base.TimeUtils;
import com.bingo.server.msg.Entity;
import com.bingo.server.msg.Error;
import com.bingo.server.msg.Match;
import com.bingo.server.msg.ServerMessage;
import com.bingo.server.utils.Check;
import com.sun.xml.internal.xsom.impl.util.SchemaWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class MatchService extends ObserveBaseService implements MatchProvider {

	@Autowired
	private MatchModelService matchModelService;

	@Autowired
	private MoneyExchangeService moneyExchangeService;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private RoleService roleService;

	@Autowired
	private SendToService sendToService;

	@Override
	public void initService() {
		matchModelService.setMatchHandler(new MatchHandler() {

			@Override
			public void outOfTime(MatchRule matchRule) {
				RoleMatchRule roleMatchRule = (RoleMatchRule) matchRule;
				long roleId = roleMatchRule.getRoleId();
				// if (roleMatchRule.isAi()) {
				// Game game = createGame(roleId, gameConfig);
				// addAccountRole(game, roleId);
				// int maxCount = game.getMaxRoleCount();
				// for (int i = game.getRoleIdMap().size(); i < maxCount; i++) {
				// addAIRole(game);
				// }
				// }

				System.out.println(TimeUtils.getNowTime() + " out of Time");
			}

			@Override
			public void matchSuccess(Map<String, MatchRule> matchMap) {
				List<RoleMatchRule> list = new ArrayList<>(matchMap.size());
				for (MatchRule matchRule : matchMap.values())
					list.add((RoleMatchRule) matchRule);

				Collections.sort(list);
				Entity.GameConfig config = Entity.GameConfig.newBuilder().setDi(3).setMingpai(true).setMoguai(true).setRound(1)
						.build();
				Game game = createGame(list.get(0).getRoleId(), config);
				game.setGameType(Entity.GameType.GAME_TYPE_MATCH);

				for (MatchRule matchRule : matchMap.values()) {
					RoleMatchRule rule = (RoleMatchRule) matchRule;

					addAccountRole(game, rule.getRoleId());
				}

			}

			@Override
			public boolean checkMatchRule(MatchRule rule1, MatchRule rule2) {
				RoleMatchRule roleRule1 = (RoleMatchRule) rule1;
				RoleMatchRule roleRule2 = (RoleMatchRule) rule2;

				return roleRule1.getMaxCount() == roleRule2.getMaxCount();
			}

			@Override
			public boolean checkArriveMaxCount(MatchRule rule, Map<String, MatchRule> matchRuleMap) {
				RoleMatchRule roleRule = (RoleMatchRule) rule;

				return matchRuleMap.size() == roleRule.getMaxCount();
			}
		});

		matchModelService.initService();
	}

	@Override
	public ServerMessage.SC createRoom(Role role, Entity.GameConfig gameConfig) {
		if (!checkConfig(gameConfig)) {
			return ServerMessage.SC.newBuilder()
					.setMatchCreateGameResponse(
							Match.MatchCreateGameResponse.newBuilder().setErrorCode(Error.ErrorCode.CREATE_FAILED.getNumber()))
					.build();
		}
		if (!moneyExchangeService.exchangeMoney(role, gameConfig.getRound() / 6 * 20, true)) {
			if (role.getSpecialMoney() - gameConfig.getRound() / 6 * 20 < 0) {
				return ServerMessage.SC.newBuilder()
						.setMatchCreateGameResponse(
								Match.MatchCreateGameResponse.newBuilder().setErrorCode(Error.ErrorCode.NO_MONEY.getNumber()))
						.build();
			}
		}
		Game game = this.createGame(role.getId(), gameConfig);
		RoleGameInfo roleGameInfo = game.getRoleIdMap().get(this.getGameRoleId(game.getGameId(), role.getId()));

		Entity.GameRoleData myGameRoleData = this.parseGameRoleData(roleGameInfo, game.getGameId());
		return ServerMessage.SC.newBuilder()
				.setMatchCreateGameResponse(Match.MatchCreateGameResponse.newBuilder().setId(game.getLockString())
						.setGameRoleData(myGameRoleData).setMoguai(game.getGameConfig().getMoguai()).setRoomType(Entity.GameType.GAME_TYPE_FRIEND.getNumber()))
				.build();
	}

	/**
	 * 创建游戏
	 * 
	 * @param roleId
	 * @return
	 * @author wcy 2017年5月26日
	 */
	private Game createGame(long roleId, Entity.GameConfig gameConfig) {
		Game game = new Game();
		long gameId = IDGen.getId();
		game.setGameId(gameId);
		game.setGameType(Entity.GameType.GAME_TYPE_FRIEND);
		game.setGameState(Entity.GameState.GAME_STATE_PREPARE);

		game.setMasterRoleId(roleId);
		game.setLockString(this.getLockString());
		game.setMaxRoleCount(3);

		this.addAccountRole(game, roleId);

		game.setGameConfig(gameConfig);
		game.setRound(gameConfig.getRound());

		GameCache.getGameMap().put(gameId, game);
		GameCache.getGameLockStringMap().put(game.getLockString(), gameId);

		return game;
	}

	/**
	 * 加入玩家
	 * 
	 * @param game
	 * @param roleId
	 * @author wcy 2017年5月26日
	 */
	private void addAccountRole(Game game, long roleId) {
		String gameRoleId = getGameRoleId(game.getGameId(), roleId);

		addRole(game, roleId, gameRoleId);
	}

	/**
	 * 加入ai
	 * 
	 * @param game
	 * @author wcy 2017年5月26日
	 */
	private String addAIRole(Game game) {
		String gameRoleId = this.getAIGameRoleId(game.getGameId());

		addRole(game, 0, gameRoleId);
		// 机器人自动准备完毕
		game.getRoleIdMap().get(gameRoleId).ready = true;
		return gameRoleId;
	}

	private void addRole(Game game, long roleId, String gameRoleId) {
		RoleGameInfo roleGameInfo = this.createRoleGameInfo(roleId, gameRoleId);
		// roleGameInfo.seatIndex = game.getRoleIdMap().size();
		if (roleId != 0) {
			Role role = (Role) RoleCache.getRoleById(roleId);
			role.setGameId(game.getGameId());
		}
		game.getRoleIdMap().put(roleGameInfo.gameRoleId, roleGameInfo);
		game.getRoleIdList().add(gameRoleId);
	}

	/**
	 * 创建用户在游戏中的数据结构
	 * 
	 * @param roleId
	 * @param gameRoleId
	 * @return
	 * @author wcy 2017年5月25日
	 */
	private RoleGameInfo createRoleGameInfo(long roleId, String gameRoleId) {
		RoleGameInfo roleGameInfo = new RoleGameInfo();
		roleGameInfo.roleId = roleId;
		roleGameInfo.gameRoleId = gameRoleId;

		return roleGameInfo;
	}
	@Override
	public Role getRoleFromRoleGameInfo(RoleGameInfo info) {
		long roleId = info.roleId;
		if(roleId == 0){
			Role role = new Role();

			role.setUsername("ROBOT"+info.roleId);
			return role;
		}
		return (Role) RoleCache.getRoleById(roleId);
	}

	@Override
	public ServerMessage.SC joinGame(Role role, String lockString) {
		Long gameId = GameCache.getGameLockStringMap().get(lockString);
		System.out.println("gameid:" + gameId);
		if (gameId == null) {
			return ServerMessage.SC.newBuilder()
					.setMatchJoinGameResponse(
							Match.MatchJoinGameResponse.newBuilder().setErrorCode(Error.ErrorCode.GAME_JOIN_ERROR.getNumber()))
					.build();
		}

		Game game = GameCache.getGameMap().get(gameId);
		System.out.println("game:" + game);
		if (game == null) {
			return ServerMessage.SC.newBuilder()
					.setMatchJoinGameResponse(
							Match.MatchJoinGameResponse.newBuilder().setErrorCode(Error.ErrorCode.GAME_JOIN_ERROR.getNumber()))
					.build();
		}
		String targetLock = game.getLockString();
		// 如果锁相同则可以进
		if (!targetLock.equals(lockString)) {
			return ServerMessage.SC.newBuilder()
					.setMatchJoinGameResponse(
							Match.MatchJoinGameResponse.newBuilder().setErrorCode(Error.ErrorCode.MATCH_ERROR_LOCK.getNumber()))
					.build();
		}

		if (game.getRoleIdMap().size() >= game.getMaxRoleCount()) {
			return ServerMessage.SC.newBuilder()
					.setMatchJoinGameResponse(
							Match.MatchJoinGameResponse.newBuilder().setErrorCode(Error.ErrorCode.MATCH_MAX_ROLE_COUNT.getNumber()))
					.build();
		}

		this.addAccountRole(game, role.getId());

		RoleGameInfo roleGameInfo = game.getRoleIdMap().get(this.getGameRoleId(game.getGameId(), role.getId()));

		Entity.GameRoleData myGameRoleData = this.parseGameRoleData(roleGameInfo, game.getGameId());
		ServerMessage.SC scJoinGame = ServerMessage.SC.newBuilder().setSCMatchJoinGame(Match.SCMatchJoinGame.newBuilder().setGameRoleData(myGameRoleData))
				.build();
		// 通知其他人加入房间
		for (RoleGameInfo info : game.getRoleIdMap().values()) {
			if (role.getId() == info.roleId)
				continue;
			sendToService.send(info.roleId, scJoinGame);
		}

		List<Entity.GameRoleData> gameRoleDataList = new ArrayList<>(game.getRoleIdMap().size());
		for (RoleGameInfo info : game.getRoleIdMap().values()) {
			Entity.GameRoleData gameRoleData = this.parseGameRoleData(info, game.getGameId());
			gameRoleDataList.add(gameRoleData);
		}
		return ServerMessage.SC.newBuilder().setMatchJoinGameResponse(Match.MatchJoinGameResponse.newBuilder()
				.addAllGameRoleData(gameRoleDataList).setSeated(myGameRoleData.getSeated()).setId(lockString).setMoguai(game.getGameConfig().getMoguai()).setRoomType(Entity.GameType.GAME_TYPE_FRIEND.getNumber()))
				.build();
	}

	@Override
	public void match(Role role) {
		RoleMatchRule matchRule = new RoleMatchRule();
		matchRule.setId(IDGen.getId() + "_" + role.getId());
		matchRule.setWaitTime(60);
		matchRule.setAi(false);
		matchRule.setMatchTime(TimeUtils.getNowTime());
		matchModelService.matchRole(matchRule);
	}

	@Override
	public void matchAI(Role role) {
		long roleId = role.getId();
		Entity.GameConfig config = Entity.GameConfig.newBuilder().setDi(1).setMingpai(true).setMoguai(true).setRound(1).build();
		Game game = createGame(roleId, config);
		RoleGameInfo tRoleGameInfo = game.getRoleIdMap().get(this.getGameRoleId(game.getGameId(), role.getId()));

		Entity.GameRoleData myGameRoleData = this.parseGameRoleData(tRoleGameInfo, game.getGameId());
		sendToService.send(role.getId(),
						ServerMessage.SC.newBuilder()
								.setMatchCreateGameResponse(Match.MatchCreateGameResponse.newBuilder()
										.setId(game.getLockString()).setGameRoleData(myGameRoleData)
										.setMoguai(game.getGameConfig().getMoguai()).setRoomType(Entity.GameType.GAME_TYPE_MATCH.getNumber()))
								.build());
		int maxCount = game.getMaxRoleCount();
		for (int i = game.getRoleIdMap().size(); i < maxCount; i++) {
			String gameRoleId = addAIRole(game);

			RoleGameInfo info = game.getRoleIdMap().get(gameRoleId);
			System.out.println(info);
			int index = game.getRoleIdList().indexOf(gameRoleId);
			Entity.GameRoleData AIGameRoleData = Entity.GameRoleData.newBuilder().setGameRoleId(info.gameRoleId).setReady(info.ready)
					.setSeated(index).setName("ROBOT" + info.gameRoleId).build();

			ServerMessage.SC scJoinGame = ServerMessage.SC.newBuilder()
					.setSCMatchJoinGame(Match.SCMatchJoinGame.newBuilder().setGameRoleData(AIGameRoleData)).build();
			sendToService.send(role.getId(), scJoinGame);
		}

	}

	private Entity.GameRoleData parseGameRoleData(RoleGameInfo info, long gameId) {
		Role role = roleMapper.selectByPrimaryKey(info.roleId);
		String name = role != null ? role.getUsername() : "";

		Game game = GameCache.getGameMap().get(gameId);
		int index = game.getRoleIdList().indexOf(info.gameRoleId);
		return Entity.GameRoleData.newBuilder().setGameRoleId(info.gameRoleId).setReady(info.ready).setSeated(index)
				.setName(name).setHeadImgUrl(role.getHeadImgUrl()).setMoney(role.getMoney()).build();
	}

	/**
	 * 游戏内使用的玩家id
	 * 
	 * @param gameId
	 * @param roleId
	 * @return
	 * @author wcy 2017年5月24日
	 */
	@Override
	public String getGameRoleId(long gameId, long roleId) {
		return gameId + "_" + roleId;
	}

	/**
	 * 
	 * @param gameId
	 * @return
	 * @author wcy 2017年5月24日
	 */
	private String getAIGameRoleId(long gameId) {
		Game game = GameCache.getGameMap().get(gameId);
		int aiCount = 0;
		for (RoleGameInfo roleGameInfo : game.getRoleIdMap().values()) {
			if (roleGameInfo.roleId == 0) {
				aiCount++;
			}
		}
		return gameId + "_0_" + aiCount;
	}

	private String getLockString() {
		return /* "1980" */"111111";
	}

	public boolean checkConfig(Entity.GameConfig gameConfig) {
		int[] di = { 1, 2, 3, 5 };
		int[] round = {2, 6, 12, 18, 24 };
		if (Tool.indexOf(di, gameConfig.getDi()) == -1 || Tool.indexOf(round, gameConfig.getRound()) == -1) {
			return false;
		}
		return true;
	}

}
