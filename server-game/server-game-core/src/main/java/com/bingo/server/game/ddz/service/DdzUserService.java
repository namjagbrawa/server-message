package com.bingo.server.game.ddz.service;

import com.bingo.framework.common.URL;
import com.bingo.server.database.dao.DdzUserMapper;
import com.bingo.server.database.model.DdzUser;
import com.bingo.server.database.model.DdzUserExample;
import com.bingo.server.database.dao.SpecDdzUserMapper;
import com.bingo.server.game.provider.DdzUserProvider;
import com.bingo.server.game.provider.bean.enums.UserStatus;
import com.bingo.server.utils.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/22.
 */
@Service
public class DdzUserService implements DdzUserProvider {

    @Autowired
    private DdzUserMapper ddzUserMapper;
    @Autowired
    private SpecDdzUserMapper specDdzUserMapper;

    @Override
    public DdzUser addDdzUser(long userId, long deskId, String siteUrl, UserStatus userStatus) {
        DdzUser ddzUser = new DdzUser();
        ddzUser.setId(userId);
        ddzUser.setDeskId(deskId);
        ddzUser.setCreateTime(new Date());
        ddzUser.setStatus(userStatus.name());
        ddzUser.setSiteUrl(siteUrl);
        int row = specDdzUserMapper.addUser(ddzUser);
        if (row <= 0) {
            return null;
        }
        return ddzUser;
    }

    @Override
    public void addServerUrl(URL url, List<Long> userIds) {
        DdzUser ddzUser = new DdzUser();
        ddzUser.setServerUrl(url.getAddress());
        DdzUserExample ddzUserExample = new DdzUserExample();
        ddzUserExample.createCriteria().andIdIn(userIds);
        ddzUserMapper.updateByExampleSelective(ddzUser, ddzUserExample);
    }

    @Override
    public void addServerUrl(URL url, long deskId) {
        DdzUser ddzUser = new DdzUser();
        ddzUser.setServerUrl(url.getAddress());
        DdzUserExample ddzUserExample = new DdzUserExample();
        ddzUserExample.createCriteria().andDeskIdEqualTo(deskId);
        ddzUserMapper.updateByExampleSelective(ddzUser, ddzUserExample);
    }

    @Override
    public void update(DdzUser ddzUser) {
        ddzUserMapper.updateByPrimaryKey(ddzUser);
    }

    @Override
    public boolean allReady(long deskId) {
        DdzUserExample ddzUserExample = new DdzUserExample();
        ddzUserExample.createCriteria().andDeskIdEqualTo(deskId).andStatusEqualTo(UserStatus.ready.name());
        List<DdzUser> ddzUsers = ddzUserMapper.selectByExample(ddzUserExample);
        if (Check.isNull(ddzUsers)) {
            return false;
        } else if (ddzUsers.size() < 3) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<DdzUser> getUsers(List<Long> userId) {
        DdzUserExample ddzUserExample = new DdzUserExample();
        ddzUserExample.createCriteria().andIdIn(userId);
        return ddzUserMapper.selectByExample(ddzUserExample);
    }

    @Override
    public Map<Long, DdzUser> getUserMap(List<Long> userIds) {
        List<DdzUser> ddzUsers = getUsers(userIds);
        if (Check.isNull(ddzUsers)) {
            return null;
        }
        Map<Long, DdzUser> ddzUserMap = new HashMap<>();
        for (DdzUser ddzUser : ddzUsers) {
            ddzUserMap.put(ddzUser.getId(), ddzUser);
        }
        return ddzUserMap;
    }

    @Override
    public DdzUser getById(long userId) {
        return ddzUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<DdzUser> getByDeskId(long deskId) {
        DdzUserExample ddzUserExample = new DdzUserExample();
        ddzUserExample.createCriteria().andDeskIdEqualTo(deskId);
        List<DdzUser> ddzUsers = ddzUserMapper.selectByExample(ddzUserExample);
        return ddzUsers;
    }

}
