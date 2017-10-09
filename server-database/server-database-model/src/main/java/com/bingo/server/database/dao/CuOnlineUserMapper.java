package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuOnlineUser;
import com.bingo.server.database.model.CuOnlineUserExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuOnlineUserMapper extends SuperMapper {
    long countByExample(CuOnlineUserExample example);

    int deleteByExample(CuOnlineUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuOnlineUser record);

    int insertSelective(CuOnlineUser record);

    List<CuOnlineUser> selectByExample(CuOnlineUserExample example);

    CuOnlineUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuOnlineUser record, @Param("example") CuOnlineUserExample example);

    int updateByExample(@Param("record") CuOnlineUser record, @Param("example") CuOnlineUserExample example);

    int updateByPrimaryKeySelective(CuOnlineUser record);

    int updateByPrimaryKey(CuOnlineUser record);
}