package com.bingo.server.database.dao;

import com.bingo.server.database.model.Message;
import com.bingo.server.database.model.MessageExample;
import com.bingo.server.database.mybatis.SuperMapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MessageMapper extends SuperMapper {
    long countByExample(MessageExample example);

    int deleteByExample(MessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Message record);

    int insertSelective(Message record);

    List<Message> selectByExample(MessageExample example);

    Message selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByExample(@Param("record") Message record, @Param("example") MessageExample example);

    int updateByPrimaryKeySelective(Message record);

    int updateByPrimaryKey(Message record);
}