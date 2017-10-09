/*
package com.bingo.server.game.service;

import com.bingo.server.database.model.*;
import com.bingo.server.exception.ServerException;
import com.bingo.server.gate.provider.PushProvider;
import com.bingo.server.game.provider.DdzBuddyDeskProvider;
import com.bingo.server.game.provider.DdzRuleProvider;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.hall.provider.GameProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.game.provider.DdzRule;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.user.provider.*;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

*/
/**
 * Created by ZhangGe on 2017/7/22.
 *//*

public class GameProviderImpl implements GameProvider {

    private final Logger logger = LoggerFactory.getLogger(OrderProviderImpl.class);

    @Autowired
    private DdzRuleProvider ddzRuleProvider;
    @Autowired
    private CuUserProvider cuUserProvider;
    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;
    @Autowired
    private DdzRuleProvider ddzRuleProvider;
    @Autowired
    private DdzBuddyDeskProvider ddzBuddyDeskProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private CuFriendProvider cuFriendProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private PushProvider pushProvider;


    */
/**
     * 邀请好友
     *//*

    public void invite(long userId, long deskId, long inviteUserId) {
        DdzBuddyDesk ddzBuddyDesk = ddzBuddyDeskProvider.getDeskById(deskId);
        if (Check.isNull(ddzBuddyDesk)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR_VALUE, "牌桌不存在");
        }
        DdzUser ddzUser = ddzUserProvider.getById(inviteUserId);
        if (Check.isNull(ddzUser)) {
            ddzUserProvider.addDdzUser(inviteUserId, deskId, UserStatus.invite);
            // TODO 增加30秒监控线程,如果用户还没进入牌桌,则发送更改状态信息到在桌的是这个用户的好友的用户

            // TODO 发送消息给用户
            CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
            if (Check.isNull(onlineUser)) {
                throw new ServerException(MSG.MsgCode.SYSTEM_ERROR,"用户不在线");
            }
            pushProvider.push(onlineUser.getSiteUrl(), );

        } else {
            if (ddzUser.getStatus().equals(UserStatus.invite)
                    && ddzUser.getDeskId().equals(deskId)) {
                throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户已被邀请,请等待确认");
            } else {
                throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户已在游戏中");
            }
        }
    }

    */
/**
     * 接受邀请
     *//*

    public void acceptInvite(long userId, long deskId) {
        DdzBuddyDesk ddzBuddyDesk = ddzBuddyDeskProvider.getDeskById(deskId);
        if (Check.isNull(ddzBuddyDesk)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR_VALUE, "牌桌不存在");
        }
        DdzUser ddzUser = ddzUserProvider.getById(userId);
        // TODO 将状态改为已进桌,并将牌桌信息返回给用户
    }

    */
/**
     * 获取微信消息
     *//*

    public String getWeixinMessage(long userId, long deskId) {

    }


}
*/
