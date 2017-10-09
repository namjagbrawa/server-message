package com.bingo.server.hall.service;

import com.bingo.server.database.model.CuFriend;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.game.provider.ScoreProvider;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.hall.provider.FriendProvider;
import com.bingo.server.msg.BASE;
import com.bingo.server.msg.RESP;
import com.bingo.server.user.provider.CuFriendProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ZhangGe on 2017/7/22.
 */
@Service
public class FriendService implements FriendProvider {
    private final Logger logger = LoggerFactory.getLogger(FriendService.class);

    @Autowired
    private CuFriendProvider cuFriendProvider;
    @Autowired
    private ScoreProvider scoreProvider;
    @Autowired
    private DdzUserProvider ddzUserProvider;
    @Autowired
    private CuUserProvider cuUserProvider;

    @Override
    public RESP.GetFriendsResponse getFriends(long userId) {
        List<CuFriend> friends = cuFriendProvider.getFriends(userId);
        if (Check.isNull(friends)) {
            return RESP.GetFriendsResponse.newBuilder().build(); // 没有好友
        }

        List<Long> userIds = new ArrayList<>();
        for (CuFriend cuFriend : friends) {
            userIds.add(cuFriend.getCuUserId());
        }
        Map<Long, CuUser> cuUserMap = cuUserProvider.getUserMap(userIds);
        Map<Long, DdzUser> ddzUserMap = ddzUserProvider.getUserMap(userIds);
        Map<Long, Integer> scoreMap = scoreProvider.getScoreMap(userIds);

        List<BASE.Friend> sortFriends = new ArrayList<>();
        for (CuUser cuUser : cuUserMap.values()) {
            BASE.Friend.Builder friend = BASE.Friend.newBuilder();
            Long id = cuUser.getId();
            friend.setUserId(cuUser.getId());
            friend.setNickName(cuUser.getNickName());
            friend.setImgUrl(cuUser.getHeadImgUrl());
            if (!Check.isNull(ddzUserMap.get(id))) {
                friend.setStatus(ddzUserMap.get(id).getStatus());
            } else {
                friend.setStatus(UserStatus.free.name());
            }
            if (!Check.isNull(scoreMap.get(id))) {
                friend.setWin(scoreMap.get(id));
            } else {
                friend.setWin(0);
            }
            sortFriends.add(friend.build());
        }

        sortFriendAtInvite(sortFriends);
        return RESP.GetFriendsResponse.newBuilder().addAllFriends(sortFriends).build();
    }

    private void sortFriendAtHall(List<BASE.Friend> notSortFriends) {
        Collections.sort(notSortFriends, new Comparator<BASE.Friend>() {
            @Override
            public int compare(BASE.Friend o1, BASE.Friend o2) {
                return o1.getNickName().compareTo(o2.getNickName());
            }
        });
    }

    private void sortFriendAtInvite(List<BASE.Friend> notSortFriends) {
        Collections.sort(notSortFriends, new Comparator<BASE.Friend>() {
            @Override
            public int compare(BASE.Friend o1, BASE.Friend o2) {
                if (o1.getStatus().equals(UserStatus.free)) {
                    return 1;
                } else {
                    return o1.getNickName().compareTo(o2.getNickName());
                }
            }
        });
    }
}
