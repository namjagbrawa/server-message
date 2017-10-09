package com.bingo.server.hall.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.hall.provider.LoginProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.utils.Assert;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/13.
 */
@Service
public class LoginService implements LoginProvider {

    private final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    private CuUserProvider cuUserProvider;
    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;
    @Autowired
    private WeixinService weixinService;

    // 注册用户，初始化用户信息及相关信息
    public long registry(String username, String password) {
        Assert.hasText(username, "用户名不能为空");
        Assert.hasText(password, "密码不能为空");

        CuUser cuUserByUsername = cuUserProvider.getUserByUsername(username);
        if (!Check.isNull(cuUserByUsername)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户名已存在，请更换用户名");
        }

        // TODO 用户名/密码后台校验
        CuUser cuUser = cuUserProvider.registryUser(username, password);
        if (Check.isNull(cuUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户注册失败");
        }
        // TODO 初始化用户背包等信息，或者等使用时再初始化

       return cuUser.getId();
    }

    // 用户登录，用户在线，获取topic
    public void login(String username, String password, long topic, URL siteUrl) {
        Assert.hasText(username, "用户名不能为空");
        Assert.hasText(password, "密码不能为空");
        Assert.isNull(topic, "Topic不能为空");
        Assert.isNull(siteUrl, "SiteUrl不能为空");

        CuUser cuUser;
        try {
            cuUser = cuUserProvider.authUser(username, password);
        } catch (ServerException e) {
            logger.error("用户名密码校验错误", e);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户名密码错误，请确认后重试");
        }
        try {
            cuOnlineUserProvider.online(cuUser.getId(), topic, siteUrl);
        } catch (ServerException e) {
            logger.error("用户在线失败", e);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户在线失败，请重试");
        }
    }

    // 微信用户登录
    public long weixinLogin(String code, long topic, URL siteUrl) {
        Assert.hasText(code, "微信Code不能为空");
        Assert.isNull(topic, "Topic不能为空");
        Assert.isNull(siteUrl, "SiteUrl不能为空");

        CuUser weinxinUser = weixinService.getWeixinUser(code);
        CuUser cuUser = cuUserProvider.authWeixinUser(weinxinUser);
        cuOnlineUserProvider.online(cuUser.getId(), topic, siteUrl);

        return cuUser.getId();
    }

    public void logout(long userId) {
        Assert.isNull(userId, "UserId不能为空");
        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);
        if (Check.isNull(onlineUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        }
        cuOnlineUserProvider.logout(userId);
    }
}
