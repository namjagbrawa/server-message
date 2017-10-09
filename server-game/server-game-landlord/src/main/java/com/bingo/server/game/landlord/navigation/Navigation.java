package com.bingo.server.game.landlord.navigation;

import com.bingo.server.game.landlord.template.IActionSupport;

import java.util.HashMap;
import java.util.Map;

public class Navigation {
	private static Map<String, IActionSupport> navigate = new HashMap<>();

	// 根据消息头获取导航
	public static IActionSupport getAction(String name) {
		return navigate.get(name);
	}

}
