package com.bingo.server.gate.service;

import com.bingo.framework.common.URL;
import com.bingo.framework.remoting.Channel;
import com.bingo.server.database.model.Version;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.provider.GameInfoProvider;
import com.bingo.server.game.provider.StartGameProvider;
import com.bingo.server.game.provider.ddz.DeskProvider;
import com.bingo.server.game.provider.landlord.LandlordProvider;
import com.bingo.server.gate.bean.Client;
import com.bingo.server.gate.manager.ClientManager;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.hall.provider.*;
import com.bingo.server.msg.*;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.LocalService;
import com.bingo.server.utils.MSGBuilder;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/13.
 */
@Service
public class DispatcherService {

    private static final Logger logger = LoggerFactory.getLogger(DispatcherService.class);

    @Autowired
    private LoginProvider loginProvider;
    @Autowired
    private VersionProvider versionProvider;
    @Autowired
    private UserProvider userProvider;
    @Autowired
    private BroadcastProvider broadcastProvider;
    @Autowired
    private MessageProvider messageProvider;
    @Autowired
    private GameInfoProvider gameInfoProvider;
    @Autowired
    private StartGameProvider startGameProvider;
    @Autowired
    private FriendProvider friendProvider;
    @Autowired
    private DeskProvider deskProvider;
    @Autowired
    private LandlordProvider landlordProvider;

    public MSG.Message.Builder onClientMSG(long topic, long userId, MSG.Message reqMsg, Channel channel) {
        // 校验连接是否正常
        if (topic == 0) {
            return MSGBuilder.fail(MSG.MsgCode.NOT_CONNECT_ERROR, "用户连接异常,请重新连接");
        }

        // 获取消息代码
        MSG.MsgCode msgCode = reqMsg.getMsgCode();
        if (Check.isNull(msgCode)) {
            return MSGBuilder.fail(MSG.MsgCode.SYSTEM_ERROR, "消息代码不能为空");
        }

        REQ.Request request = reqMsg.getRequest();
        if (Check.isNull(request)) {
            logger.warn("请求消息体为空, reqMsg : ", reqMsg);
        }

        // 是否是注册和登录消息
        if (msgCode == MSG.MsgCode.Weixin_Login_Request) {
            switch (msgCode) {
                case Weixin_Login_Request:
                    REQ.WeixinLoginRequest weixinLogin = request.getWeixinLogin();
                    String code = weixinLogin.getCode();
                    URL siteUrl = LocalService.getUrl(NotificationProvider.class);
                    userId = loginProvider.weixinLogin(code, topic, siteUrl);
                    Client client = ClientManager.get().getValue(topic, true, true);
                    client.setUserId(userId);
                    channel.setAttribute("userId", userId);
                    return MSGBuilder.success(MSG.MsgCode.Weixin_Login_Response,
                            RESP.WeixinLoginResponse.newBuilder().setUserId(userId)
                                    .build());
            }
        }

        if (userId == 0) {
            return MSGBuilder.fail(MSG.MsgCode.NOT_ONLINE_ERROR, "用户未登录,请登录后重试");
        }

        // 消息路由到相应的服务
        switch (msgCode) {
            case Logout_Request: // 登出
                loginProvider.logout(userId);
                return MSGBuilder.success(MSG.MsgCode.Logout_Response);
            case Lastest_Version_Request: // 最新版本信息
                Version version = versionProvider.getLastestVersion();
                return MSGBuilder.success(MSG.MsgCode.Lastest_Version_Response,
                        RESP.LastestVersionResponse.newBuilder().setVersion(version.getVersion())
                                .setUrl(version.getUrl())
                                .build());
            case Timestamp_Request:
                return MSGBuilder.success(MSG.MsgCode.Timestamp_Response, RESP.TimestampResponse.newBuilder()
                        .setTimestamp(System.currentTimeMillis())
                        .build());
            case Get_User_Info_Request:
                RESP.GetUserInfoResponse getUserInfoResponse = userProvider.getUserInfo(userId);
                return MSGBuilder.success(MSG.MsgCode.Get_User_Info_Response, getUserInfoResponse);
            case Get_Broadcast_Request:
                String broadcast = broadcastProvider.getBroadcast();
                return MSGBuilder.success(MSG.MsgCode.Get_Broadcast_Response, RESP.GetBroadcastResponse.newBuilder()
                        .setContent(broadcast)
                        .build());
            case Get_Message_Request:
                RESP.GetMessageResponse getMessageResponse = messageProvider.getMessages(userId);
                return MSGBuilder.success(MSG.MsgCode.Get_Message_Response, getMessageResponse);
            case Ddz_Get_Rule_Request:
                RESP.DdzGetRuleResponse ddzGetRuleResponse = gameInfoProvider.getDdzRule();
                return MSGBuilder.success(MSG.MsgCode.Ddz_Get_Rule_Response, ddzGetRuleResponse);
            case Ddz_Check_In_Desk_Request:
                RESP.DdzCheckInDeskResponse ddzCheckInDeskResponse = startGameProvider.checkInDesk(userId);
                return MSGBuilder.success(MSG.MsgCode.Ddz_Check_In_Desk_Response, ddzCheckInDeskResponse);
            case Ddz_Confirm_Rule_Request:
                REQ.DdzConfirmRuleRequest ddzConfirmRule = request.getDdzConfirmRule();
                RESP.DdzConfirmRuleResponse ddzConfirmRuleResponse = startGameProvider.confirmRule(userId, ddzConfirmRule.getDeskName(), ddzConfirmRule.getRule());
                return MSGBuilder.success(MSG.MsgCode.Ddz_Confirm_Rule_Response, ddzConfirmRuleResponse);
            case Get_Friends_Request:
                RESP.GetFriendsResponse friends = friendProvider.getFriends(userId);
                return MSGBuilder.success(MSG.MsgCode.Get_Friends_Response, friends);
        }

        URL serverUrl = (URL) channel.getAttribute("serverUrl");
        if (Check.isNull(serverUrl)) {
            logger.error("游戏消息ServerUrl不能为空, topic : " + topic + " userId : " + userId + " channel : " + channel);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "尚未注册到游戏服务器, 请先加入牌桌");
        }

        // 直接到斗地主模块
        if (msgCode.equals(MSG.MsgCode.Landlord_CS)) {
            ClientMessage.CS cs = reqMsg.getCs();
            byte[] bytes = landlordProvider.process(cs.toByteArray(), userId, topic);
            ServerMessage.SC sc = null;
            try {
                sc = ServerMessage.SC.parseFrom(bytes);
            } catch (InvalidProtocolBufferException e) {
                logger.error("SC 解析错误, CS : " + cs + " userId : " + userId + " topic : " + topic);
                throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "SC 解析错误");
            }
            return MSG.Message.newBuilder().setMsgCode(MSG.MsgCode.Landlord_SC).setSc(sc);
        }

        // 路由游戏消息
        switch (msgCode) {
            case Ddz_Call_Lord_Request:
                REQ.DdzCallLordRequest ddzCallLord = request.getDdzCallLord();
                int mark = ddzCallLord.getMark();
                deskProvider.callLord(serverUrl, userId, mark);
                return MSGBuilder.success(MSG.MsgCode.Success_Response);
            case Ddz_Play_Card_Request:
                REQ.DdzPlayCardRequest ddzPlayCard = request.getDdzPlayCard();
                byte[] cards = ddzPlayCard.getCards().toByteArray();
                deskProvider.play(serverUrl, userId, cards);
                return MSGBuilder.success(MSG.MsgCode.Success_Response);
            default:
                logger.error("未知的消息类型, MsgCode : " + msgCode);
                throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "未知的消息类型");
        }
    }

    public void disconnect(long userId) {
        loginProvider.logout(userId);
    }
}
