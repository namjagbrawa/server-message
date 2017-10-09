package com.bingo.server.hall.provider;

import com.bingo.server.database.model.CuBag;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.utils.Assert;
import com.bingo.server.utils.Check;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface BagProvider {

    void buyItem(long userId, long itemId, int count);

    CuBag addItem(long userId, long itemId, int count);

    List<CuBag> getItems(long userId);

    List<CuBag> addCount(long userId, long itemId, int count);
}
