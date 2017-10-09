package com.bingo.server.gate.service;

import com.bingo.framework.common.URL;
import com.bingo.framework.remoting.Channel;
import com.bingo.framework.remoting.RemotingException;
import com.bingo.server.gate.bean.Client;
import com.bingo.server.gate.manager.ClientManager;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.utils.Check;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@Service
public class NotificationService implements NotificationProvider {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public void notify(URL url, long topic, byte[] bytes) {
        if (Check.isNull(bytes)) {
            logger.error("消息内容为空, topic : " + topic);
            return;
        }
        MSG.Message message = null;
        try {
            message = MSG.Message.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            logger.error("消息解析错误, topic : " + topic + " bytes : " + bytes, e);
            return;
        }
        if (true) {
            logger.info("url : {}, topic : {}, message : {}", url, topic, message);
            return;
        }
        Client client = ClientManager.get().getValue(topic, true, true);
        if (Check.isNull(client)) {
            logger.error("用户不存在,或用户已断开连接, topic : " + topic + " bytes : " + bytes);
            return;
        }
        Channel channel = client.getChannel();
        if (Check.isNull(channel)) {
            logger.error("用户连接已断开, Channel不可用, Client : " + client);
            ClientManager.get().remove(topic, true);
            return;
        }

        if (!channel.isConnected()) {
            logger.error("用户连接已断开, Channel已出于非连接状态, Channel : " + channel);
            channel.close();
            ClientManager.get().remove(topic, true);
            return;
        }

        try {
            if (true) {
                logger.info("url : {}, topic : {}, message : {}", url, topic, message);
                return;
            }
            channel.send(message);
        } catch (RemotingException e) {
            logger.error("发送消息错误, Client : " + client + " Message : " + message);
            int count = 3;
            int waitSecond = 1;
            while (count-- >= 0) {
                try {
                    Thread.sleep(waitSecond);
                    waitSecond *= 2;
                    channel.send(message);
                    break;
                } catch (RemotingException e1) {
                    logger.error("重发错误, Client : " + client + " Message : " + message + " 剩余重发次数 : " + count);
                    if (count >= 3) {
                        continue;
                    } else {
                        break;
                    }
                } catch (InterruptedException ie) {
                    logger.error("等待错误", ie);
                }
            }
        }
    }

}
