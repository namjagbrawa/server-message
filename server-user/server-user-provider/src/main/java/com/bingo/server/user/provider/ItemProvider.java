package com.bingo.server.user.provider;

import com.bingo.server.database.model.Item;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface ItemProvider {

    List<Item> getItems(List<Long> itemIds);

    Item getItem(long itemId);

    Item add(String name, String detail, String tip, String price);

    Item update(long itemId, String name, String detail, String tip, String price);

    void del(long itemId);
}
