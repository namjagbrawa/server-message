package com.bingo.server.game.provider.bean.enums;

import org.springframework.context.annotation.Description;

/**
 * Created by ZhangGe on 2017/7/17.
 */
@Deprecated
public enum Subject {

    ddzType, // 玩法类型
    playNumber, // 局数,
    rascalNumber, // 癞子规则, 癞子专用
    upperLimit, // 地主封顶
    except, // 去除规则, 闪电专用
    callLord, // 先叫地主
    snatchLord, // 抢地主
    kickPull, // 踢拉规则
    dealCard, // 发牌规则
    handCard, // 底牌规则
    seenCard, // 明牌规则
    tracker, // 记牌器
    rascalCard, // 癞子, 癞子专用
    threeCard, // 三张
    fourCard, // 四张
    fly, // 飞机
    kingBoom // 王炸

}


