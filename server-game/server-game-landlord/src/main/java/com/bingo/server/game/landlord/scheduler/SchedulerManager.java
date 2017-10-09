package com.bingo.server.game.landlord.scheduler;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class SchedulerManager implements SchedulerInterface {
	private List<SchedulerInterface> schedulers = new ArrayList<>();

	public void setSchedulers(List<SchedulerInterface> schedulers) {
		this.schedulers = schedulers;
	}

	public List<SchedulerInterface> getSchedulers() {
		return schedulers;
	}

	public void start() {
		for (SchedulerInterface scheduler : schedulers) {
			scheduler.start();
		}
	}

	@Override
	public void shutdown(long timeout, TimeUnit unit) throws Exception {
		for (SchedulerInterface scheduler : schedulers) {
			scheduler.shutdown(timeout, unit);
		}
	}
}
