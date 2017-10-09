package com.bingo.server.database.dao;

import com.bingo.server.database.mybatis.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhangGe on 2017/7/26.
 */
public interface SpecDdzDeskMapper extends SuperMapper{

    int getUniqueDeskNo(@Param("deskNo") int deskNo, @Param("deskId") long deskId);

}
