package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuBag;
import com.bingo.server.database.model.CuBagExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuBagMapper extends SuperMapper {
    long countByExample(CuBagExample example);

    int deleteByExample(CuBagExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuBag record);

    int insertSelective(CuBag record);

    List<CuBag> selectByExample(CuBagExample example);

    CuBag selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuBag record, @Param("example") CuBagExample example);

    int updateByExample(@Param("record") CuBag record, @Param("example") CuBagExample example);

    int updateByPrimaryKeySelective(CuBag record);

    int updateByPrimaryKey(CuBag record);
}