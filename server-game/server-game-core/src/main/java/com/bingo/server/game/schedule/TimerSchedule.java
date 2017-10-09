package com.bingo.server.game.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by zhanghuan on 2017/7/19.
 */
@Service
public class TimerSchedule {

    @Autowired
    Set<Schedule> scheduleSet;

    @Scheduled(cron = "0/20 * * * * ? ")   //每20秒执行一次
    public void first() {
    }

    @Scheduled(cron = "0 0/1 * * * ? ")   //每分钟执行一次
    public void second() {
    }

    @Scheduled(cron = "0 0/5 * * * ? ")   //每5分钟执行一次
    public void third() {
    }

    @Scheduled(cron = "0 0 0 0/1 * ? ")   //每天执行一次
    public void last() {
    }
}

