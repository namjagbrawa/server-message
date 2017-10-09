package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzUser;
import com.bingo.server.database.model.DdzUserExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzUserMapper extends SuperMapper {
    long countByExample(DdzUserExample example);

    int deleteByExample(DdzUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzUser record);

    int insertSelective(DdzUser record);

    List<DdzUser> selectByExample(DdzUserExample example);

    DdzUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzUser record, @Param("example") DdzUserExample example);

    int updateByExample(@Param("record") DdzUser record, @Param("example") DdzUserExample example);

    int updateByPrimaryKeySelective(DdzUser record);

    int updateByPrimaryKey(DdzUser record);
}