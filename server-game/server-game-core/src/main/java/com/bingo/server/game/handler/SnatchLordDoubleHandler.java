package com.bingo.server.game.handler;

import com.bingo.server.exception.ServerException;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.msg.MSG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/27.
 */
@Service("snatchLordDouble")
public class SnatchLordDoubleHandler implements SnatchLordHandler {

    private final Logger logger = LoggerFactory.getLogger(SnatchLordDoubleHandler.class);

    public void nextMark(Desk desk) {
        int maxCallMark = desk.getMaxCallMark();
        if (maxCallMark <= 0) {
            desk.setNextCallMark(new byte[]{1, 0});
        } else {
            desk.setNextCallMark(new byte[]{(byte) (maxCallMark * 2), 0});
        }
    }

    public void mark(Desk desk, int mark, int chairId) {
        if (mark == 0) {
            desk.setNoCallTime(desk.getNoCallTime() + 1);
        }
        if (desk.getNoCallTime() >= 3) {
            desk.setRedeal(true);
        }
        byte[] nextCallMark = desk.getNextCallMark();
        boolean contain = false;
        for (int i = 0; i < nextCallMark.length; i++) {
            if (nextCallMark[i] == mark) {
                contain = true;
                break;
            }
        }
        if (!contain) {
            logger.error("不是可叫的数值, Desk : " + desk + " Mark : " + mark);
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "不是可叫的数值");
        }
        if (mark > desk.getMaxCallMark()) {
            desk.setMaxCallMark(mark);
            desk.setMaxChairId(chairId);
        }
        if (desk.getCallTime() >= 3) {
            desk.setLordChair(desk.getMaxChairId());
            return;
        } else {
            nextMark(desk);
        }
    }
}
