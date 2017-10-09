package com.bingo.server.user.provider;

import com.bingo.server.database.model.CuBag;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/13.
 */
public interface CuBagProvider {

    List<CuBag> getBags(long userId);

    CuBag getBag(long userId, long itemId);

    CuBag addCount(long userId, long itemId, int count);

    CuBag addItem(long userId, long itemId, int count);

}
