package com.bingo.server.user.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.dao.CuOnlineUserMapper;
import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.CuOnlineUserExample;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/13.
 */
@Service
public class CuOnlineUserService implements CuOnlineUserProvider {

    private static Logger logger = LoggerFactory.getLogger(CuOnlineUserService.class);

    @Autowired
    private CuUserService cuUserService;
    @Autowired
    private CuOnlineUserMapper cuOnlineUserMapper;
    @Autowired
    private CuUserProvider cuUserProvider;

    @Override
    public void online(long userId, long topic, URL siteUrl) {
        CuUser user = cuUserService.getUserById(userId);
        if (Check.isNull(user)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "没有此用户, userId : " + userId);
        }
        CuOnlineUser cuOnlineUser = cuOnlineUserMapper.selectByPrimaryKey(userId);
        if (Check.isNull(cuOnlineUser)) {
            CuOnlineUser newOnline = new CuOnlineUser();
            newOnline.setId(userId);
            newOnline.setOnlineTime(new Date());
            newOnline.setTopic(topic);
            newOnline.setSiteUrl(siteUrl.getAddress());
            cuOnlineUserMapper.insert(newOnline);
        } else {
            if (Check.isNull(cuOnlineUser.getTopic())) {
                cuOnlineUser.setTopic(topic);
                cuOnlineUser.setSiteUrl(siteUrl.getAddress());
                cuOnlineUser.setOnlineTime(new Date());
                cuOnlineUserMapper.updateByPrimaryKeySelective(cuOnlineUser);
            } else {
                logger.warn("用户已在线，更新topic, DB : {}, new Topic : {}, new SiteUrl : {}", cuOnlineUser, topic, siteUrl);
                cuOnlineUser.setTopic(topic);
                cuOnlineUser.setSiteUrl(siteUrl.getAddress());
                cuOnlineUser.setOnlineTime(new Date());
                cuOnlineUserMapper.updateByPrimaryKeySelective(cuOnlineUser);
            }
        }
    }

    @Override
    public void offline(long userId) {
        CuOnlineUser cuOnlineUser = cuOnlineUserMapper.selectByPrimaryKey(userId);
        if (Check.isNull(cuOnlineUser)) {
            logger.error("用户不在线，userId : {}", userId);
        } else {
            cuOnlineUserMapper.deleteByPrimaryKey(userId);
        }
    }

    @Override
    public long getTopic(long userId) {
        CuOnlineUser cuOnlineUser = cuOnlineUserMapper.selectByPrimaryKey(userId);
        if (Check.isNull(cuOnlineUser) || Check.isNull(cuOnlineUser.getTopic())) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        } else {
            return cuOnlineUser.getTopic();
        }
    }

    @Override
    public CuOnlineUser getOnlineUser(long userId) {
        CuOnlineUser cuOnlineUser = cuOnlineUserMapper.selectByPrimaryKey(userId);
        if (Check.isNull(cuOnlineUser) || Check.isNull(cuOnlineUser.getTopic())) {
            logger.error("用户不在线, userId : " + userId);
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户不在线");
        } else {
            return cuOnlineUser;
        }
    }

    @Override
    public void logout(long userId) {
        cuOnlineUserMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public Map<Long, CuOnlineUser> getOnlineUsers(List<Long> userIds) {
        CuOnlineUserExample cuOnlineUserExample = new CuOnlineUserExample();
        cuOnlineUserExample.createCriteria().andIdIn(userIds);
        List<CuOnlineUser> cuOnlineUsers = cuOnlineUserMapper.selectByExample(cuOnlineUserExample);
        if (Check.isNull(cuOnlineUsers)) {
            return  null;
        }
        Map<Long ,CuOnlineUser> cuOnlineUserMap = new HashMap<>();
        for (CuOnlineUser cuOnlineUser : cuOnlineUsers) {
            cuOnlineUserMap.put(cuOnlineUser.getId(), cuOnlineUser);
        }
        return cuOnlineUserMap;

    }
}
