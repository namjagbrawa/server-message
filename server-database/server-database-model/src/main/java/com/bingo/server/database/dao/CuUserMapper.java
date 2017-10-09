package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuUser;
import com.bingo.server.database.model.CuUserExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuUserMapper extends SuperMapper {
    long countByExample(CuUserExample example);

    int deleteByExample(CuUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuUser record);

    int insertSelective(CuUser record);

    List<CuUser> selectByExample(CuUserExample example);

    CuUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuUser record, @Param("example") CuUserExample example);

    int updateByExample(@Param("record") CuUser record, @Param("example") CuUserExample example);

    int updateByPrimaryKeySelective(CuUser record);

    int updateByPrimaryKey(CuUser record);
}