package com.bingo.server.game.landlord.service;

import com.bingo.server.game.landlord.template.ObserveBaseServiceInterface;

public interface GmProvider extends ObserveBaseServiceInterface {

	void loopSaveData(boolean mustSave);
//	public GeneratedMessage rejectLogin(String code);
//	public void terminatedServer(String code,IoSession session);
//	public GeneratedMessage openLogin(String code);
}
