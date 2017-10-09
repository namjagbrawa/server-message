package com.bingo.server.user.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.CuUserMapper;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.CuUserExample;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.bean.enums.UserType;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/11.
 */
@Service
public class CuUserService implements CuUserProvider {

    private static Logger logger = LoggerFactory.getLogger(CuUserService.class);

    @Autowired
    private CuUserMapper cuUserMapper;

    @Override
    public CuUser getUserById(long userId) {
        return cuUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public CuUser getUserByPhone(String phone) {
        CuUserExample cuUserExample = new CuUserExample();
        cuUserExample.createCriteria().andPhoneEqualTo(phone);
        List<CuUser> cuUsers = cuUserMapper.selectByExample(cuUserExample);
        if (Check.isNull(cuUsers)) {
            return null;
        } else if (cuUsers.size() >= 2) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户手机号有重复, phone : " + phone + " users : " + cuUsers);
        } else {
            return cuUsers.get(0);
        }
    }

    @Override
    public CuUser getUserByUsername(String username) {
        CuUserExample cuUserExample = new CuUserExample();
        cuUserExample.createCriteria().andUsernameEqualTo(username);
        List<CuUser> cuUsers = cuUserMapper.selectByExample(cuUserExample);
        if (Check.isNull(cuUsers)) {
            return null;
        } else if (cuUsers.size() >= 2) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户名有重复, username : " + username + " users : " + cuUsers);
        } else {
            return cuUsers.get(0);
        }
    }

    @Override
    public CuUser authUser(String username, String password) {
        CuUserExample cuUserExample = new CuUserExample();
        cuUserExample.createCriteria().andUsernameEqualTo(username);
        List<CuUser> cuUsers = cuUserMapper.selectByExample(cuUserExample);
        if (Check.isNull(cuUsers)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户名不存在, username : " + username + " password : " + password);
        } else if (cuUsers.size() >= 2) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "存在多个用户, username : " + username + " password : " + password + " cuUsers : " + cuUsers);
        } else {
            CuUser cuUser = cuUsers.get(0);
            if (cuUser.getPasswordMd5().equals(MD5.str2MD5(password))) {
                return cuUser;
            } else {
                throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户密码不正确，请重试, username : " + username + " password : " + password);
            }
        }
    }

    @Override
    public CuUser registryUser(String username, String password) {
        CuUser userByUsername = getUserByUsername(username);
        if (!Check.isNull(userByUsername)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "用户名有重复.");
        }
        CuUser cuUser = new CuUser();
        cuUser.setUserType(UserType.common.name());
        cuUser.setId(IDGen.getId());
        cuUser.setCreateTime(new Date());
        cuUser.setPassword(password);
        cuUser.setPasswordMd5(MD5.str2MD5(password));
        cuUser.setUsername(username);
        cuUserMapper.insert(cuUser);
        return cuUser;
    }

    @Override
    public void updatePassword(long userId, String password) {
        CuUser cuUser = cuUserMapper.selectByPrimaryKey(userId);
        if (Check.isNull(cuUser)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "用户不存在, userId : " + userId);
        } else {
            cuUser.setPassword(password);
            cuUser.setPasswordMd5(MD5.str2MD5(password));
            cuUserMapper.updateByPrimaryKeySelective(cuUser);
        }
    }

    @Override
    public List<CuUser> getUsers(List<Long> userIds) {
        CuUserExample cuUserExample = new CuUserExample();
        cuUserExample.createCriteria().andIdIn(userIds);
        List<CuUser> cuUsers = cuUserMapper.selectByExample(cuUserExample);
        if (Check.isNull(cuUsers)) {
            return null;
        }
        return cuUsers;
    }

    @Override
    public Map<Long, CuUser> getUserMap(List<Long> userIds) {
        List<CuUser> cuUsers = getUsers(userIds);
        if (Check.isNull(cuUsers)) {
            return null;
        }
        Map<Long, CuUser> cuUserMap = new HashMap<>();
        for (CuUser cuUser : cuUsers) {
            cuUserMap.put(cuUser.getId(), cuUser);
        }
        return cuUserMap;
    }

    @Override
    public CuUser authWeixinUser(CuUser cuUser) {
        String openId = cuUser.getOpenId();
        if (Check.isNull(openId)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "OpenId不能为为空");
        }
        CuUserExample cuUserExample = new CuUserExample();
        cuUserExample.createCriteria().andOpenIdEqualTo(openId).andUserTypeEqualTo(UserType.weixin.name());
        List<CuUser> cuUsers = cuUserMapper.selectByExample(cuUserExample);
        if (Check.isNull(cuUsers)) {
            cuUser.setId(IDGen.getId());
            cuUser.setUserType(UserType.weixin.name());
            cuUser.setCreateTime(new Date());
            cuUserMapper.insert(cuUser);
        } else if (cuUsers.size() >= 2) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "OpenId有重复, openId : " + openId + " cuUser : " + cuUser + " users : " + cuUsers);
        } else {
            CuUser cuUserDB = cuUsers.get(0);
            if (cuUserDB.getDisable().equals(Boolean.TRUE)) {
                throw new ServerException(MSG.MsgCode.ACCOUNT_DISABLE_ERROR, "您的账号已被封停,如有疑问,请联系客服");
            }
            cuUser.setId(cuUserDB.getId());
            cuUser.setUserType(cuUserDB.getUserType());
            cuUser.setCreateTime(cuUserDB.getCreateTime());
            cuUserMapper.updateByPrimaryKeySelective(cuUser);
        }
        return cuUser;
    }

}
