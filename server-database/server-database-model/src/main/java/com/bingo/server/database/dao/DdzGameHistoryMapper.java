package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzGameHistory;
import com.bingo.server.database.model.DdzGameHistoryExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzGameHistoryMapper extends SuperMapper {
    long countByExample(DdzGameHistoryExample example);

    int deleteByExample(DdzGameHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzGameHistory record);

    int insertSelective(DdzGameHistory record);

    List<DdzGameHistory> selectByExample(DdzGameHistoryExample example);

    DdzGameHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzGameHistory record, @Param("example") DdzGameHistoryExample example);

    int updateByExample(@Param("record") DdzGameHistory record, @Param("example") DdzGameHistoryExample example);

    int updateByPrimaryKeySelective(DdzGameHistory record);

    int updateByPrimaryKey(DdzGameHistory record);
}