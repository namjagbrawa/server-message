package com.bingo.server.database.dao;

import com.bingo.server.database.model.CuFriend;
import com.bingo.server.database.model.CuFriendExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CuFriendMapper extends SuperMapper {
    long countByExample(CuFriendExample example);

    int deleteByExample(CuFriendExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CuFriend record);

    int insertSelective(CuFriend record);

    List<CuFriend> selectByExample(CuFriendExample example);

    CuFriend selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CuFriend record, @Param("example") CuFriendExample example);

    int updateByExample(@Param("record") CuFriend record, @Param("example") CuFriendExample example);

    int updateByPrimaryKeySelective(CuFriend record);

    int updateByPrimaryKey(CuFriend record);
}