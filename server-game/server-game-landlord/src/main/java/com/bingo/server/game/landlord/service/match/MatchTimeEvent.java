package com.bingo.server.game.landlord.service.match;


import com.bingo.server.game.landlord.scheduler.TimeEvent;

public abstract class MatchTimeEvent implements TimeEvent {

	private MatchRule matchRule;
	private int endTime;

	public MatchTimeEvent(MatchRule matchRule) {
		this.matchRule = matchRule;
	}

	@Override
	public int getEndTime() {
		return endTime;
	}

	@Override
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	@Override
	public void update(TimeEvent timeEvent) {
		this.outOfTime(matchRule);
	}

	public abstract void outOfTime(MatchRule matchRule);

}
