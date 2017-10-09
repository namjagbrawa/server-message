package com.bingo.server.hall.provider;

import com.bingo.server.msg.RESP;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface MessageProvider {

    RESP.GetMessageResponse getMessages(long userId);

}
