package com.bingo.server.exception;

import com.bingo.framework.common.exception.BingoException;
import com.bingo.server.msg.MSG;

import javax.xml.ws.Service;

/**
 * Created by ZhangGe on 2017/4/4.
 */
public final class ServerException extends BingoException {

    private static final long serialVersionUID = 7815426752583648687L;

    private MSG.MsgCode msgCode;

    public ServerException() {

    }

    public ServerException(MSG.MsgCode code) {
        super();
        this.msgCode = code;
        this.code = code.getNumber();
    }

    public ServerException(MSG.MsgCode code, String message, Throwable cause) {
        super(message, cause);
        this.msgCode = code;
        this.code = code.getNumber();
    }

    public ServerException(MSG.MsgCode code, String message) {
        super(message);
        this.msgCode = code;
        this.code = code.getNumber();
    }

    public ServerException(MSG.MsgCode code, Throwable cause) {
        super(cause);
        this.msgCode = code;
        this.code = code.getNumber();
    }

    public MSG.MsgCode getMsgCode() {
        return msgCode;
    }
}
