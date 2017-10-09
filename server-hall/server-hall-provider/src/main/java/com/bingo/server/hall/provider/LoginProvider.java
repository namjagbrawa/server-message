package com.bingo.server.hall.provider;

import com.bingo.framework.common.URL;
import com.bingo.server.msg.REQ;

/**
 * Created by ZhangGe on 2017/7/24.
 */
public interface LoginProvider {

    // 注册用户，初始化用户信息及相关信息
    long registry(String username, String password);

    // 用户登录，用户在线，获取topic
    void login(String username, String password, long topic, URL siteUrl);

    // 微信用户登录
    long weixinLogin(String code, long topic, URL siteUrl);

    // 登出
    void logout(long userId);
}
