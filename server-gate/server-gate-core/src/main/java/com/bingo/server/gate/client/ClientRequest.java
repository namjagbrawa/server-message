package com.bingo.server.gate.client;

import com.bingo.framework.remoting.Channel;

/**
 * Created by ZhangGe on 2017/7/12.
 */
public interface ClientRequest {

    void onClientMSG(int code, byte[] bytes, Channel channel);

    void onConnect(Channel channel);

    void onDisconnect(Channel channel);

}
