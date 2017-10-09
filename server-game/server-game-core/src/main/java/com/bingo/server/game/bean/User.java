package com.bingo.server.game.bean;

import com.bingo.framework.common.URL;
import com.bingo.server.game.bean.enums.CardType;
import com.bingo.server.game.provider.bean.enums.UserStatus;

/**
 * Created by ZhangGe on 2017/7/20.
 */
public class User {

    // 基础信息
    private long userId;
    private long topic;
    private URL url;
    private int site;

    // 牌局信息
    private UserStatus userStatus;
    private byte[] cards;
    private int pullTime;
    private byte[] currentPullCards;
    private CardType cardType;
    private int chairId;
    private int callLordScore;

    private int score;
    private boolean agent;

    private int weight;
    private boolean seenCard;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTopic() {
        return topic;
    }

    public void setTopic(long topic) {
        this.topic = topic;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public int getSite() {
        return site;
    }

    public void setSite(int site) {
        this.site = site;
    }

    public byte[] getCards() {
        return cards;
    }

    public void setCards(byte[] cards) {
        this.cards = cards;
    }

    public int getPullTime() {
        return pullTime;
    }

    public void setPullTime(int pullTime) {
        this.pullTime = pullTime;
    }

    public byte[] getCurrentPullCards() {
        return currentPullCards;
    }

    public void setCurrentPullCards(byte[] currentPullCards) {
        this.currentPullCards = currentPullCards;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getChairId() {
        return chairId;
    }

    public void setChairId(int chairId) {
        this.chairId = chairId;
    }

    public int getCallLordScore() {
        return callLordScore;
    }

    public void setCallLordScore(int callLordScore) {
        this.callLordScore = callLordScore;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isAgent() {
        return agent;
    }

    public void setAgent(boolean agent) {
        this.agent = agent;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSeenCard() {
        return seenCard;
    }

    public void setSeenCard(boolean seenCard) {
        this.seenCard = seenCard;
    }
}
