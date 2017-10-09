package com.bingo.server.user.service;


import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.CuFriendMapper;
import com.bingo.server.database.model.CuFriend;
import com.bingo.server.database.model.CuFriendExample;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuFriendProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ZhangGe on 2017/7/11.
 */
@Service
public class CuFriendService implements CuFriendProvider {

    private static Logger logger = LoggerFactory.getLogger(CuFriendService.class);

    @Autowired
    private CuFriendMapper cuFriendMapper;
    @Autowired
    private CuUserService cuUserProvider;

    @Override
    public List<CuFriend> getFriends(long userId) {
        CuFriendExample cFriendExample = new CuFriendExample();
        cFriendExample.createCriteria().andCuUserIdEqualTo(userId);
        List<CuFriend> cFriends = cuFriendMapper.selectByExample(cFriendExample);
        if (Check.isNull(cFriends)) {
            return null;
        }
        return cFriends;
    }

    @Override
    public List<CuUser> getUsers(long userId) {
        List<CuFriend> cFriends = getFriends(userId);
        if (Check.isNull(cFriends)) {
            return null;
        }
        List<Long> userIds = new ArrayList<>();
        for (CuFriend cuFriend : cFriends) {
            userIds.add(cuFriend.getCuFriendUserId());
        }
        List<CuUser> users = cuUserProvider.getUsers(userIds);
        if (Check.isNull(users)) {
            return null;
        }
        return users;
    }

    @Override
    public void addFriend(long userId, long friendUserId) {
        CuUser user = cuUserProvider.getUserById(userId);
        if (Check.isNull(user)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户不存在, userId : " + userId);
        }
        CuUser friend = cuUserProvider.getUserById(friendUserId);
        if (Check.isNull(friend)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "好友用户不存在, userId : " + friendUserId);
        }
        CuFriendExample cuFriendExample = new CuFriendExample();
        cuFriendExample.createCriteria().andCuUserIdEqualTo(userId).andCuFriendUserIdEqualTo(friendUserId);
        List<CuFriend> cuFriends = cuFriendMapper.selectByExample(cuFriendExample);
        if (Check.isNull(cuFriends)) {
            CuFriend cuFriend = new CuFriend();
            cuFriend.setId(IDGen.getId());
            cuFriend.setCreateTime(new Date());
            cuFriend.setCuFriendUserId(friendUserId);
            cuFriend.setCuUserId(userId);
            cuFriend.setModifyTime(new Date());
            cuFriendMapper.insert(cuFriend);
        } else if (cuFriends.size() >= 2) {
            logger.error("好友关系添加重复，请检查，userId : {}, friendUserId : {}, cuFriends : {}", userId, friendUserId, cuFriends);
        } else {
            logger.warn("用户已添加为好友, userId : {}, friendUserId : {}", userId, friendUserId);
        }
    }

    @Override
    public void delFreiend(long userId, long friendUserId) {
        CuFriendExample cuFriendExample = new CuFriendExample();
        cuFriendExample.createCriteria().andCuUserIdEqualTo(userId).andCuFriendUserIdEqualTo(friendUserId);
        List<CuFriend> cuFriends = cuFriendMapper.selectByExample(cuFriendExample);
        if (Check.isNull(cuFriends)) {
            logger.warn("好友关系不存在, userId : {}, friendUserId : {}", userId, friendUserId);
        } else if (cuFriends.size() >= 2) {
            logger.error("好友关系重复，请检查，userId : {}, friendUserId : {}, cuFriends : {}", userId, friendUserId, cuFriends);
        } else {
            cuFriendMapper.deleteByExample(cuFriendExample);
        }
    }

    @Override
    public Map<Long, CuUser> getUserMap(long userId) {
        List<CuUser> users = getUsers(userId);
        if (Check.isNull(users)) {
            return null;
        }
        Map<Long, CuUser> userMap = new HashMap<>();
        for (CuUser user : users) {
            userMap.put(user.getId(), user);
        }
        return userMap;
    }
}
