package com.bingo.server.game.provider.landlord;

/**
 * Created by ZhangGe on 2017/8/2.
 */
public interface LandlordProvider {

    byte[] process(byte[] bytes, long userId, long topic);

}
