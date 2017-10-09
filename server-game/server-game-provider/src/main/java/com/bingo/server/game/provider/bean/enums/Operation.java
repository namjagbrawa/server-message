package com.bingo.server.game.provider.bean.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ZhangGe on 2017/7/18.
 */
public enum Operation {

    ddzClassic(0), // 斗地主类型,经典玩法
    ddzRascal(0), // 斗地主类型,癞子玩法
    ddzLighting(0), // 斗地主类型,闪电玩法

    playNumber(2), // 牌局数 value1 : 局数, value2 : 房卡数

    rascalNumber(1), // 癞子张数, 癞子专用, value1 : 癞子张数

    upperLimit(1), // 封顶, value1 : 封顶分数

    except(1), // 去除牌,闪电专用 value1 : 3,4,5, 去除牌张

    callLordWinner(0), // 叫地主规则, 胜者叫地主
    callLordPolling(0), // 叫地主规则, 轮流叫地主

    snatchLordDirect(0), // 抢地主规则, 一分直接叫地主
    snatchLordThreeUpper(0), // 抢地主规则, 一分开始三分封顶
    snatchLordDouble(0), // 抢地主规则, 叫一次抢两次每次翻倍

    kickPullOnce(0), // 踢拉规则, 可踢拉一次
    kickPullOut(0), // 踢拉规则, 只可踢不可拉
    kickPullNo(0), // 踢拉规则, 不可踢拉

    dealCard(2), // 发牌规则, value1 : 0, 逐张发牌, 2 两次发完, 3, 三次发完, value2 : 最小炸弹数, value3: 多炸弹数, 默认推荐0, 3

    handCard(1), // 底牌规则,value1 ：底牌翻几倍, >= 1, 1为不翻倍

    seenCard(1), // 明牌规则,value1 : true, false, 允不允许明白

    tracker(1), // 记牌器, value1: 是不是用记牌器

    rascalCardNoKing(0), // 癞子,癞子专用, 不可配大小王

    threeCardNoOne(0), // 三张, 不可带单张
    threeCardNoPair(0), // 三张, 不可带对子

    fourCardNoPair(0), // 四张, 不可带一对
    fourCardNoOneAndPair(0), // 四张, 不可带一单一对

    flyNoPair(0), // 飞机, 不可带一对

    kingBombNoBreak(0); // 王炸, 不可拆开出

    private int parameter;

    Operation(int parameter) {
        this.parameter = parameter;
    }

    private static final Logger logger = LoggerFactory.getLogger(Operation.class);

    public int getParameter() {
        return this.parameter;
    }

    public static Operation get(String optionType) {
        return Operation.valueOf(optionType);
        /*try {
            Operation.valueOf(optionType);
            return true;
        }catch (Exception e) {
            logger.error("OptionType获取枚举类型错误,Operation String : " + optionType);
            return false;
        }*/
    }
}
