package com.bingo.server.gate.service;

import com.bingo.framework.common.URL;
import com.bingo.framework.remoting.Channel;
import com.bingo.server.exception.ServerException;
import com.bingo.server.gate.bean.Client;
import com.bingo.server.gate.manager.ClientManager;
import com.bingo.server.gate.manager.GameManager;
import com.bingo.server.gate.provider.GateDeskProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.utils.Check;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@Service
public class GateDeskService implements GateDeskProvider {

    @Override
    public void inDesk(URL url, URL serverUrl, long topic) {
        GameManager.get().put(topic, serverUrl, true, true);
        Client client = ClientManager.get().getValue(topic, true, true);
        if (Check.isNull(client)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "Topic未注册, 请检查, Topic : " + topic + " ServerUrl : " + serverUrl);
        }

        Channel channel = client.getChannel();
        if (Check.isNull(channel)) {
            ClientManager.get().remove(topic, true);
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户连接已断开, Channel不可用, Client : " + client);
        }

        if (!channel.isConnected()) {
            channel.close();
            ClientManager.get().remove(topic, true);
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户连接已断开, Channel已出于非连接状态, Channel : " + channel);
        }
        channel.setAttribute("serverUrl", serverUrl);
    }
}
