package com.bingo.server.game.landlord.entity.po;


import com.bingo.server.game.landlord.scheduler.DefaultTimeEvent;

public abstract class CallLandLordTimeEvent extends DefaultTimeEvent {
	private long gameId;
	private String gameRoleId;
	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public String getGameRoleId() {
		return gameRoleId;
	}

	public void setGameRoleId(String gameRoleId) {
		this.gameRoleId = gameRoleId;
	}
	
	
}
