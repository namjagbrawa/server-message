package com.bingo.server.game.handler;

import com.bingo.server.exception.ServerException;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.ddz.DeskUtil;
import com.bingo.server.msg.MSG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/27.
 */
@Service("snatchLordDirect")
public class SnatchLordDirectHandler implements SnatchLordHandler {

    private final Logger logger = LoggerFactory.getLogger(SnatchLordDirectHandler.class);

    public void nextMark(Desk desk) {
        if (desk.getMaxCallMark() < 1) {
            desk.setNextCallMark(new byte[]{1, 0});
        } else {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "已经叫到1分了,可以直接开桌");
        }
    }

    public void mark(Desk desk, int mark, int chairId) {
        if (mark == 0) {
            desk.setNoCallTime(desk.getNoCallTime() + 1);
            desk.setCallTime(desk.getCallTime() + 1);
            if (desk.getNoCallTime() >= 3) {
                desk.setRedeal(true);
                return;
            } else {
                nextMark(desk);
                return;
            }
        }
        if (mark > 1) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "不能叫超过1分的地主");
        }
        desk.setLordChair(chairId);
    }
}
