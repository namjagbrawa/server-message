package com.bingo.server.game.bean;

/**
 * Created by ZhangGe on 2017/7/21.
 */
public class DdzConstant {

    // 3 4 5 6 7 8 9 10 11J 12Q 13K 14A 15_2 16小王 17大王
    // 数字比较:大王＞小王＞2＞A＞K＞Q＞J＞10＞9＞8＞7＞6＞5＞4＞3

    public static final byte[] poker; // 一副牌
    public static final byte[] faces; // 牌面
    public static final int chairWeight = 64; //初始权重  64

    static {
        // 初始化牌面
        faces = new byte[14];
        for (byte f = 2; f < 16; f++) {
            faces[f - 2] = f;
        }
        // 初始化扑克牌
        poker = new byte[54];
        int p = 0;
        for (int i = 2; i < 15; i++) {
            poker[p++] = (byte) (i + 0x00); // 黑
            poker[p++] = (byte) (i + 0x10); // 红
            poker[p++] = (byte) (i + 0x20); // 梅
            poker[p++] = (byte) (i + 0x30); // 方
        }
        // 初始化大小王
        poker[p++] = (byte) 0x4F; // 小王
        poker[p++] = (byte) 0x5F; // 大王
    }
}
