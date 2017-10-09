package com.bingo.server.hall.service;

import com.bingo.framework.common.utils.JSONUtil;
import com.bingo.server.database.model.CuUser;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.user.bean.enums.UserSex;
import com.bingo.server.utils.Check;
import com.bingo.server.utils.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WeixinService {

    private final Logger logger = LoggerFactory.getLogger(WeixinService.class);

    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    @Value("${weixin.app.id}")
    private String appId;

    @Value("${weixin.secret}")
    private String secret;

    // 返回只包括微信字段的CuUser
    public CuUser getWeixinUser(String code) {
        CuUser cuUser = getAccessToken(code);
        if (Check.isNull(cuUser)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "微信授权失败");
        }
        return cuUser;
    }

    private CuUser getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, appId, secret, code);
        String result = HttpClient.sendRequestByGet(url);
        if (Check.isNull(result)) {
            logger.error("获取微信AccessToken失败，请求返回为空，code : " + code);
            return null;
        }

        Map map = JSONUtil.toBean(result, Map.class);
        if (Check.isNull(result)) {
            logger.error("获取微信AccessToken失败，解析json错误，code : " + code + " result : " + result);
            return null;
        }

        if (map.get("errcode") != null) {
            logger.error("获取微信AccessToken失败，返回错误，code : " + code + " result : " + result);
            return null;
        }

        String accessToken = (String) map.get("access_token");
        String openId = (String) map.get("openid");

        return getUserInfo(accessToken, openId);
    }

    private CuUser getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, accessToken, openId);
        String result = HttpClient.sendRequestByGet(url);
        if (Check.isNull(result)) {
            logger.error("获取微信用户信息失败，请求返回为空，accessToken : " + accessToken + " openId : " + openId);
            return null;
        }

        Map map = JSONUtil.toBean(result, Map.class);
        if (Check.isNull(result)) {
            logger.error("获取微信用户信息失败，解析json错误，accessToken : " + accessToken + " openId : " + openId + " result : " + result);
            return null;
        }

        if (map.get("errcode") != null) {
            logger.error("获取微信用户信息失败，返回错误，accessToken : " + accessToken + " openId : " + openId + " result : " + result);
            return null;
        }

        CuUser cuUser= new CuUser();
        cuUser.setOpenId((String) map.get("openid"));
        cuUser.setOpenId((String) map.get("nickname"));
        if ("1".equals(map.get("sex"))) {
            cuUser.setSex(UserSex.male.name());
        } else if ("2".equals(map.get("sex"))) {
            cuUser.setSex(UserSex.female.name());
        }
        cuUser.setProvince((String) map.get("province"));
        cuUser.setCity((String) map.get("city"));
        cuUser.setCountry((String) map.get("country"));
        cuUser.setHeadImgUrl((String) map.get("headimgurl"));
        cuUser.setPrivilege((String) map.get("privilege")); // 可能会报错
        cuUser.setUnionId((String) map.get("unionid"));

        return cuUser;
    }

}