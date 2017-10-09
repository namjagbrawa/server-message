package com.bingo.server.gate.provider;

import com.bingo.framework.common.URL;
import com.bingo.server.msg.MSG;

/**
 * Created by ZhangGe on 2017/7/12.
 */
public interface NotificationProvider {

    void notify(URL url, long topic, byte[] bytes);

}
