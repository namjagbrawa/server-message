package com.bingo.server.hall.provider;

import com.bingo.server.msg.RESP;

/**
 * Created by ZhangGe on 2017/7/17.
 */
public interface UserProvider {

    RESP.GetUserInfoResponse getUserInfo(long userId);

    void getPhoneCode(String phone);

    void validateCode(String phone, String code);

}
