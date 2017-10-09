package com.bingo.server.game.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.game.bean.User;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.MSGBuilder;
import com.google.protobuf.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/21.
 */
@Service
public class SendToService {

    private static final Logger logger = LoggerFactory.getLogger(SendToService.class);

    @Autowired
    private NotificationProvider notificationProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;

    public void send(MSG.MsgCode msgCode, Message innerNotification, long topic, URL url) {
        MSG.Message.Builder builder = MSGBuilder.notification(msgCode, innerNotification);
        notificationProvider.notify(url, topic, builder.build().toByteArray());
    }

    public void send(MSG.MsgCode msgCode, Message innerNotification, long deskId) {
        List<DdzUser> ddzUsers = ddzUserProvider.getByDeskId(deskId);
        if (Check.isNull(ddzUsers)) {
            return;
        }
        for (DdzUser ddzUser : ddzUsers) {
            URL url = URL.valueOf(ddzUser.getSiteUrl());
            send(msgCode, innerNotification, ddzUser.getTopic(), url);
        }
    }

    public void send(MSG.MsgCode msgCode, Message innerNotification, List<User> users) {
        for (User user : users) {
            send(msgCode, innerNotification, user.getTopic(), user.getUrl());
        }
    }

    public void send(MSG.MsgCode msgCode, Message innerNotification, long deskId, long exceptUserId) {
        List<DdzUser> ddzUsers = ddzUserProvider.getByDeskId(deskId);
        if (Check.isNull(ddzUsers)) {
            return;
        }
        for (DdzUser ddzUser : ddzUsers) {
            if (ddzUser.getId() == exceptUserId) {
                continue;
            }
            URL url = URL.valueOf(ddzUser.getSiteUrl());
            send(msgCode, innerNotification, ddzUser.getTopic(), url);
        }
    }

}
