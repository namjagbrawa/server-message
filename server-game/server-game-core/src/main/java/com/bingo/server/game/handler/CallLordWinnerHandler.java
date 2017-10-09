package com.bingo.server.game.handler;

import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.ddz.service.DeskService;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by ZhangGe on 2017/7/27.
 */
@Service("callLordWinner")
public class CallLordWinnerHandler implements CallLordHandler {

    private final Logger logger = LoggerFactory.getLogger(CallLordWinnerHandler.class);

    @Override
    public void startCall(Desk desk) {
        int lastWinnerChair = desk.getLastWinnerChair();
        if (lastWinnerChair != 0) {
            desk.setNextChair(lastWinnerChair);
        } else {
            desk.setNextChair(1);
        }
    }

    @Override
    public void nextCall(Desk desk) {
        int lastWinnerChair = desk.getLastWinnerChair();
        if (lastWinnerChair != 0) {
            desk.setNextChair(lastWinnerChair);
        } else {
            desk.setNextChair(1);
        }
    }

}
