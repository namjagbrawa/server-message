package com.bingo.server.game.ddz;

/**
 * Created by ZhangGe on 2017/7/27.
 */
public class DeskUtil {

    public static int clockwise(int current, int chairNumber) {
        return current >= chairNumber ? 1 : current + 1;
    }

    public static int antiClockwise(int current, int chairNumber) {
        return current <= 1 ? chairNumber : current - 1;
    }
}
