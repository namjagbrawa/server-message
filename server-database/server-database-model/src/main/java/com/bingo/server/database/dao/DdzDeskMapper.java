package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzDesk;
import com.bingo.server.database.model.DdzDeskExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzDeskMapper extends SuperMapper {
    long countByExample(DdzDeskExample example);

    int deleteByExample(DdzDeskExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzDesk record);

    int insertSelective(DdzDesk record);

    List<DdzDesk> selectByExample(DdzDeskExample example);

    DdzDesk selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzDesk record, @Param("example") DdzDeskExample example);

    int updateByExample(@Param("record") DdzDesk record, @Param("example") DdzDeskExample example);

    int updateByPrimaryKeySelective(DdzDesk record);

    int updateByPrimaryKey(DdzDesk record);
}