package com.bingo.server.hall.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.HallBroadcastMapper;
import com.bingo.server.database.model.HallBroadcast;
import com.bingo.server.database.model.HallBroadcastExample;
import com.bingo.server.hall.provider.BroadcastProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/24.
 */
@Service
public class BroadcastService implements BroadcastProvider {

    private final Logger logger = LoggerFactory.getLogger(BroadcastService.class);

    @Autowired
    private HallBroadcastMapper hallBroadcastMapper;


    public HallBroadcast addBroadcast(int order, String content, String detail) {
        HallBroadcast hallBroadcast = new HallBroadcast();
        hallBroadcast.setId(IDGen.getId());
        hallBroadcast.setEntryOrder(order);
        hallBroadcast.setContent(content);
        hallBroadcast.setDetail(detail);
        hallBroadcast.setModifyTime(new Date());
        hallBroadcastMapper.insert(hallBroadcast);
        return hallBroadcast;
    }

    @Override
    public List<HallBroadcast> getBroadcasts() {
        return hallBroadcastMapper.selectByExample(new HallBroadcastExample());
    }

    @Override
    public void updateBroadcast(HallBroadcast hallBroadcast) {
        hallBroadcast.setModifyTime(new Date());
        hallBroadcastMapper.updateByPrimaryKey(hallBroadcast);
    }

    @Override
    public HallBroadcast getBroadcast(long id) {
        return hallBroadcastMapper.selectByPrimaryKey(id);
    }

    @Override
    public String getBroadcast() {
        List<HallBroadcast> hallBroadcasts = hallBroadcastMapper.selectByExample(new HallBroadcastExample());
        if (Check.isNull(hallBroadcasts)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (HallBroadcast hallBroadcast : hallBroadcasts) {
            sb.append(hallBroadcast.getContent()).append(", ");
        }
        return sb.substring(0, sb.length() - 1);
    }
}
