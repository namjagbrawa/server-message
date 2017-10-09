package com.bingo.server.user.provider;

import com.bingo.server.database.model.CuRoomType;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface CuRoomTypeProvider {

    CuRoomType addRoomType(String name, String detail, String tip, int upperLimit, int lowerLimit);

    void updateRoomType(CuRoomType cuRoomType);

    void delete(long roomTypeId);

    CuRoomType get(long roomTypeId);

}
