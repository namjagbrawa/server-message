package com.bingo.server.gate.client;

import com.bingo.framework.common.io.Bytes;
import com.bingo.framework.common.utils.IDGen;
import com.bingo.framework.remoting.Channel;
import com.bingo.framework.remoting.RemotingException;
import com.bingo.server.exception.ServerException;
import com.bingo.server.gate.bean.Client;
import com.bingo.server.gate.manager.ClientManager;
import com.bingo.server.gate.service.DispatcherService;
import com.bingo.server.hall.provider.LoginProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.NOTIFY;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.MSGBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ZhangGe on 2017/7/12.
 */
@Service
public class ClientService implements ClientRequest {

    private final Logger logger = LoggerFactory.getLogger(ClientService.class);
    private final AtomicInteger connectNumber = new AtomicInteger(0);

    @Autowired
    private DispatcherService dispatcherService;
    @Autowired
    private LoginProvider loginProvider;

    @Override
    public void onClientMSG(int code /*code只定义了心跳,其他未定义,所以不使用*/, byte[] bytes, Channel channel) {
        if (bytes == null) {
            logger.error("MSG IS NULL, CHANNEL : " + channel);
            return;
        }

        // 解析请求消息
        MSG.Message reqMsg;
        try {
            reqMsg = MSG.Message.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            logger.error("MESSAGE PARSE ERROR, BYTES : " + bytes, e);
            return;
        }

        if (reqMsg == null) {
            logger.error("MSG PARSE RESULT IS NULL, CHANNEL : " + channel + " BYTE : " + bytes);
            return;
        }

        long topic = (long) channel.getAttribute("topic");
        long userId = (long) channel.getAttribute("userId");

        // 有返回消息,指向Process的消息
        try {
            MSG.Message.Builder respMsg = dispatcherService.onClientMSG(topic, userId, reqMsg, channel);
            if (Check.isNull(reqMsg)) {
                respMsg = MSGBuilder.success(MSG.MsgCode.Success_Response);
            }
            send(channel, reqMsg.getSequence(), respMsg);
        } catch (Throwable t) {
            MSG.Message.Builder failMsg;
            if (t instanceof ServerException) {
                if (((ServerException) t).getMsgCode().equals(MSG.MsgCode.SYSTEM_ERROR)) {
                    logger.error("ServerException系统错误", t);
                    failMsg = MSGBuilder.fail(((ServerException) t).getMsgCode(), "系统繁忙,请重试");
                } else {
                    failMsg = MSGBuilder.fail(((ServerException) t).getMsgCode(), t.getMessage());
                }
            } else {
                logger.error("未定义系统错误", t);
                failMsg = MSGBuilder.fail(MSG.MsgCode.SYSTEM_ERROR, "系统繁忙,请重试");
            }
            send(channel, reqMsg.getSequence(), failMsg);
        }
    }

    @Override
    public void onConnect(Channel channel) {
        if (channel == null) {
            logger.error("CLIENT ON CONNECT CHANNEL IS NULL.");
            return;
        }
        if (!channel.isConnected()) {
            logger.error("CLIENT ON CONNECT, CHANNEL IS NOT CONNECTED, CHANNEL : " + channel);
            return;
        }

        // 限制最大连接数
        if (connectNumber.get() >= 30000) {
            logger.info("超过最大连接数 : connectNumber : " + connectNumber);
            channel.close();
            return;
        }
        connectNumber.incrementAndGet();

        // 初始化Client信息
        long topic = IDGen.getId();
        Client client = new Client();
        client.setChannel(channel);
        client.setTopic(topic);
        ClientManager.get().put(topic, client, true, true);

        channel.setAttribute("topic", topic);

        MSG.Message message = MSG.Message.newBuilder()
                .setNotification(NOTIFY.Notification.newBuilder()
                        .setOnConnect(NOTIFY.OnConnectNotification.newBuilder()
                                .setTopic(topic)))
                .setMsgCode(MSG.MsgCode.On_Connect_Notification)
                .build();
        send(channel, message);
    }

    @Override
    public void onDisconnect(Channel channel) {
        if (channel == null) {
            logger.error("CLIENT ON CONNECT CHANNEL IS NULL.");
            return;
        }
        Long topic = (Long) channel.getAttribute("topic");
        Long userId = (Long) channel.getAttribute("userId");

        if (userId != null) {
            dispatcherService.disconnect(userId);
        }

        if (topic != null) {
            Client client = ClientManager.get().remove(topic, true);
            client.getChannel().close();
        }
        if (channel != null) {
            channel.close();
        }
        connectNumber.decrementAndGet();
    }

    private void send(Channel channel, long sequence, MSG.Message.Builder respMsg) {
        send(channel, respMsg.setSequence(sequence).build());
    }

    private void send(Channel channel, MSG.Message respMsg) {
        if (channel == null) {
            logger.error("SEND TO CLIENT, CHANNEL IS NULL");
            return;
        }
        if (!channel.isConnected()) {
            logger.error("SEND TO CLIENT, CHANNEL IS NOT CONNECTED, CHANNEL : " + channel);
            return;
        }
        byte[] bytes = respMsg.toByteArray();
        byte[] data = new byte[bytes.length + 2];
        MSG.MsgCode msgCode = respMsg.getMsgCode();
        Bytes.short2bytes((short) msgCode.getNumber(), data, 0);
        System.arraycopy(bytes, 0, data, 2, bytes.length);

        try {
            channel.send(data);
        } catch (RemotingException e) {
            logger.error("SEND TO CLIENT, SEND ERROR, CHANNEL : " + channel + " MESSAGE : " + respMsg);
            return;
        }
    }
}
