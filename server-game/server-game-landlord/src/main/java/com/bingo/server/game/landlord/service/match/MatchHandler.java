package com.bingo.server.game.landlord.service.match;

import java.util.Map;

public interface MatchHandler {
	void outOfTime(MatchRule matchRule);

	boolean checkMatchRule(MatchRule rule1, MatchRule rule2);

	boolean checkArriveMaxCount(MatchRule rule, Map<String, MatchRule> matchRuleMap);

	void matchSuccess(Map<String, MatchRule> matchRule);
}
