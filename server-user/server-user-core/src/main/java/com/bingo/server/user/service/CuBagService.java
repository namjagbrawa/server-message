package com.bingo.server.user.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.CuBagMapper;
import com.bingo.server.database.dao.SpecCuBagMapper;
import com.bingo.server.database.model.CuBag;
import com.bingo.server.database.model.CuBagExample;
import com.bingo.server.database.model.Item;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuBagProvider;
import com.bingo.server.user.provider.ItemProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/13.
 */
@Service
public class CuBagService implements CuBagProvider {

    private static Logger logger = LoggerFactory.getLogger(CuBagService.class);

    @Autowired
    private CuBagMapper cuBagMapper;
    @Autowired
    private ItemProvider itemProvider;
    @Autowired
    private SpecCuBagMapper specCuBagMapper;


    @Override
    public List<CuBag> getBags(long userId) {
        CuBagExample cuBagExample = new CuBagExample();
        cuBagExample.createCriteria().andCuUserIdEqualTo(userId);
        List<CuBag> cuBags = cuBagMapper.selectByExample(cuBagExample);
        if (cuBags == null || cuBags.size() <= 0) {
            return null;
        }
        return cuBags;
    }

    @Override
    public CuBag getBag(long userId, long itemId) {
        CuBagExample cuBagExample = new CuBagExample();
        cuBagExample.createCriteria().andCuUserIdEqualTo(userId).andItemIdEqualTo(itemId);
        List<CuBag> cuBags = cuBagMapper.selectByExample(cuBagExample);
        if (Check.isNull(cuBags)) {
            return null;
        } else if (cuBags.size() >= 2) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "相同物品相同用户存在重复条目, userId : " + userId + " itemId : " + itemId + "cuBags : " + cuBags);
        } else {
            return cuBags.get(0);
        }
    }

    @Override
    public CuBag addCount(long userId, long itemId, int count) {
        CuBag bag = getBag(userId, itemId);
        if (Check.isNull(bag)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "背包物品不存在, userId : " + userId + " itemId : " + itemId);
        }
        specCuBagMapper.modifyItemCount(userId, itemId, count);
        return getBag(userId, itemId);
    }

    @Override
    public CuBag addItem(long userId, long itemId, int count) {
        Item item = itemProvider.getItem(itemId);
        if (Check.isNull(item)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "物品不存在, itemId : " + itemId);
        }
        CuBag bag = getBag(userId, itemId);
        if (!Check.isNull(bag)) {
            logger.warn("物品已存在, bag : " + bag);
            bag.setCount(count);
            cuBagMapper.updateByPrimaryKey(bag);
            return bag;
        } else {
            CuBag cuBag = new CuBag();
            cuBag.setId(IDGen.getId());
            cuBag.setCuUserId(userId);
            cuBag.setItemId(itemId);
            cuBag.setCount(count);
            cuBag.setCreateTime(new Date());
            cuBag.setName(item.getName());
            cuBag.setDetail(item.getDetail());
            cuBag.setTip(item.getTip());
            cuBagMapper.insert(cuBag);
            return cuBag;
        }
    }
}
