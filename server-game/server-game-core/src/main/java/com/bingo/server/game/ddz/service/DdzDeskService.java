package com.bingo.server.game.ddz.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.framework.common.utils.JSONUtil;
import com.bingo.server.database.dao.DdzDeskMapper;
import com.bingo.server.database.dao.DdzUserMapper;
import com.bingo.server.database.model.DdzDesk;
import com.bingo.server.database.model.DdzDeskExample;
import com.bingo.server.database.dao.SpecDdzDeskMapper;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.DeskStatus;
import com.bingo.server.game.provider.DdzDeskProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * Created by ZhangGe on 2017/7/19.
 */
@Service
public class DdzDeskService implements DdzDeskProvider {

    private final static Logger logger = LoggerFactory.getLogger(DdzDeskService.class);
    private final static Random random = new Random();

    @Autowired
    private DdzDeskMapper ddzDeskMapper;
    @Autowired
    private DdzUserMapper ddzUserMapper;
    @Autowired
    private SpecDdzDeskMapper specDdzDeskMapper;

    @Override
    public DdzDesk getDeskById(long deskId) {
        return ddzDeskMapper.selectByPrimaryKey(deskId);
    }

    @Override
    public DdzDesk getDeskByDeskNo(int deskNo) {
        DdzDeskExample ddzDeskExample = new DdzDeskExample();
        ddzDeskExample.createCriteria().andDeskNoEqualTo(deskNo);
        List<DdzDesk> ddzDesks = ddzDeskMapper.selectByExample(ddzDeskExample);
        if (Check.isNull(ddzDesks)) {
            return null;
        }
        return ddzDesks.get(0);
    }

    @Override
    public int getDeskNo(long deskId) {
        random.setSeed(System.nanoTime());
        boolean unique = false;
        int deskNo = 0;
        while (!unique) {
            deskNo = DdzDeskService.random.nextInt(999999);
            int row = specDdzDeskMapper.getUniqueDeskNo(deskNo, deskId);
            if (row >= 1) {
                unique = true;
            }
        }
        return deskNo;
    }

    @Override
    public DdzDesk newDesk(String deskName, int roomCard, DdzRule ddzRule) {
        DdzDesk ddzDesk = new DdzDesk();
        ddzDesk.setId(IDGen.getId());
        ddzDesk.setName(deskName);
        ddzDesk.setRule(JSONUtil.toJson(ddzRule));
        ddzDesk.setStatus(DeskStatus.wait.name());
        ddzDeskMapper.insert(ddzDesk);
        int deskNo = getDeskNo(ddzDesk.getId());
        ddzDesk.setDeskNo(deskNo);
        return ddzDesk;
    }

    @Override
    public void update(DdzDesk ddzDesk) {
        ddzDeskMapper.updateByPrimaryKey(ddzDesk);
    }

}
