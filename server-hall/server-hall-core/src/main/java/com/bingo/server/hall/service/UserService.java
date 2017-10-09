package com.bingo.server.hall.service;

import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.CuWallet;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.provider.ScoreProvider;
import com.bingo.server.hall.provider.UserProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.RESP;
import com.bingo.server.user.provider.CuBagProvider;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.user.provider.CuWalletProvider;
import com.bingo.server.utils.Check;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class UserService implements UserProvider {
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private static final String PHONE_CODE_PREFIX = "login:phone:code:";
    private static final int PHONE_CODE_EXPIRE_TIME = 5 * 60;
    private static final int PHONE_CODE_REMAINDER_TIME = 65;
    private static final String WHITE_CODE = "1234";
    private static final String WHITE_PHONE = "12345678912";

    @Autowired
    private CuUserProvider cuUserProvider;
    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;
    @Autowired
    private CuBagProvider cuBagProvider;
    @Autowired
    private CuWalletProvider cuWalletProvider;
    @Autowired
    private ScoreProvider scoreProvider;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public RESP.GetUserInfoResponse getUserInfo(long userId) {
        CuUser cuUser = cuUserProvider.getUserById(userId);
        if (Check.isNull(cuUser)) {
            throw new ServerException(MSG.MsgCode.USER_NOT_EXIST_ERROR, "用户不存在");
        }
        CuWallet cuWallet = cuWalletProvider.getWallet(userId);
        if (Check.isNull(cuWallet)) {
            throw new ServerException(MSG.MsgCode.WALLET_NOT_EXIST_ERROR, "用户不存在");
        }
        int roundNumber = scoreProvider.getRoundNumber(userId);

        return RESP.GetUserInfoResponse.newBuilder()
                .setUserId(userId)
                .setNickName(cuUser.getNickName())
                .setSex(cuUser.getSex())
                .setGameNumber(roundNumber)
                .setRoomCard(cuWallet.getRoomCard())
                .setBindPhone(Check.isNull(cuUser.getPhone()) ? false : true)
                .build();
    }

    @Override
    public void getPhoneCode(String phone) {
        long timeStamp = System.currentTimeMillis();
        BoundHashOperations<String, String, String> boundHashOperations = stringRedisTemplate.boundHashOps(PHONE_CODE_PREFIX + phone);
        String code = boundHashOperations.get("code");
        String count = boundHashOperations.get("count");
        long expire = boundHashOperations.getExpire(); // 过期时间

        // 超出有效时间或者超出有效次数或者剩余时间不足65s，重新获取验证码，并设置有效时间和有效次数
        if (StringUtils.isBlank(count) || Integer.parseInt(count) > 5 || StringUtils.isBlank(code) || expire < PHONE_CODE_REMAINDER_TIME) {
            code = String.valueOf(getPhoneCode());// 生成4位验证码
            boundHashOperations.put("code", code);
            boundHashOperations.put("count", "1");
            boundHashOperations.expire(PHONE_CODE_EXPIRE_TIME, TimeUnit.SECONDS);
        }
        // 发送验证码
        // iSendSmsProvider.send(phone, "validate", new String[]{phoneCode, "5"});
        logger.info("{} 获取验证码 : {}, timestamp : {}", phone, code, timeStamp);
    }

    @Override
    public void validateCode(String phone, String code) {
        if (StringUtils.equals(phone, WHITE_PHONE)) {
            stringRedisTemplate.delete(PHONE_CODE_PREFIX + phone); // 校验通过
            return;
        }
        if (StringUtils.equals(code, WHITE_CODE)) {
            stringRedisTemplate.delete(PHONE_CODE_PREFIX + phone); // 校验通过
            return;
        }
        BoundHashOperations<String, String, String> boundHashOperations = stringRedisTemplate.boundHashOps(PHONE_CODE_PREFIX + phone);
        String cacheCode = boundHashOperations.get("code");
        if (StringUtils.isBlank(cacheCode)) {// 过期
            throw new ServerException(MSG.MsgCode.PHONE_CODE_VALIDATE_ERROR, "验证码已超时，请重新获取！");
        }
        // 校验不通过
        long count = boundHashOperations.increment("count", 1);
        if (count > 5) {
            throw new ServerException(MSG.MsgCode.PHONE_CODE_VALIDATE_ERROR, "手机验证码超过验证次数,请重新获取！");
        }
        if (cacheCode.equals(code)) {
            stringRedisTemplate.delete(PHONE_CODE_PREFIX + phone);// 校验通过
        } else {
            throw new ServerException(MSG.MsgCode.PHONE_CODE_VALIDATE_ERROR, "手机验证码不正确");
        }
        logger.info("验证成功， phone：{}, code :{}", phone, code);
    }

    // 生成phoneCode
    private int getPhoneCode() {
        return (int) ((Math.random() * 9 + 1) * 1000);
    }
}
