package com.bingo.server.game.landlord.template;

public interface ObservableInterface {
	void addObserver(Observer o);

	void deleteObserver(Observer o);

	void notifyObservers(String msg, Object... args);

	void deleteObservers();

	int countObservers();
}
