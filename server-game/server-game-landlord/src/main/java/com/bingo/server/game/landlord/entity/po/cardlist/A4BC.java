package com.bingo.server.game.landlord.entity.po.cardlist;

import com.bingo.server.game.landlord.entity.po.CardSort;
import com.bingo.server.game.landlord.error.CardListPatternException;
import com.bingo.server.game.landlord.error.CardTypeComparableException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class A4BC extends A4 {

	private List<Integer> addNumList = new ArrayList<>();

	public List<Integer> getAddNumList() {
		return addNumList;
	}

	public void setAddNumList(List<Integer> addNumList) {
		this.addNumList = addNumList;
	}



	@Override
	public int compareTo(CardList arg0) {
		if (arg0.getClass() == A4.class || arg0.getClass() == KQ.class)
			return -1;

		if (getClass() != arg0.getClass())
			throw new CardTypeComparableException();

		A4BC a4bc = (A4BC) arg0;

		return getNum() - a4bc.getNum();
	}

	@Override
	public CardList pattern(CardSort cardSort, List<Integer> arr) throws CardListPatternException {
		if (arr.size() != 6)
			throw new CardListPatternException();

		Set<Integer> set0 = cardSort.getCardSort().get(0);
		Set<Integer> set1 = cardSort.getCardSort().get(1);
		Set<Integer> set3 = cardSort.getCardSort().get(3);
		if (set3.size() != 1)
			throw new CardListPatternException();
		// 如果是带对子

		if (!(set0.size() == 3 ||(set0.size() == 2 && set1.size() == 2)))
			throw new CardListPatternException();
		A4BC a4bc = new A4BC();
		a4bc.setNum(set3.iterator().next());
		for (int i : set0) {
			if (i != a4bc.getNum()) {
				a4bc.getAddNumList().add(i);
			}
		}

		return a4bc;
	}
	
	@Override
	public void recommand(List<List<Integer>> recommandList, CardSort cardSort, CardList lastCardList, List<Integer> arr) {
		
	}

}
