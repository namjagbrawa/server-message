package com.bingo.server.gate.provider;

import com.bingo.framework.common.URL;
import com.bingo.server.msg.MSG;

/**
 * Created by ZhangGe on 2017/7/12.
 */
public interface GateDeskProvider {

    void inDesk(URL url, URL serverUrl, long topic);

}
