package com.bingo.server.hall.service;

import com.bingo.server.database.model.CuBag;
import com.bingo.server.exception.ServerException;
import com.bingo.server.hall.provider.BagProvider;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuBagProvider;
import com.bingo.server.utils.Assert;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class BagService implements BagProvider {

    private final Logger logger = LoggerFactory.getLogger(BagService.class);

    @Autowired
    private CuBagProvider cuBagProvider;

    public void buyItem(long userId, long itemId, int count) {
        // TODO 获取物品，获取物品价格，获取物品剩余数量，向用户确定，支付，确定购买物品
    }

    @Transactional
    public CuBag addItem(long userId, long itemId, int count) {
        Assert.isNull(userId, "用户ID不能为空");
        Assert.isNull(itemId, "物品ID不能为空");
        CuBag cuBag = cuBagProvider.addItem(userId, itemId, count);
        if (Check.isNull(cuBag)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "物品添加失败, userId : "+ userId + " itemId : " + itemId + " count : " + count);
        }
        return cuBag;
    }

    public List<CuBag> getItems(long userId) {
        return cuBagProvider.getBags(userId);
    }

    @Transactional
    public List<CuBag> addCount(long userId, long itemId, int count) {
        CuBag cuBag = cuBagProvider.addCount(userId, itemId, count);
        if (!Check.isNull(cuBag)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "增加物品失败");
        }
        return getItems(userId);
    }

}
