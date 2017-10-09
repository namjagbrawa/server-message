package com.bingo.server.game.landlord.service.match;


import com.bingo.server.game.landlord.template.BaseServiceInterface;

public interface MatchModelService extends BaseServiceInterface {
    void setMatchHandler(MatchHandler matchHandler);

    void matchRole(MatchRule matchRule);

    void cancelMatch(String ruleId);
}
