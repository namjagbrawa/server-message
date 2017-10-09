package com.bingo.server.user.provider;

import com.bingo.server.database.model.CuFriend;
import com.bingo.server.database.model.CuUser;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/13.
 */
public interface CuFriendProvider {

    List<CuFriend> getFriends(long userId);

    List<CuUser> getUsers(long userId);

    void addFriend(long userId, long friendUserId);

    void delFreiend(long userId, long friendUserId);

    Map<Long, CuUser> getUserMap(long userId);

}
