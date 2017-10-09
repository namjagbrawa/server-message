package com.bingo.server.game.schedule;

import com.bingo.server.game.ddz.service.DdzRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/19.
 */
@Service
public class RuleSchedule implements Schedule{
    protected final Logger logger= LoggerFactory.getLogger(RuleSchedule.class);

    @Autowired
    private DdzRuleService ddzRuleService;

    @Override
    public void process() {
        ddzRuleService.ruleReferesh();
    }
}
