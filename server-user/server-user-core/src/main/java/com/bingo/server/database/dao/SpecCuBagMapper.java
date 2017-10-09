package com.bingo.server.database.dao;

import com.bingo.server.database.mybatis.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhangGe on 2017/8/2.
 */
public interface SpecCuBagMapper extends SuperMapper{

    int modifyItemCount(@Param("userId") long userId, @Param("itemId") long itemId, @Param("count") int count);

}
