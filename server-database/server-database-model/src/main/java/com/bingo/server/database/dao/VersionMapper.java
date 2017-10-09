package com.bingo.server.database.dao;

import com.bingo.server.database.model.Version;
import com.bingo.server.database.model.VersionExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface VersionMapper extends SuperMapper {
    long countByExample(VersionExample example);

    int deleteByExample(VersionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Version record);

    int insertSelective(Version record);

    List<Version> selectByExample(VersionExample example);

    Version selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Version record, @Param("example") VersionExample example);

    int updateByExample(@Param("record") Version record, @Param("example") VersionExample example);

    int updateByPrimaryKeySelective(Version record);

    int updateByPrimaryKey(Version record);
}