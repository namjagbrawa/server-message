package com.bingo.server.game.landlord.entity.po.cardlist;

import com.bingo.server.game.landlord.entity.po.CardSort;
import com.bingo.server.game.landlord.error.CardListPatternException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class KQ extends CardList {

	@Override
	public CardList pattern(CardSort cardSort, List<Integer> arr) throws CardListPatternException {
		if (arr.size() != 2)
			throw new CardListPatternException();
		Set<Integer> set = cardSort.getCardSort().get(0);
		if (set.size() == 2 && set.contains(14) && set.contains(15))
			return new KQ();

		throw new CardListPatternException();
	}

	@Override
	public int compareTo(CardList o) {
		return 1;
	}

	@Override
	public void recommand(List<List<Integer>> recommandList, CardSort cardSort, CardList lastCardList,
			List<Integer> arr) {
		if (arr.size() < 2 || cardSort.getCardSort().get(0).size() < 2)
			return;
		
		cardSort = cardSort.clone();
		List<List<Integer>> lists = new ArrayList<>();
		Set<Integer> set = cardSort.getCardSort().get(0);				
		if(set.contains(0xE) && set.contains(0xF)){
			List<Integer> list = new ArrayList<>(2);
			list.add(0xE);
			list.add(0xF);
			lists.add(list);
		}
		recommandList.addAll(recommandList.size(), lists);	

	}


}
