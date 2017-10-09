package com.bingo.server.game.handler;

import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.ddz.DeskUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/27.
 */
@Service("callLordPolling")
public class CallLordPollingHandler implements CallLordHandler {

    private final Logger logger = LoggerFactory.getLogger(CallLordPollingHandler.class);

    @Override
    public void startCall(Desk desk) {
        int startCallChair = desk.getStartCallChair();
        int clockwiseChair = DeskUtil.clockwise(startCallChair, 3);
        desk.setNextChair(clockwiseChair);
        desk.setStartCallChair(clockwiseChair);
    }

    @Override
    public void nextCall(Desk desk) {
        desk.setNextChair(desk.getNextChair() + 1);
    }

}
