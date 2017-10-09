package com.bingo.server.game.ddz;

/**
 * Created by ZhangGe on 2017/7/28.
 */
public class CardUtil {

    // 牌张排序
    public static short[] sortCards(byte[] cards) {
        short[] sort = new short[cards.length];
        for (int i = 0; i < sort.length; i++) {
            sort[i] = cards[i];
        }
        sort(sort);
        return sort;
    }

    // 牌张排序, 含花色排序
    private static void sort(short[] cards) {
        for (int i = 1; i < cards.length; i++) {
            for (int j = cards.length - 1; j > 0; j--) {
                int n = cards[j] & 0x0F; // 牌数
                int hua = ((cards[j] & 0x0F0) >> 4); // 花色
                if (n == 2) {
                    n = 15; // 2当15
                } else if (n == 15) {
                    n = hua == 4 ? 16 : 17; // 16小王 17大王
                }
                int n2 = cards[j - 1] & 0x0F;
                hua = ((cards[j - 1] & 0xF0) >> 4);
                if (n2 == 2) {
                    n2 = 15;
                } else if (n2 == 15) {
                    n2 = hua == 4 ? 16 : 17;
                }
                if (n > n2) { // < ??
                    short q = cards[j];
                    cards[j] = cards[j - 1];
                    cards[j - 1] = q;
                }
            }
        }
    }
}
