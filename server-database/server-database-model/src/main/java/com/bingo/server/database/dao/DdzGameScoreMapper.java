package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzGameScore;
import com.bingo.server.database.model.DdzGameScoreExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzGameScoreMapper extends SuperMapper {
    long countByExample(DdzGameScoreExample example);

    int deleteByExample(DdzGameScoreExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzGameScore record);

    int insertSelective(DdzGameScore record);

    List<DdzGameScore> selectByExample(DdzGameScoreExample example);

    DdzGameScore selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzGameScore record, @Param("example") DdzGameScoreExample example);

    int updateByExample(@Param("record") DdzGameScore record, @Param("example") DdzGameScoreExample example);

    int updateByPrimaryKeySelective(DdzGameScore record);

    int updateByPrimaryKey(DdzGameScore record);
}