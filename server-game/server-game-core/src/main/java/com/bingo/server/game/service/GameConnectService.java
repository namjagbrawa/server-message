package com.bingo.server.game.service;

import com.bingo.server.game.bean.Desk;
import com.bingo.server.game.ddz.service.DealService;
import com.bingo.server.game.provider.GameConnectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by ZhangGe on 2017/7/21.
 */
@Service
public class GameConnectService implements GameConnectProvider {

    @Autowired
    private DealService dealService;

    @Override
    public void disconnect(long userId) {

    }
}
