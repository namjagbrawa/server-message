package com.bingo.server.game.landlord.template;

import java.util.HashSet;
import java.util.Set;

public class ObserveBaseService extends BaseService implements ObserveBaseServiceInterface {

	private Set<Observer> observers = new HashSet<>();

	@Override
	public void addObserver(Observer o) {
		if (o == null)
			throw new NullPointerException();
		
		observers.add(o);
	}

	@Override
	public void deleteObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers(String msg, Object... args) {
		for (Observer observer : observers) {
			try {
				observer.update(this, msg, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void deleteObservers() {
		observers.clear();
	}

	@Override
	public int countObservers() {
		return observers.size();
	}

	@Override
	public void update(Observer observer, String msg, Object... args) {

	}

}
