package com.bingo.server.game.landlord.scheduler;

import java.util.concurrent.TimeUnit;

public interface SchedulerInterface {
	void start();
	void shutdown(long timeout, TimeUnit unit) throws Exception;
}
