package com.bingo.server.database.dao;

import java.util.List;

/**
 * Created by ZhangGe on 2017/8/10.
 */
public interface BaseMapper<T> {
    T get(T var1);

    void insert(T var1);

    void update(T var1);

    List<T> selectList(int var1);
}

