package com.bingo.server.game.landlord.entity.bo;

import com.bingo.server.game.landlord.entity.po.CardRecord;
import com.bingo.server.game.landlord.entity.po.RoleGameInfo;
import com.bingo.server.game.landlord.entity.po.cardlist.CardList;
import com.bingo.server.msg.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game implements Serializable{

	private static final long serialVersionUID = -6854593614163295186L;

	private long gameId;
	// 玩家id集合
	private Map<String, RoleGameInfo> roleIdMap = new LinkedHashMap<>();
	// 房主id
	private long masterRoleId;
	// 房间锁
	private String lockString;
	// 最大玩家数量
	private int maxRoleCount;
	// 游戏开始
	private Entity.GameState gameState;
	// 游戏类型
	private Entity.GameType gameType;
	// 游戏配置
	private Entity.GameConfig gameConfig;
	// 地主牌
	private List<Integer> landlordCards = new ArrayList<>();
	// 在线玩家数量
	private int onlineRoleCount;
	// 玩家id列表，用于换人
	private List<String> roleIdList = new ArrayList<>();
	// 当前玩家id
	private int currentRoleIdIndex;
	// 出牌的类型
	private CardList lastCardList;
	// 不要
	private int passCount;
	// 地主的玩家id
	private String landlordGameRoleId;
	// 游戏倍数
	private int multiple;
	// 叫地主的分数
	private int callLandlordScore;
	// 叫分的次数
	private int callLandlordCount;
	// 出牌计数
	private int sendCardCount;
	// 出牌的时间戳
	private int sendCardTime;
	
	private boolean mingPaiState = false;
	
	private boolean farmerSpring = true;
	
	private boolean LandLordSpring = true;
	
	private int bomb;
	
	private boolean moGuai;
	
	private int round;
	
	private List<List<List<CardRecord>>> records = new ArrayList<>();

	public int getOnlineRoleCount() {
		return onlineRoleCount;
	}

	public void setOnlineRoleCount(int onlineRoleCount) {
		this.onlineRoleCount = onlineRoleCount;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getGameId() {
		return gameId;
	}

	public Map<String, RoleGameInfo> getRoleIdMap() {
		return roleIdMap;
	}

	public long getMasterRoleId() {
		return masterRoleId;
	}

	public void setMasterRoleId(long masterRoleId) {
		this.masterRoleId = masterRoleId;
	}

	public String getLockString() {
		return lockString;
	}

	public void setLockString(String lockString) {
		this.lockString = lockString;
	}

	public int getMaxRoleCount() {
		return maxRoleCount;
	}

	public void setMaxRoleCount(int maxRoleCount) {
		this.maxRoleCount = maxRoleCount;
	}

	public Entity.GameType getGameType() {
		return gameType;
	}

	public void setGameType(Entity.GameType gameType) {
		this.gameType = gameType;
	}

	public Entity.GameState getGameState() {
		return gameState;
	}

	public void setGameState(Entity.GameState gameState) {
		this.gameState = gameState;
	}

	public Entity.GameConfig getGameConfig() {
		return gameConfig;
	}

	public void setGameConfig(Entity.GameConfig gameConfig) {
		this.gameConfig = gameConfig;
	}

	public List<Integer> getLandlordCards() {
		return landlordCards;
	}

	public List<String> getRoleIdList() {
		return roleIdList;
	}

	public int getCurrentRoleIdIndex() {
		return currentRoleIdIndex;
	}

	public void setCurrentRoleIdIndex(int currentRoleIdIndex) {
		this.currentRoleIdIndex = currentRoleIdIndex;
	}

	public CardList getLastCardList() {
		return lastCardList;
	}

	public void setLastCardList(CardList lastCardList) {
		this.lastCardList = lastCardList;
	}

	public int getPassCount() {
		return passCount;
	}

	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}

	public void setLandlordGameRoleId(String landlordGameRoleId) {
		this.landlordGameRoleId = landlordGameRoleId;
	}

	public String getLandlordGameRoleId() {
		return landlordGameRoleId;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public int getCallLandlordScore() {
		return callLandlordScore;
	}

	public void setCallLandlordScore(int callLandlordScore) {
		this.callLandlordScore = callLandlordScore;
	}

	public int getCallLandlordCount() {
		return callLandlordCount;
	}

	public void setCallLandlordCount(int callLandlordCount) {
		this.callLandlordCount = callLandlordCount;
	}

	public int getSendCardCount() {
		return sendCardCount;
	}

	public void setSendCardCount(int sendCardCount) {
		this.sendCardCount = sendCardCount;
	}

	public int getSendCardTime() {
		return sendCardTime;
	}

	public void setSendCardTime(int sendCardTime) {
		this.sendCardTime = sendCardTime;
	}

	public boolean isMingPaiState() {
		return mingPaiState;
	}

	public void setMingPaiState(boolean mingPaiState) {
		this.mingPaiState = mingPaiState;
	}

	public boolean isFarmerSpring() {
		return farmerSpring;
	}

	public void setFarmerSpring(boolean farmerSpring) {
		this.farmerSpring = farmerSpring;
	}

	public boolean isLandLordSpring() {
		return LandLordSpring;
	}

	public void setLandLordSpring(boolean landLordSpring) {
		LandLordSpring = landLordSpring;
	}

	public int getBomb() {
		return bomb;
	}

	public void setBomb(int bomb) {
		this.bomb = bomb;
	}

	public boolean isMoGuai() {
		return moGuai;
	}

	public void setMoGuai(boolean moGuai) {
		this.moGuai = moGuai;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public List<List<List<CardRecord>>> getRecords() {
		return records;
	}
	

}
