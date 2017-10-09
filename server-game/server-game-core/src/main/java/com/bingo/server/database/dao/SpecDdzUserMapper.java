package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzUser;
import com.bingo.server.database.mybatis.SuperMapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by ZhangGe on 2017/7/26.
 */
public interface SpecDdzUserMapper extends SuperMapper{

    int addUser(DdzUser ddzUser);

}
