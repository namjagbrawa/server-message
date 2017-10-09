package com.bingo.server.database.dao;

import com.bingo.server.database.model.HallBroadcast;
import com.bingo.server.database.model.HallBroadcastExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HallBroadcastMapper extends SuperMapper {
    long countByExample(HallBroadcastExample example);

    int deleteByExample(HallBroadcastExample example);

    int deleteByPrimaryKey(Long id);

    int insert(HallBroadcast record);

    int insertSelective(HallBroadcast record);

    List<HallBroadcast> selectByExample(HallBroadcastExample example);

    HallBroadcast selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") HallBroadcast record, @Param("example") HallBroadcastExample example);

    int updateByExample(@Param("record") HallBroadcast record, @Param("example") HallBroadcastExample example);

    int updateByPrimaryKeySelective(HallBroadcast record);

    int updateByPrimaryKey(HallBroadcast record);
}