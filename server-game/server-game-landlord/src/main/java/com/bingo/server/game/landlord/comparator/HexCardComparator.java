package com.bingo.server.game.landlord.comparator;

import com.bingo.server.game.landlord.util.CardTools;
import org.springframework.stereotype.Component;

import java.util.Comparator;

@Component("hexCardComparator")
public class HexCardComparator implements Comparator<Integer> {

	@Override
	public int compare(Integer o1, Integer o2) {
		return CardTools.toNum(o1) - CardTools.toNum(o2);
	}

}
