package com.bingo.server.game.landlord.entity.po;

import java.util.List;

public class CardRecord {
	public List<Integer> cards;
	public String gameRoleId;
	@Override
	public String toString() {
		if(cards == null)
			return "null";
		return cards.toString();
	}
	
}
