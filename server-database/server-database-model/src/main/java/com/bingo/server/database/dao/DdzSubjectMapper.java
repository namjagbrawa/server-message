package com.bingo.server.database.dao;

import com.bingo.server.database.model.DdzSubject;
import com.bingo.server.database.model.DdzSubjectExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DdzSubjectMapper extends SuperMapper {
    long countByExample(DdzSubjectExample example);

    int deleteByExample(DdzSubjectExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DdzSubject record);

    int insertSelective(DdzSubject record);

    List<DdzSubject> selectByExample(DdzSubjectExample example);

    DdzSubject selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DdzSubject record, @Param("example") DdzSubjectExample example);

    int updateByExample(@Param("record") DdzSubject record, @Param("example") DdzSubjectExample example);

    int updateByPrimaryKeySelective(DdzSubject record);

    int updateByPrimaryKey(DdzSubject record);
}