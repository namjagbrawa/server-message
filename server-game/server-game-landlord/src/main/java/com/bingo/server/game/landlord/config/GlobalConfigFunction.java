package com.bingo.server.game.landlord.config;

import com.bingo.server.game.landlord.template.Function;

import java.util.List;
import java.util.Map;

public abstract class GlobalConfigFunction implements Function {

	@SuppressWarnings("unchecked")
    @Override
	@Deprecated
	public Object apply(Object... params) {
		// TODO Auto-generated method stub
		init((Map<String, Object>) params[0], (List<String>) params[1]);
		return null;
	}

	public abstract void init(Map<String, Object> map, List<String> list);

}
