package com.bingo.server.user.provider;

import com.bingo.framework.common.URL;
import com.bingo.server.database.model.CuOnlineUser;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/13.
 */
public interface CuOnlineUserProvider {

    void online(long userId, long topic, URL siteUrl);

    void offline(long userId);

    long getTopic(long userId);

    CuOnlineUser getOnlineUser(long userId);

    void logout(long userId);

    Map<Long, CuOnlineUser> getOnlineUsers(List<Long> userIds);

}
