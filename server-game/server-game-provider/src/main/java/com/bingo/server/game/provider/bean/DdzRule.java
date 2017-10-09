package com.bingo.server.game.provider.bean;

import com.bingo.server.game.provider.bean.enums.Operation;
import com.bingo.server.msg.BASE;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/19.
 */
public class DdzRule implements Serializable {

    private static final long serialVersionUID = 7815426752583648699L;

    // 牌局类型
    Operation ddzType; // classic; // 经典玩法, rascal; // 癞子玩法, lighting; // 闪电玩法

    // 牌局数
    int playNumber;

    // 房卡数
    int roomCard;

    // 癞子张数
    int rascalNumber;

    // 封顶分数
    int upperLimit;

    // 去除牌张
    List<Integer> except;

    // 叫地主规则
    Operation callLord; // 叫地主规则, 胜者叫地主, callLordWinner; // 叫地主规则, 轮流叫地主, callLordPolling;

    // 抢地主规则
    Operation snatchLord; // 抢地主规则, 一分直接叫地主, snatchLordDirect; // 抢地主规则, 一分开始三分封顶, snatchLordDirectUpper; // 抢地主规则, 叫一次抢两次每次翻倍, snatchLordDouble;

    // 踢拉规则
    Operation kickPull; // 踢拉规则, 可踢拉一次, inOutOnce; // 踢拉规则, 只可踢不可拉, inOutOut; // 踢拉规则, 不可踢拉, inOutNo;

    // 发牌规则, 几次发完, 0,表示逐张
    int dealCardTime;
    // 发牌规则, 最少炸弹数
    int minBomb;
    // 发牌规则, 最多炸弹数
    int maxBomb;

    // 底牌规则,底牌翻几倍, >= 1, 1为不翻倍
    int handCard;

    // 明牌规则,true, false, 允不允许明牌
    boolean seenCard;

    // 记牌器, 是不是用记牌器
    boolean tracker;

    // 癞子,癞子专用, 不可配大小王
    boolean rascalCardNoKing;

    // 三张, 不可带单张
    boolean threeCardNoOne;
    // 三张, 不可带对子
    boolean threeCardNoPair;

    // 四张, 不可带一对
    boolean fourCardNoPair;
    // 四张, 不可带一单一对
    boolean fourCardNoOneAndPair;

    // 飞机, 不可带一对
    boolean flyNoPair;

    // 王炸, 不可拆开出
    boolean kingBombNoBreak;

    public Operation getDdzType() {
        return ddzType;
    }

    public void setDdzType(Operation ddzType) {
        this.ddzType = ddzType;
    }

    public Operation getCallLord() {
        return callLord;
    }

    public void setCallLord(Operation callLord) {
        this.callLord = callLord;
    }

    public Operation getSnatchLord() {
        return snatchLord;
    }

    public void setSnatchLord(Operation snatchLord) {
        this.snatchLord = snatchLord;
    }

    public Operation getKickPull() {
        return kickPull;
    }

    public void setKickPull(Operation kickPull) {
        this.kickPull = kickPull;
    }

    public int getPlayNumber() {
        return playNumber;
    }

    public void setPlayNumber(int playNumber) {
        this.playNumber = playNumber;
    }

    public int getRoomCard() {
        return roomCard;
    }

    public void setRoomCard(int roomCard) {
        this.roomCard = roomCard;
    }

    public int getRascalNumber() {
        return rascalNumber;
    }

    public void setRascalNumber(int rascalNumber) {
        this.rascalNumber = rascalNumber;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public List<Integer> getExcept() {
        return except;
    }

    public void setExcept(List<Integer> except) {
        this.except = except;
    }

    public int getDealCardTime() {
        return dealCardTime;
    }

    public void setDealCardTime(int dealCardTime) {
        this.dealCardTime = dealCardTime;
    }

    public int getMinBomb() {
        return minBomb;
    }

    public void setMinBomb(int minBomb) {
        this.minBomb = minBomb;
    }

    public int getMaxBomb() {
        return maxBomb;
    }

    public void setMaxBomb(int maxBomb) {
        this.maxBomb = maxBomb;
    }

    public int getHandCard() {
        return handCard;
    }

    public void setHandCard(int handCard) {
        this.handCard = handCard;
    }

    public boolean isSeenCard() {
        return seenCard;
    }

    public void setSeenCard(boolean seenCard) {
        this.seenCard = seenCard;
    }

    public boolean isTracker() {
        return tracker;
    }

    public void setTracker(boolean tracker) {
        this.tracker = tracker;
    }

    public boolean isRascalCardNoKing() {
        return rascalCardNoKing;
    }

    public void setRascalCardNoKing(boolean rascalCardNoKing) {
        this.rascalCardNoKing = rascalCardNoKing;
    }

    public boolean isThreeCardNoOne() {
        return threeCardNoOne;
    }

    public void setThreeCardNoOne(boolean threeCardNoOne) {
        this.threeCardNoOne = threeCardNoOne;
    }

    public boolean isThreeCardNoPair() {
        return threeCardNoPair;
    }

    public void setThreeCardNoPair(boolean threeCardNoPair) {
        this.threeCardNoPair = threeCardNoPair;
    }

    public boolean isFourCardNoPair() {
        return fourCardNoPair;
    }

    public void setFourCardNoPair(boolean fourCardNoPair) {
        this.fourCardNoPair = fourCardNoPair;
    }

    public boolean isFourCardNoOneAndPair() {
        return fourCardNoOneAndPair;
    }

    public void setFourCardNoOneAndPair(boolean fourCardNoOneAndPair) {
        this.fourCardNoOneAndPair = fourCardNoOneAndPair;
    }

    public boolean isFlyNoPair() {
        return flyNoPair;
    }

    public void setFlyNoPair(boolean flyNoPair) {
        this.flyNoPair = flyNoPair;
    }

    public boolean isKingBombNoBreak() {
        return kingBombNoBreak;
    }

    public void setKingBombNoBreak(boolean kingBombNoBreak) {
        this.kingBombNoBreak = kingBombNoBreak;
    }

    @Override
    public String toString() {
        return "DdzRule{" +
                "ddzType=" + ddzType +
                ", playNumber=" + playNumber +
                ", roomCard=" + roomCard +
                ", rascalNumber=" + rascalNumber +
                ", upperLimit=" + upperLimit +
                ", except=" + except +
                ", callLord=" + callLord +
                ", snatchLord=" + snatchLord +
                ", kickPull=" + kickPull +
                ", dealCardTime=" + dealCardTime +
                ", minBomb=" + minBomb +
                ", maxBomb=" + maxBomb +
                ", handCard=" + handCard +
                ", seenCard=" + seenCard +
                ", tracker=" + tracker +
                ", rascalCardNoKing=" + rascalCardNoKing +
                ", threeCardNoOne=" + threeCardNoOne +
                ", threeCardNoPair=" + threeCardNoPair +
                ", fourCardNoPair=" + fourCardNoPair +
                ", fourCardNoOneAndPair=" + fourCardNoOneAndPair +
                ", flyNoPair=" + flyNoPair +
                ", kingBombNoBreak=" + kingBombNoBreak +
                '}';
    }
}
