package com.bingo.server.game.landlord.entity.po.cardlist;

import com.bingo.server.game.landlord.entity.po.CardSort;
import com.bingo.server.game.landlord.error.CardListPatternException;

import java.util.List;

public abstract class CardList implements Comparable<CardList> {

	public abstract CardList pattern(CardSort cardSort, List<Integer> arr) throws CardListPatternException;

	public abstract void recommand(List<List<Integer>> recommandList, CardSort cardSort, CardList lastCardList,
			List<Integer> arr);
}
