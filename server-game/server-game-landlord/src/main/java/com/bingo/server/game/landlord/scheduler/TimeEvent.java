package com.bingo.server.game.landlord.scheduler;


public interface TimeEvent {

    int getEndTime();

    void setEndTime(int endTime);

    void update(TimeEvent timeEvent);
}
