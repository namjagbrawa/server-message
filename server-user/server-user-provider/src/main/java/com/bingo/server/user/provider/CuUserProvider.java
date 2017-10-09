package com.bingo.server.user.provider;

import com.bingo.server.database.model.CuUser;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/13.
 */
public interface CuUserProvider {

    CuUser getUserById(long userId);

    CuUser getUserByPhone(String phone);

    CuUser getUserByUsername(String username);

    CuUser authUser(String username, String password);

    CuUser registryUser(String username, String password);

    void updatePassword(long userId, String password);

    List<CuUser> getUsers(List<Long> userIds);

    Map<Long, CuUser> getUserMap(List<Long> userIds);

    CuUser authWeixinUser(CuUser cuUser);
}
