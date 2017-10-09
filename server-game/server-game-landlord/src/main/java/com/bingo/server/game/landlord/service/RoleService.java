package com.bingo.server.game.landlord.service;

import com.bingo.server.database.dao.RoleMapper;
import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.CuWallet;
import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.game.landlord.util.Tool;
import com.bingo.server.game.landlord.util.base.StringUtils;
import com.bingo.server.msg.Entity;
import com.bingo.server.user.provider.CuOnlineUserProvider;
import com.bingo.server.user.provider.CuUserProvider;
import com.bingo.server.user.provider.CuWalletProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/8/10.
 */
@Service
public class RoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    private MoneyExchangeProvider moneyExchangeService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private CuUserProvider cuUserProvider;
    @Autowired
    private CuWalletProvider cuWalletProvider;
    @Autowired
    private CuOnlineUserProvider cuOnlineUserProvider;

    public Role createRole(long userId, long topic) {
        CuUser cuUser = cuUserProvider.getUserById(userId);
        CuWallet wallet = cuWalletProvider.getWallet(userId);
        CuOnlineUser onlineUser = cuOnlineUserProvider.getOnlineUser(userId);

        // 创建用户
        Role role = new Role();
        role.setUsername(cuUser.getUsername());
        role.setId(cuUser.getId());
        role.setHeadImgUrl(cuUser.getHeadImgUrl());
        role.setMoney(wallet.getBean());
        role.setSiteUrl(onlineUser.getSiteUrl());
        role.setTopic(onlineUser.getTopic());

        newRoleInit(role);
        moneyExchangeService.newRoleInit(role);

        Role roleDb = roleMapper.selectByPrimaryKey(userId);
        if (Check.isNull(roleDb)) {
            roleMapper.insert(role);
        } else {
            roleMapper.updateByPrimaryKey(role);
        }
        RoleCache.putNewRole(role); // 存入缓存
        return role;
    }

    public void newRoleInit(Role role) {
        // 设置战场的第一章
        role.setNickName("用户" + role.getId());

        initRoleDataFromHttp(role);
    }

    public void initRoleDataFromHttp(Role role) {
        int money = -1;
        String name = "";
        String headImgUrl = "";

        role.setNickName(StringUtils.isNullOrEmpty(name) ? "guest" + role.getId() : name);
        System.out.println("@@@" + headImgUrl + (headImgUrl == null));
        role.setHeadImgUrl(StringUtils.isNullOrEmpty(headImgUrl) ? "ui://h24q1ml0x7tz13m" : headImgUrl);
        role.setSpecialMoney(money);
    }


    public Entity.RoleData getRoleData(Role role) {
        Entity.RoleData.Builder roleDataBuilder = Entity.RoleData
                .newBuilder()
                .setRoleId(
                        (Tool.regExpression(role.getUsername(), "[0-9]*") ? Integer.parseInt(role.getUsername()) : role
                                .getId())).setName(role.getNickName()).setMoney(role.getMoney())
                .setSpecialMoney(role.getSpecialMoney()).setHeadImgUrl(role.getHeadImgUrl());

        return roleDataBuilder.build();
    }

}
