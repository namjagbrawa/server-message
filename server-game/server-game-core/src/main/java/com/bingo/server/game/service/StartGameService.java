package com.bingo.server.game.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.*;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.ddz.service.CardService;
import com.bingo.server.game.provider.*;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.DeskStatus;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.game.provider.ddz.DeskProvider;
import com.bingo.server.msg.BASE;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.NOTIFY;
import com.bingo.server.msg.RESP;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.user.provider.CuWalletProvider;
import com.bingo.server.utils.Assert;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.RegistryCenter;
import com.google.protobuf.ByteString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class StartGameService implements StartGameProvider {
    private final Logger logger = LoggerFactory.getLogger(StartGameService.class);

    @Autowired
    private CuUserProvider cuUserProvider;
    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;
    @Autowired
    private DdzRuleProvider ddzRuleProvider;
    @Autowired
    private DdzDeskProvider ddzDeskProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private SendToService sendToService;
    @Autowired
    private DdzGameHistoryProvider ddzGameHistoryProvider;
    @Autowired
    private CardService cardService;
    @Autowired
    private DeskProvider deskProvider;
    @Autowired
    private CuWalletProvider cuWalletProvider;

    @Override
    public RESP.DdzConfirmRuleResponse confirmRule(long userId, String deskName, String rule) {
        Assert.hasText(deskName, "牌桌名称不能为空");
        Assert.hasText(rule, "牌桌规则不能为空");

        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
        if (Check.isNull(onlineUser)) {
            logger.error("用户不在线, userId : " + userId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        }

        String[] options = rule.split(",");
        if (Check.isNull(options)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "规则配置错误, rule : " + rule);
        }
        DdzRule ddzRule = ddzRuleProvider.parseRule(Arrays.asList(options));
        if (Check.isNull(ddzRule)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "规则解析错误, rule : " + rule);
        }

        DdzUser ddzUser = ddzUserProvider.getById(userId);
        if (!Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户已在牌桌, ddzUser : " + ddzUser);
        }

        CuWallet wallet = cuWalletProvider.getWallet(userId);
        if (Check.isNull(wallet)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户钱包不存在");
        }
        Integer roomCard = wallet.getRoomCard();
        int useCard = ddzRule.getRoomCard();
        if (roomCard < useCard) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户房卡数小于房间需要房卡数, roomCard : " + roomCard + " useCard : " + useCard);
        }

        DdzDesk ddzDesk = ddzDeskProvider.newDesk(deskName, useCard, ddzRule);
        ddzUserProvider.addDdzUser(userId, ddzDesk.getId(), onlineUser.getSiteUrl(), UserStatus.wait);

        ddzUser = ddzUserProvider.addDdzUser(userId, ddzDesk.getId(), onlineUser.getSiteUrl(), UserStatus.wait);
        if (Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌已满");
        }

        RESP.DdzConfirmRuleResponse.Builder ddzConfirmRule = RESP.DdzConfirmRuleResponse.newBuilder();
        ddzConfirmRule.setDeskId(ddzDesk.getId()).setName(ddzDesk.getName());
        return ddzConfirmRule.build();
    }

    @Override
    public RESP.DdzCheckInDeskResponse checkInDesk(long userId) {
        DdzUser ddzUser = ddzUserProvider.getById(userId);
        RESP.DdzCheckInDeskResponse.Builder checkInDesk = RESP.DdzCheckInDeskResponse.newBuilder();
        if (Check.isNull(ddzUser)) {
            checkInDesk.setIn(false);
        } else {
            checkInDesk.setIn(true);
            checkInDesk.setDeskId(ddzUser.getDeskId());
        }
        return checkInDesk.build();
    }

    @Override
    public RESP.EnterDeskResponse enterDesk(long userId, int deskNo) {
        Assert.isNull(deskNo, "牌桌号不能为空");
        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
        if (Check.isNull(onlineUser)) {
            logger.error("用户不在线, userId : " + userId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        }

        DdzUser ddzUser = ddzUserProvider.getById(userId);
        if (!Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户已在牌桌, ddzUser : " + ddzUser);
        }

        DdzDesk ddzDesk = ddzDeskProvider.getDeskByDeskNo(deskNo);
        if (Check.isNull(ddzDesk)) {
            logger.error("牌桌不存在, deskNo : " + deskNo);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌不存在");
        }

        ddzUser = ddzUserProvider.addDdzUser(userId, ddzDesk.getId(), onlineUser.getSiteUrl(), UserStatus.wait);
        if (Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌已满");
        }

        BASE.Desk desk = deskStatus(ddzDesk);

        // 通知到用户
        sendToService.send(MSG.MsgCode.Ddz_Desk_Status_Notification,
                NOTIFY.DdzDeskStatusNotification.newBuilder().setDesk(deskStatus(ddzDesk)).build(),
                ddzDesk.getId(), ddzUser.getId());

        RESP.EnterDeskResponse.Builder enterDesk = RESP.EnterDeskResponse.newBuilder().setDesk(desk);
        return enterDesk.build();
    }

    /**
     * 用户断线重连, 需要重写, 现在掉线时是等待, 所以, 不存在此问题
     */
    @Override
    public RESP.ReconnectDeskResponse reconnect(long userId, long deskId) {
        Assert.isNull(deskId, "牌桌ID不能为空");
        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
        if (Check.isNull(onlineUser)) {
            logger.error("用户不在线, userId : " + userId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        }

        DdzUser ddzUser = ddzUserProvider.getById(userId);
        if (Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在牌桌");
        }

        DdzDesk ddzDesk = ddzDeskProvider.getDeskById(deskId);
        if (Check.isNull(ddzDesk)) {
            logger.error("牌桌不存在, deskId : " + deskId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌不存在");
        }

        ddzUser.setStatus(ddzDesk.getStatus());
        ddzUserProvider.update(ddzUser);

        BASE.Desk desk = deskStatus(ddzDesk);
        // 通知到用户
        sendToService.send(MSG.MsgCode.Ddz_Desk_Status_Notification,
                NOTIFY.DdzDeskStatusNotification.newBuilder().setDesk(deskStatus(ddzDesk)).build(),
                ddzDesk.getId(), ddzUser.getId());

        RESP.ReconnectDeskResponse.Builder reconnect = RESP.ReconnectDeskResponse.newBuilder().setDesk(desk);
        List<DdzGameHistory> lastestOperation = ddzGameHistoryProvider.getLastestOperation(deskId, ddzDesk.getRound(), 3);
        if (!Check.isNull(lastestOperation)) {
            List<BASE.DeskOperation> operations = operationHistory(lastestOperation);
            reconnect.addAllHistory(operations);
        }
        // TODO 当前状态
        reconnect.setDesk(desk);

        return reconnect.build();
    }

    @Override
    public void ready(long userId, long deskId) {
        Assert.isNull(deskId, "牌桌ID不能为空");

        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
        if (Check.isNull(onlineUser)) {
            logger.error("用户不在线, userId : " + userId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        }

        DdzUser ddzUser = ddzUserProvider.getById(userId);
        if (Check.isNull(ddzUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在牌桌, userId : " + userId);
        }

        DdzDesk ddzDesk = ddzDeskProvider.getDeskById(deskId);
        if (Check.isNull(ddzDesk)) {
            logger.error("牌桌不存在, deskId : " + deskId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "牌桌不存在");
        }

        ddzUser.setStatus(UserStatus.ready.name());
        ddzUserProvider.update(ddzUser);

        // 通知到用户
        sendToService.send(MSG.MsgCode.Ddz_Desk_Status_Notification,
                NOTIFY.DdzDeskStatusNotification.newBuilder().setDesk(deskStatus(ddzDesk)).build(),
                ddzDesk.getId());

        // 如果全部准备好,则开桌
        boolean allReady = ddzUserProvider.allReady(deskId);
        if (allReady) {
            ddzDesk.setStatus(DeskStatus.ready.name());
            URL url = updateServerUrl(ddzDesk);
            deskProvider.initDesk(url, ddzDesk);
        }
        // TODO 用户点击准备按钮,设置用户状态为准备,判断牌桌是不是三个人都准备了,发送准备消息到其他用户, 应该放在game模块,不初始化Desk,
        // 全程使用DdzBuddyDesk进行操作,只有所有用户都完成准备了,再初始化Desk和User
        // 启动倒计时5分钟,如果用户不准备,则剔除未准备的用户

        // 全部准备后,开始游戏
    }

    // 开始发牌,负载选择服务器,更新牌桌和用户的ServerUrl
    private URL updateServerUrl(DdzDesk ddzDesk) {
        URL url = RegistryCenter.select(DeskProvider.class);
        ddzDesk.setServerUrl(url.getAddress());
        ddzDeskProvider.update(ddzDesk);
        ddzUserProvider.addServerUrl(url, ddzDesk.getId());
        return url;
    }

    // 封装Desk当前状态消息
    private BASE.Desk deskStatus(DdzDesk ddzDesk) {
        BASE.Desk.Builder deskBuilder = BASE.Desk.newBuilder();
        List<DdzUser> ddzUsers = ddzUserProvider.getByDeskId(ddzDesk.getId());
        if (!Check.isNull(ddzDesk)) {
            List<Long> userIds = new ArrayList<>();
            for (DdzUser ddzUser : ddzUsers) {
                userIds.add(ddzUser.getId());
            }
            Map<Long, CuUser> userMap = cuUserProvider.getUserMap(userIds);
            for (DdzUser ddzUser : ddzUsers) {
                BASE.User.Builder userBuilder = BASE.User.newBuilder();
                Long userId = ddzUser.getId();
                CuUser cuUser = userMap.get(userId);
                userBuilder.setUserId(userId);
                userBuilder.setNickName(cuUser.getNickName());
                userBuilder.setImgUrl(cuUser.getHeadImgUrl());
                userBuilder.setStatus(ddzUser.getStatus());
                userBuilder.setScore(ddzUser.getScore());
                userBuilder.setSite(ddzUser.getSite());
                deskBuilder.addUsers(userBuilder.build());
            }
        }
        deskBuilder.setDeskId(ddzDesk.getId());
        deskBuilder.setDeskName(ddzDesk.getName());
        deskBuilder.setRule(ddzDesk.getRule());
        deskBuilder.setRound(ddzDesk.getRound());
        return deskBuilder.build();
    }

    private List<BASE.DeskOperation> operationHistory(List<DdzGameHistory> ddzGameHistories) {
        List<BASE.DeskOperation> operations = new ArrayList<>();
        for (DdzGameHistory ddzGameHistory : ddzGameHistories) {
            // TODO 重连状态恢复
            /*BASE.DeskOperation.Builder deskOperation = BASE.DeskOperation.newBuilder();
            deskOperation.setSiteCards1(ByteString.copyFrom(cardToByte(ddzGameHistory.getSiteCard1())));
            deskOperation.setSiteCards2(ByteString.copyFrom(cardToByte(ddzGameHistory.getSiteCard2())));
            deskOperation.setSiteCards3(ByteString.copyFrom(cardToByte(ddzGameHistory.getSiteCard3())));
            deskOperation.setPullSite(ddzGameHistory.getPullSite());
            deskOperation.setPullCards(ddzGameHistory.getPullCard());
            deskOperation.setSiteRemain1(ddzGameHistory.());
            operations.add(deskOperation.build());*/
        }
        return operations;
    }

    private byte[] cardToByte(String cards) {
        if (Check.isNull(cards)) {
            return null;
        }
        String[] split = cards.split(",");
        byte[] bytes = new byte[split.length];
        for (int i = 0 ;i < split.length; i++) {
            bytes[i] = Byte.valueOf(split[i]);
        }
        return bytes;
    }

    /**
     * 用户自动匹配进入比赛(暂未实现)
     *
     * @param userId
     * @param openNumber
     * @return
     */
    public void ent(String serviceUrl, String topic, String userId, String roomId, int openNumber) {
        /*User enums = iUserProvider.getUser(String.valueOf(userId));
        if (enums == null) {
            logger.error("用户不存在，userId: " + userId);
            throw new HallException("用户不存在。");
        }

        Room room = roomMapper.selectByPrimaryKey(Long.valueOf(roomId));
        if (room == null) {
            logger.error("房间不存在，roomId: " + roomId);
            throw new HallException("房间不存在。");
        }

        if (enums.getCurrgold() < room.getEnterpoint()) {
            logger.error("筹码不足，enums: " + enums + "room: " + room);
            throw new HallException("筹码不足。");
        }

        if (enums.getCurrgold() > room.getEnterpointuplimit()) {
            logger.error("筹码大于了入局上限，enums: " + enums + "room: " + room);
            throw new HallException("筹码大于了入局上限。");
        }

        // 从缓存查询用户是否已经在牌桌中
        BoundHashOperations<String, String, String> boundHashOperations = stringRedisTemplate.boundHashOps(PLAYER_TO_DESKID_CACHE_KEY);
        String existUser = boundHashOperations.get(String.valueOf(userId));
        if (existUser != null) {
            logger.warn("用户已在牌桌，deskId: " + existUser);
            throw new HallException("用户已在牌桌，请退出后再新建。");
        }

        BoundListOperations<String, String> listOperations = stringRedisTemplate.boundListOps(PLAYER_QUEUE_CACHE_KEY);
        listOperations.leftPush(userId);

        // 分布式锁获取牌桌玩家
        List<String> userIds = new ArrayList<>(openNumber);
        if (redisLock == null) {
            redisLock = new RedisLock(REDIS_DISTRIBUTE_KEY, "hall", new RedisClient(jedisPool));
        }
        try {
            if (redisLock.lock(3, 5)) {
                if (listOperations.size() >= openNumber) {
                    for (int i = 0; i < openNumber; i++) {
                        userIds.add(listOperations.rightPop(3, TimeUnit.SECONDS));
                    }
                }
            }
        } finally {
            redisLock.unlock();
        }

        List<User> users = new ArrayList<>(openNumber);
        for (String id : userIds) {
            User record = iUserProvider.getUser(String.valueOf(id));
            if (record == null) {
                logger.error("用户不存在，userId: " + userId);
                // TODO 继续匹配新的用户
                // throw new HallException("用户不存在。");
                for (int i = 0; i < CACHE_POP_RETRY_TIMES; i++) {
                    String cacheUserId = listOperations.rightPop(3, TimeUnit.SECONDS);
                    record = iUserProvider.getUser(cacheUserId);
                    if (record != null) {
                        break;
                    }
                }
            }
            users.add(enums);
        }

        if (users.size() < openNumber) {
            logger.error("读取用户队列失败，不够可开局人数, openNumber : " + openNumber + "users.size : " + users.size());
            throw new HallException("读取用户队列失败，不够可开局人数");
        }


        // 新建牌桌，写入缓存
        String deskId = String.valueOf(IDGen.get().nextId());
        Desk desk = new Desk();
        desk.setDeskId(Long.valueOf(deskId));
        desk.setRoomId(Long.valueOf(roomId));
        desk.setBasicPoint(room.getBasicpoint());
        desk.setEnterPoint(room.getEnterpoint());
        desk.setTaxType(String.valueOf(room.getTaxtype()));
        desk.setTaxValue(Float.floatToIntBits(room.getTaxvalue()));
        desk.setDeskStatus("随机牌桌" + new Date());

        // 查询牌桌服务器及服务器牌桌负载
        String deskUrl = playServerService.selectServer();
        playServerService.addDeskIdToUrl(deskId, deskUrl);

        // TODO 如果用户不够，返回，等待，加入机器人等
        iNewPlayProvider.enterDeskRandom(deskUrl, serviceUrl, topic, desk, room, users);*/
    }

}
