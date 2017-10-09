package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzType;
import com.bingo.server.database.model.DdzTypeExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzTypeMapper extends SuperMapper {
    long countByExample(DdzTypeExample example);

    int deleteByExample(DdzTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzType record);

    int insertSelective(DdzType record);

    List<DdzType> selectByExample(DdzTypeExample example);

    DdzType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzType record, @Param("example") DdzTypeExample example);

    int updateByExample(@Param("record") DdzType record, @Param("example") DdzTypeExample example);

    int updateByPrimaryKeySelective(DdzType record);

    int updateByPrimaryKey(DdzType record);
}