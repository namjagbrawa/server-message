package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuRoomType;
import com.bingo.server.database.model.CuRoomTypeExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuRoomTypeMapper extends SuperMapper {
    long countByExample(CuRoomTypeExample example);

    int deleteByExample(CuRoomTypeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuRoomType record);

    int insertSelective(CuRoomType record);

    List<CuRoomType> selectByExample(CuRoomTypeExample example);

    CuRoomType selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuRoomType record, @Param("example") CuRoomTypeExample example);

    int updateByExample(@Param("record") CuRoomType record, @Param("example") CuRoomTypeExample example);

    int updateByPrimaryKeySelective(CuRoomType record);

    int updateByPrimaryKey(CuRoomType record);
}