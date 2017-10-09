package com.bingo.server.game.ddz.service;

import com.bingo.framework.common.URL;
import com.bingo.framework.common.utils.JSONUtil;
import com.bingo.server.database.model.DdzDesk;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.bean.User;
import com.bingo.server.game.bean.enums.CardType;
import com.bingo.server.game.ddz.CardUtil;
import com.bingo.server.game.ddz.DeskUtil;
import com.bingo.server.game.handler.CallLordHandler;
import com.bingo.server.game.handler.SnatchLordHandler;
import com.bingo.server.game.manager.DeskManager;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.DeskStatus;
import com.bingo.server.game.provider.bean.enums.DeskType;
import com.bingo.server.game.provider.bean.enums.Operation;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.game.provider.ddz.DeskProvider;
import com.bingo.server.game.service.SendToService;
import com.bingo.server.gate.provider.GateDeskProvider;
import com.bingo.server.gate.provider.NotificationProvider;
import com.bingo.server.msg.BASE;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.NOTIFY;
import com.bingo.server.user.provider.CuWalletProvider;
import com.bingo.server.utils.Check;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/26.
 */
@Service
public class DeskService implements DeskProvider {

    private final Logger logger = LoggerFactory.getLogger(DeskService.class);

    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private NotificationProvider notificationProvider;
    @Autowired
    private GateDeskProvider gateDeskProvider;
    @Autowired
    private DealService dealService;
    @Autowired
    private SendToService sendToService;
    @Autowired
    private CuWalletProvider cuWalletProvider;
    @Autowired
    private Map<String, CallLordHandler> callLordHandlerMap;
    @Autowired
    private Map<String, SnatchLordHandler> snatchLordHandlerMap;
    @Autowired
    private CardService cardService;


    public void initDesk(URL url, DdzDesk ddzDesk) {
        Desk desk = new Desk();
        desk.setCreateTime(new Date());
        List<DdzUser> ddzUsers = ddzUserProvider.getByDeskId(ddzDesk.getId());
        List<User> users = toUser(ddzUsers);
        desk.setUsers(users);
        desk.setDdzRule(JSONUtil.toBean(ddzDesk.getRule(), DdzRule.class));
        desk.setDeskName(ddzDesk.getName());
        desk.setDeakDetail(ddzDesk.getDetail());
        desk.setDeskTip(ddzDesk.getTip());
        desk.setServerUrl(URL.valueOf(ddzDesk.getServerUrl()));
        desk.setDeskId(ddzDesk.getId());
        desk.setDeskType(DeskType.buddy);
        desk.setDeskStatus(DeskStatus.valueOf(ddzDesk.getStatus()));
        DeskManager.get().put(desk, users);

        // 通知Gate用户所在游戏服务器
        notifyGateServerUrl(desk.getServerUrl(), users);
        // 扣除房卡
        substractRoomCard(desk);
        // 发牌
        deal(desk);
    }

    public void callLord(URL url, long userId, int callNumber) {
        Desk desk = DeskManager.get().getByUser(userId);
        if (!desk.getDeskStatus().equals(DeskStatus.callLord)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌不是叫地主状态, Desk : " + desk);
        }
        int nextChair = desk.getNextChair();

        int userChairId = getUserChairId(desk, userId);
        if (userChairId == 0) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户不在牌桌, Desk : " + desk + " userId : " + userId);
        }
        if (userChairId != nextChair) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "不该此用户叫地主 : Desk " + desk + " userId : " + userId + " nextChair : " + nextChair);
        }
        desk.setCallTime(desk.getCallTime() + 1); // 增加叫地主次数
        desk.setLastCallMark(callNumber);
        desk.setLastCallChair(userChairId);

        DdzRule ddzRule = desk.getDdzRule();
        snatchLordHandlerMap.get(ddzRule.getSnatchLord().name()).mark(desk, callNumber, userChairId);

        if (desk.isRedeal()) {
            desk.setNextCallMark(null);
            desk.setNextChair(0);
            desk.setNoCallTime(0);
            desk.setMaxCallMark(0);
            desk.setMaxChairId(0);
            desk.setCallTime(0);
            desk.setLastCallChair(0);
            desk.setLastCallMark(0);
            desk.setDeskStatus(DeskStatus.redeal);
            // 重新发牌
            deal(desk);
            desk.setRedeal(false);
            desk.setDeskStatus(DeskStatus.callLord);
            return;
        }
        if (desk.getLordChair() > 0) {
            desk.setDeskStatus(DeskStatus.start);
            startGame(desk);
            return;
        }
        notifyUserStatus(desk);
    }

    public void play(URL url, long userId, byte[] cards) {
        Desk desk = DeskManager.get().getByUser(userId);
        if (!desk.getDeskStatus().equals(DeskStatus.start)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌不是出牌状态, Desk : " + desk);
        }
        int nextChair = desk.getNextChair();

        int userChairId = getUserChairId(desk, userId);
        if (userChairId == 0) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户不在牌桌, Desk : " + desk + " userId : " + userId);
        }
        if (userChairId != nextChair) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "不该此用户出牌 : Desk " + desk + " userId : " + userId + " nextChair : " + nextChair);
        }

        DdzRule ddzRule = desk.getDdzRule();
        if (Check.isNull(cards)) {
            // TODO 玩家没有出牌
        } else {
            CardType cardType = cardService.getCardType(ddzRule, CardUtil.sortCards(cards));
            cardService.playCard(desk.getUsers().get(userChairId), cards);
            if (cardType.equals(CardType.Super_Boom_Card)) {
                desk.setRocketCount(desk.getRocketCount() + 1);
            }
            if (cardType.equals(CardType.Four_Card)) {
                desk.setBombCount(desk.getBombCount() + 1);
            }
        }
        desk.setCurrentChair(nextChair);
        desk.setNextChair(DeskUtil.clockwise(nextChair, 3));
        desk.setCurrentCards(cards);
        desk.setCurrentUserId(userId);
        playCard(desk);

        if (Check.isNull(desk.getUsers().get(desk.getCurrentChair()).getCards())) {

        }
    }

    private void playCard(Desk desk) {
        NOTIFY.DdzPlayCardNotification.Builder playCard = NOTIFY.DdzPlayCardNotification.newBuilder();
        playCard.setUserId(desk.getCurrentUserId());
        playCard.setCards(ByteString.copyFrom(desk.getCurrentCards()));
        playCard.setRemain(desk.getUsers().get(desk.getCurrentChair()).getCards().length);
        playCard.setChair(desk.getCurrentChair());
        sendToService.send(MSG.MsgCode.Ddz_Play_Card_Notification, playCard.build(), desk.getUsers());
    }



    private void startGame(Desk desk) {
        desk.setNextChair(desk.getMaxChairId());
        notifyUserStatus(desk);
    }

    private int getUserChairId(Desk desk, long userId) {
        List<User> users = desk.getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId() == userId) {
                return i;
            }
        }
        return 0;
    }

    // 扣除房卡
    private void substractRoomCard(Desk desk) {
        if (!desk.getDeskStatus().equals(DeskStatus.ready)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "扣除房卡,当前牌桌状态不是准备状态,DeskStatus : " + desk.getDeskStatus());
        }
        for (User user : desk.getUsers()) {
            if (user.getChairId() == 1) {
                boolean result = cuWalletProvider.substractRoomCard(user.getUserId(), desk.getDdzRule().getRoomCard());
                if (!result) {
                    logger.error("开桌时用户房卡不足, user : " + user);
                    throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户房卡不足,请充值后再开始游戏");
                }
                return;
            }
        }
        throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "没有房主的牌桌, 错误请验证, Desk : " + desk);
    }

    private void deal(Desk desk) {
        dealService.deal(desk);

        // 定义下一步斗地主操作状态
        defineCallLord(desk);

        desk.setDeskStatus(DeskStatus.callLord);
        notifyUserStatus(desk);
    }

    private void defineCallLord(Desk desk) {
        desk.setDeskStatus(DeskStatus.callLord);
        // 谁叫地主
        DdzRule ddzRule = desk.getDdzRule();
        Operation callLord = ddzRule.getCallLord();
        callLordHandlerMap.get(callLord.name()).startCall(desk);
        snatchLordHandlerMap.get(ddzRule.getSnatchLord().name()).nextMark(desk);
    }

    private void notifyUserStatus(Desk desk) {
        List<Integer> siteRemain = new ArrayList<>();
        for (User user : desk.getUsers()) {
            siteRemain.add(user.getCards().length);
        }
        for (User user : desk.getUsers()) {
            BASE.UserStatus.Builder userStatus = BASE.UserStatus.newBuilder();
            userStatus.setCards(ByteString.copyFrom(user.getCards()));
            userStatus.setUserId(user.getUserId());
            userStatus.setDeskId(desk.getDeskId());
            userStatus.setSite(user.getSite());
            userStatus.addAllRemain(siteRemain);
            userStatus.setNextSite(desk.getNextChair());
            userStatus.setDeskStatus(desk.getDeskStatus().name());
            userStatus.setNextCallMark(ByteString.copyFrom(desk.getNextCallMark()));
            userStatus.setLastCallMark(desk.getLastCallMark());
            userStatus.setLastCallChair(desk.getLastCallChair());
            sendToService.send(MSG.MsgCode.Ddz_User_Notification,
                    NOTIFY.DdzUserNotification.newBuilder().setUserStatus(userStatus).build(),
                    user.getTopic(), user.getUrl());
        }
    }

    private void notifyGateServerUrl(URL serverUrl, List<User> users) {
        for (User user : users) {
            gateDeskProvider.inDesk(user.getUrl(), serverUrl, user.getTopic());
            // TODO 如果报错,进入此用户disconnect处理
        }
    }

    private List<User> toUser(List<DdzUser> ddzUsers) {
        List<User> users = new ArrayList<>();
        for (DdzUser ddzUser : ddzUsers) {
            users.add(toUser(ddzUser));
        }
        return users;
    }

    private User toUser(DdzUser ddzUser) {
        User user = new User();
        user.setUserId(ddzUser.getId());
        user.setTopic(ddzUser.getTopic());
        user.setUrl(URL.valueOf(ddzUser.getSiteUrl()));
        user.setUserStatus(UserStatus.wait);
        user.setSite(ddzUser.getSite());
        return user;
    }
}
