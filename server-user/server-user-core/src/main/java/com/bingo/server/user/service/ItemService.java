package com.bingo.server.user.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.ItemMapper;
import com.bingo.server.database.model.Item;
import com.bingo.server.database.model.ItemExample;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.ItemProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class ItemService implements ItemProvider {

    private static Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<Item> getItems(List<Long> itemIds) {
        System.err.println(itemIds);
        ItemExample itemExample = new ItemExample();
        itemExample.createCriteria().andIdIn(itemIds);
        List<Item> items = itemMapper.selectByExample(itemExample);
        if (Check.isNull(items)) {
            return null;
        }
        return items;
    }

    @Override
    public Item getItem(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public Item add(String name, String detail, String tip, String price) {
        Item item = new Item();
        item.setId(IDGen.getId());
        item.setName(name);
        item.setDetail(detail);
        item.setTip(tip);
        item.setPrice(new BigDecimal(price));
        item.setCreateTime(new Date());
        itemMapper.insert(item);
        return item;
    }

    @Override
    public Item update(long itemId, String name, String detail, String tip, String price) {
        Item item = getItem(itemId);
        if (Check.isNull(item)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "物品不存在, itemId : " + itemId);
        }
        item.setName(name);
        item.setDetail(detail);
        item.setTip(tip);
        item.setPrice(new BigDecimal(price));
        item.setModifyTime(new Date());
        itemMapper.updateByPrimaryKey(item);
        return item;
    }

    @Override
    public void del(long itemId) {
        Item item = getItem(itemId);
        if (Check.isNull(item)) {
            logger.error("物品不存在, itemId : " + itemId);
            return;
        }
        itemMapper.deleteByPrimaryKey(itemId);
    }
}
