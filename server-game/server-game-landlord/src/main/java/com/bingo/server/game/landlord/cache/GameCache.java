package com.bingo.server.game.landlord.cache;


import com.bingo.server.game.landlord.entity.bo.Game;
import com.bingo.server.game.landlord.entity.po.cardlist.CardList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameCache {
	private static Map<Long, Game> gameMap = new LinkedHashMap<>();
	private static Map<String, Long> gameLockMap = new LinkedHashMap<>();
	private static Map<Class<? extends CardList>, CardList> cardLists = new LinkedHashMap<>();
	private static Map<Class<? extends CardList>, CardList> recommendCardLists = new LinkedHashMap<>();

	public static Map<Long, Game> getGameMap() {
		return gameMap;
	}

	public static Map<String, Long> getGameLockStringMap() {
		return gameLockMap;
	}

	public static Map<Class<? extends CardList>, CardList> getCardLists() {
		return cardLists;
	}

	public static List<CardList> sendCardSeqCheckerList = new ArrayList<>();

	public static List<CardList> getSendCardSeqCheckerList() {
		return sendCardSeqCheckerList;
	}

	public static Map<Class<? extends CardList>, CardList> getRecommendCardLists() {
		return recommendCardLists;
	}
	

}
