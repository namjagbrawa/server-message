package com.bingo.server.game.landlord.entity.po;


import com.bingo.server.game.landlord.service.match.MatchRule;

public class RoleMatchRule extends MatchRule implements Comparable<RoleMatchRule> {
	private long roleId;
	private int matchTime;
	private boolean ai;
	private int maxCount;

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	public void setMatchTime(int matchTime) {
		this.matchTime = matchTime;
	}

	public int getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}

	@Override
	public int compareTo(RoleMatchRule o) {
		return matchTime - o.matchTime;
	}

}
