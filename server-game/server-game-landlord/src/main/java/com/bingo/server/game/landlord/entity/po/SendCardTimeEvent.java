package com.bingo.server.game.landlord.entity.po;

import com.bingo.server.game.landlord.scheduler.DefaultTimeEvent;

public abstract class SendCardTimeEvent extends DefaultTimeEvent {

	private long gameId;
	private int sendCardCount;

	public int getSendCardCount() {
		return sendCardCount;
	}

	public void setSendCardCount(int sendCardCount) {
		this.sendCardCount = sendCardCount;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
}
