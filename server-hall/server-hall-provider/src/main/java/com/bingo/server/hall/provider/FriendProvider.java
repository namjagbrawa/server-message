package com.bingo.server.hall.provider;

import com.bingo.server.msg.RESP;

/**
 * Created by ZhangGe on 2017/7/22.
 */
public interface FriendProvider {

    RESP.GetFriendsResponse getFriends(long userId);

}
