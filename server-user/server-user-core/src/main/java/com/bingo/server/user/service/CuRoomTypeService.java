package com.bingo.server.user.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.CuRoomTypeMapper;
import com.bingo.server.database.model.CuRoomType;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.provider.CuRoomTypeProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class CuRoomTypeService implements CuRoomTypeProvider {

    private static Logger logger = LoggerFactory.getLogger(CuOnlineUserService.class);

    @Autowired
    private CuRoomTypeMapper cuRoomTypeMapper;

    @Override
    public CuRoomType addRoomType(String name, String detail, String tip, int upperLimit, int lowerLimit) {
        CuRoomType cuRoomType = new CuRoomType();
        cuRoomType.setId(IDGen.getId());
        cuRoomType.setCreateTime(new Date());
        cuRoomType.setName(name);
        cuRoomType.setDetail(detail);
        cuRoomType.setTip(tip);
        cuRoomType.setUpperLimit(upperLimit);
        cuRoomType.setLowerLimit(lowerLimit);
        cuRoomTypeMapper.insert(cuRoomType);
        return cuRoomType;
    }

    @Override
    public void updateRoomType(CuRoomType cuRoomType) {
        cuRoomTypeMapper.updateByPrimaryKey(cuRoomType);
    }

    @Override
    public void delete(long roomTypeId) {
        CuRoomType cuRoomType = get(roomTypeId);
        if (Check.isNull(cuRoomType)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "房间类型不存在, roomTypeId : " + roomTypeId);
        }
        cuRoomTypeMapper.deleteByPrimaryKey(roomTypeId);
    }

    @Override
    public CuRoomType get(long roomTypeId) {
        return cuRoomTypeMapper.selectByPrimaryKey(roomTypeId);
    }
}
