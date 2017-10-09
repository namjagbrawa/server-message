package com.bingo.server.game.landlord.template;

public class Ref<T> {
	private T obj = null;

	public T get() {
		return obj;
	}

	public void set(T t) {
		this.obj = t;
	}
}
