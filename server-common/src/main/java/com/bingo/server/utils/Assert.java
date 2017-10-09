package com.bingo.server.utils;

import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;

/**
 * Created by ZhangGe on 2017/7/14.
 */
public class Assert {

    public static void hasText(String text, String errorMSG) {
        if (Check.isNull(text)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, errorMSG);
        }
    }

    public static void isNull(Object obj, String errorMSG) {
        if (Check.isNull(obj)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, errorMSG);
        }
    }

    public static void isNull(Long l, String errorMSG) {
        if (Check.isNull(l)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, errorMSG);
        }
    }

    public static void isNull(Integer l, String errorMSG) {
        if (Check.isNull(l)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, errorMSG);
        }
    }

    public static void isTrue(boolean b, String errorMSG) {
        if (b) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, errorMSG);
        }
    }

}
