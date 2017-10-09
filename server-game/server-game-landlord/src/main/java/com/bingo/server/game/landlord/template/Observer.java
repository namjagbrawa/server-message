package com.bingo.server.game.landlord.template;

public interface Observer {
	public void update(Observer observer, String msg, Object... args);
}
