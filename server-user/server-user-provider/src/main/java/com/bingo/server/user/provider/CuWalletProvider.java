package com.bingo.server.user.provider;

import com.bingo.server.database.model.CuWallet;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface CuWalletProvider {

    CuWallet getWallet(long userId);

    boolean substractRoomCard(long userId, int roomCard);

    CuWallet initWallet(long userId);

    void updateWallet(CuWallet cuWallet);

    CuWallet resetWallet(long userId);
}

