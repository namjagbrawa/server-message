package com.bingo.server.game.landlord.service;


import com.bingo.server.game.landlord.template.ObserveBaseServiceInterface;

public interface ScoreProvider extends ObserveBaseServiceInterface {

	void updateScore(long gameId, String gameRoleId);
	
}
