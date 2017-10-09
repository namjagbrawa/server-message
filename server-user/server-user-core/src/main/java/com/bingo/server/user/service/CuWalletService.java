package com.bingo.server.user.service;

import com.bingo.server.database.dao.CuWalletMapper;
import com.bingo.server.database.dao.SpecCuWalletMapper;
import com.bingo.server.database.model.CuWallet;
import com.bingo.server.user.provider.CuWalletProvider;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Service
public class CuWalletService implements CuWalletProvider {

    private static Logger logger = LoggerFactory.getLogger(CuWalletService.class);

    @Autowired
    private CuWalletMapper cuWalletMapper;
    @Autowired
    private SpecCuWalletMapper specCuWalletMapper;

    @Override
    public CuWallet getWallet(long userId) {
        return cuWalletMapper.selectByPrimaryKey(userId);
    }

    @Override
    public boolean substractRoomCard(long userId, int roomCard) {
        int row = specCuWalletMapper.substractRoomCard(userId, roomCard);
        System.out.println(row);
        if (row > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public CuWallet initWallet(long userId) {
        CuWallet wallet = getWallet(userId);
        if (Check.isNull(wallet)) {
            CuWallet newWallet = newWallet(userId);
            cuWalletMapper.insert(newWallet);
            return newWallet;
        } else {
            logger.warn("用户钱包已存在, userId : " + userId + " wallet : " + wallet);
            CuWallet newWallet = newWallet(userId);
            cuWalletMapper.updateByPrimaryKey(newWallet);
            return newWallet;
        }
    }

    @Override
    public void updateWallet(CuWallet cuWallet) {
        cuWalletMapper.updateByPrimaryKey(cuWallet);
    }

    @Override
    public CuWallet resetWallet(long userId) {
        CuWallet wallet = getWallet(userId);
        if (Check.isNull(wallet)) {
            logger.warn("用户钱包不存在, userId : " + userId);
            CuWallet newWallet = newWallet(userId);
            cuWalletMapper.insert(newWallet);
            return newWallet;
        } else {
            CuWallet newWallet = newWallet(userId);
            cuWalletMapper.updateByPrimaryKey(newWallet);
            return newWallet;
        }
    }

    private CuWallet newWallet(long userId) {
        CuWallet cuWallet = new CuWallet();
        cuWallet.setId(userId);
        cuWallet.setBean(0);
        cuWallet.setCoin(new BigDecimal(0));
        cuWallet.setCreateTime(new Date());
        cuWallet.setCredit(new BigDecimal(0));
        return cuWallet;
    }
}
