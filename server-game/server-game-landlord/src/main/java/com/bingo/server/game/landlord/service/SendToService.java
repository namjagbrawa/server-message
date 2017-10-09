package com.bingo.server.game.landlord.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/21.
 */
@Service
public class SendToService {

    private static final Logger logger = LoggerFactory.getLogger(SendToService.class);

    @Autowired
    private NotificationProvider notificationProvider;

    public void send(ServerMessage.SC sc, long topic, URL url) {
        MSG.Message.Builder scBuilder = MSG.Message.newBuilder().setMsgCode(MSG.MsgCode.Landlord_SC).setSc(sc);
        notificationProvider.notify(url, topic, scBuilder.build().toByteArray());
    }

    public void send(long roleId, ServerMessage.SC sc) {
        Role role = RoleCache.getRoleById(roleId);
        send(sc, role.getTopic(), URL.valueOf(role.getSiteUrl()));
    }

}
