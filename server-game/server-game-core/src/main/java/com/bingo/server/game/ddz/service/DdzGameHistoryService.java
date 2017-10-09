package com.bingo.server.game.ddz.service;

import com.bingo.server.database.dao.DdzGameHistoryMapper;
import com.bingo.server.database.model.DdzGameHistory;
import com.bingo.server.database.model.DdzGameHistoryExample;
import com.bingo.server.game.provider.DdzGameHistoryProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ZhangGe on 2017/7/26.
 */
@Service
public class DdzGameHistoryService implements DdzGameHistoryProvider {

    private final static Logger logger = LoggerFactory.getLogger(DdzDeskService.class);

    @Autowired
    private DdzGameHistoryMapper ddzGameHistoryMapper;

    @Override
    public List<DdzGameHistory> getLastestOperation(long deskId, int round, int limit) {
        DdzGameHistoryExample ddzGameHistoryExample = new DdzGameHistoryExample();
        ddzGameHistoryExample.createCriteria().andDeskIdEqualTo(deskId).andRoundEqualTo(round);
        ddzGameHistoryExample.setLimitRows(limit);
        ddzGameHistoryExample.setOrderByClause("order by create_time desc");
        return ddzGameHistoryMapper.selectByExample(ddzGameHistoryExample);
    }
}
