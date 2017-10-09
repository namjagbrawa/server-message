/*
package com.bingo.server.game.service;

import com.bingo.framework.common.URL;
import com.bingo.framework.common.utils.JSONUtil;
import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.DdzBuddyDesk;
import com.bingo.server.game.bean.User;
import com.bingo.server.game.manager.DeskManager;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.task.DdzTaskProcess;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.provider.bean.enums.DeskStatus;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.game.provider.bean.enums.DeskType;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.game.provider.DdzBuddyDeskProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

*/
/**
 * Created by ZhangGe on 2017/7/20.
 *//*

public class NewService {

    private static final Logger logger = LoggerFactory.getLogger(DdzTaskProcess.class);

    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;
    @Autowired
    private DdzBuddyDeskProvider ddzBuddyDeskProvider;
    @Autowired
    private SendToUserService sendToUserService;
    @Autowired
    private ConnectService deskService;

    public void newBuddyDesk(URL url, DdzBuddyDesk ddzBuddyDesk, CuOnlineUser cuOnlineUser) {
        Desk desk = new Desk();
        desk.setCreateTime(new Date());
        List<User> users = new ArrayList<>();
        User user = getUser(cuOnlineUser);
        users.add(user);
        desk.setUsers(users);
        desk.setDdzRule(JSONUtil.toBean(ddzBuddyDesk.getRule(), DdzRule.class));
        desk.setDeskName(ddzBuddyDesk.getName());
        desk.setDeakDetail(ddzBuddyDesk.getDetail());
        desk.setDeskTip(ddzBuddyDesk.getTip());
        desk.setServerUrl(url);
        desk.setDeskId(ddzBuddyDesk.getId());
        desk.setDeskType(DeskType.buddy);
        desk.setDeskStatus(DeskStatus.valueOf(ddzBuddyDesk.getStatus()));
        DeskManager.get().put(desk, user);

        // TODO 通知用户
        MSG.DdzNewDeskNotification innerNotification = MSG.DdzNewDeskNotification.newBuilder().build();
        sendToUserService.sendToClient(MSG.MsgCode.DDZ_NEW_DESK_NOTIFICATION, innerNotification, user);

        if (users.size() >= 3) {
            // TODO 进入开桌流程
        }
    }

    public void enterBuddyDesk(URL url, long deskId, CuOnlineUser cuOnlineUser) {
        Desk desk = DeskManager.get().getByDesk(deskId);
        if (Check.isNull(desk)) {
            logger.error("牌桌不存在, deskId : " + deskId);
            sendToUserService.sendErrorNotification(MSG.MsgCode.DDZ_BUSINESS_ERROR, "牌桌不存在", cuOnlineUser);
        }
        List<User> users = desk.getUsers();
        if (users.size() >= 3) {
            logger.error("牌桌已满, desk : " + desk);
            sendToUserService.sendErrorNotification(MSG.MsgCode.DDZ_BUSINESS_ERROR, "牌桌已满", cuOnlineUser);
        }
        User user = getUser(cuOnlineUser);
        users.add(user);
        DeskManager.get().put(desk, user);
        // TODO 通知所有用户,有用户加入
        if (users.size() >= 3) {
            for (User user : users) {

            }
            ddzBuddyDeskProvider.addUser(deskId, users);
            // TODO 进入开桌流程
            deskService.
        }
    }

    public void reconnect(URL url, long deskId, CuOnlineUser cuOnlineUser) {
        Desk desk = DeskManager.get().getByDesk(deskId);
        if (Check.isNull(desk)) {
            logger.error("牌桌不存在, deskId : " + deskId);
            sendToUserService.sendErrorNotification(MSG.MsgCode.DDZ_BUSINESS_ERROR, "牌桌不存在", cuOnlineUser);
        }

        User user = userInDesk(cuOnlineUser.getId(), desk);
        if (Check.isNull(user)) {
            logger.error("用户不在牌桌, Desk : " + desk + " cuOnlineUser : " + cuOnlineUser);
        }
        DeskManager.get().put(desk, user);

        // TODO 通知所有用户,有用户加入

        // TODO 重连逻辑及消息
    }

    public void enterRandomDesk(URL url, DdzBuddyDesk ddzBuddyDesk, List<CuOnlineUser> cuOnlineUsers) {
        Desk desk = new Desk();
        desk.setCreateTime(new Date());
        List<User> users = new ArrayList<>();
        for (CuOnlineUser cuOnlineUser : cuOnlineUsers) {
            User user = getUser(cuOnlineUser);
            users.add(user);
        }
        desk.setUsers(users);
        desk.setDdzRule(JSONUtil.toBean(ddzBuddyDesk.getRule(), DdzRule.class));
        desk.setDeskName(ddzBuddyDesk.getName());
        desk.setDeakDetail(ddzBuddyDesk.getDetail());
        desk.setDeskTip(ddzBuddyDesk.getTip());
        desk.setServerUrl(url);
        desk.setDeskId(ddzBuddyDesk.getId());
        desk.setDeskType(DeskType.buddy);
        desk.setDeskStatus(DeskStatus.valueOf(ddzBuddyDesk.getStatus()));

        for (User user : users) {
            DeskManager.get().put(desk, user);
        }

        // 通知用户
        gateTask.onTaskMSG();
    }

    //011690密码08090826
    private User userInDesk(long userId, Desk desk) {
        for (User user : desk.getUsers()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    private User getUser(CuOnlineUser cuOnlineUser) {
        User user = new User();
        user.setUserId(cuOnlineUser.getId());
        user.setTopic(cuOnlineUser.getTopic());
        user.setUrl(URL.valueOf(cuOnlineUser.getSiteUrl()));
        user.setUserStatus(UserStatus.online);
        return user;
    }


}
*/
