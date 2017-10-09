package com.bingo.server.constant;

/**
 * Created by ZhangGe on 2017/5/26.
 */
public class CodeConstant {

    // 0 -- 1000 系统使用，保留，包含开始不包含结束
    public static final int SUCCESS = 0;
    public static final int SYSTEM_ERROR = 1;
    public static final int SYSTEM_BUSY = 2;
    public static final int REQUEST_CODE_NOT_SUPPORTED = 3;

    public static final int DEFAULT_ERROR_CODE = 90000;
    public static final int GATEWAY_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 1000;
    public static final int LOGIN_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 2000;
    public static final int HALL_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 3000;
    public static final int PLAY_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 4000;
    public static final int WORLD_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 5000;
    public static final int USER_DEFAULT_ERROR_CODE = DEFAULT_ERROR_CODE + 6000;

    public static final int DEFAULT_RESPONSE_CODE = 80000;

    public static final int DEFAULT_PLAY_RESPONSE_CODE = DEFAULT_RESPONSE_CODE + 1000;

    public static final int GATEWAY_CODE_RANGE_START = 1000;
    public static final int LOGIN_CODE_RANGE_START = 2000;
    public static final int HALL_CODE_RANGE_START = 3000;
    public static final int PLAY_CODE_RANGE_START = 4000;
    public static final int WORLD_CODE_RANGE_START = 5000;
    public static final int USER_CODE_RANGE_START = 5000;

    public static final int GATEWAY_CODE_RANGE_END = GATEWAY_CODE_RANGE_START + 1000;
    public static final int LOGIN_CODE_RANGE_END = LOGIN_CODE_RANGE_START + 1000;
    public static final int HALL_CODE_RANGE_END = HALL_CODE_RANGE_START + 1000;
    public static final int PLAY_CODE_RANGE_END = PLAY_CODE_RANGE_START + 1000;
    public static final int WORLD_CODE_RANGE_END = WORLD_CODE_RANGE_START + 1000;
    public static final int USER_CODE_RANGE_END = USER_CODE_RANGE_START + 1000;

    public static final int DEFAULT_DDZ_CODE = PLAY_CODE_RANGE_START + 100;


}
