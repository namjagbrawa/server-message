package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuWallet;
import com.bingo.server.database.model.CuWalletExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuWalletMapper extends SuperMapper {
    long countByExample(CuWalletExample example);

    int deleteByExample(CuWalletExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuWallet record);

    int insertSelective(CuWallet record);

    List<CuWallet> selectByExample(CuWalletExample example);

    CuWallet selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuWallet record, @Param("example") CuWalletExample example);

    int updateByExample(@Param("record") CuWallet record, @Param("example") CuWalletExample example);

    int updateByPrimaryKeySelective(CuWallet record);

    int updateByPrimaryKey(CuWallet record);
}