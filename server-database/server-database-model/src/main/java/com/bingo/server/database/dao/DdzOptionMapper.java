package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzOption;
import com.bingo.server.database.model.DdzOptionExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzOptionMapper extends SuperMapper {
    long countByExample(DdzOptionExample example);

    int deleteByExample(DdzOptionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzOption record);

    int insertSelective(DdzOption record);

    List<DdzOption> selectByExample(DdzOptionExample example);

    DdzOption selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzOption record, @Param("example") DdzOptionExample example);

    int updateByExample(@Param("record") DdzOption record, @Param("example") DdzOptionExample example);

    int updateByPrimaryKeySelective(DdzOption record);

    int updateByPrimaryKey(DdzOption record);
}