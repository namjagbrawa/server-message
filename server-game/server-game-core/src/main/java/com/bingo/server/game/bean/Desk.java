package com.bingo.server.game.bean;

import com.bingo.framework.common.URL;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.DeskStatus;
import com.bingo.server.game.provider.bean.enums.DeskType;

import java.util.Date;
import java.util.List;

/**
 * Created by ZhangGe on 2017/7/19.
 */
public class Desk {

    // 牌桌基础信息
    private Long deskId; // 一旦开始,就是一个牌局,所有用户退出,则解散牌桌,重新计算牌局
    private String deskName;
    private String deakDetail;
    private String deskTip;
    private DdzRule ddzRule;
    private List<User> users;
    private URL serverUrl;
    private DeskStatus deskStatus;
    private Date createTime;
    private DeskType deskType;

    // 牌局信息
    private byte[] handCards; // 三张底牌
    private byte[] excepts; // 排除的牌, 闪电玩法

    private boolean spring;
    private int bombCount;
    private int rocketCount;

    private int lordChair;

    private int currentChair;
    private byte[] currentCards;
    private long currentUserId;

    private int lastWinnerChair;
    private int nextChair;

    // 叫地主
    private int startCallChair = 0;

    private int noCallTime;
    private byte[] nextCallMark;
    private int lastCallMark;
    private int lastCallChair;
    private int maxCallMark;
    private int maxChairId;
    private int callTime;
    private boolean redeal;

    private int operateTimeForCallLord;
    private int operateTimeForPullCard;

    public long getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(long currentUserId) {
        this.currentUserId = currentUserId;
    }

    public int getRocketCount() {
        return rocketCount;
    }

    public void setRocketCount(int rocketCount) {
        this.rocketCount = rocketCount;
    }

    public byte[] getCurrentCards() {
        return currentCards;
    }

    public void setCurrentCards(byte[] currentCards) {
        this.currentCards = currentCards;
    }

    public int getLastCallChair() {
        return lastCallChair;
    }

    public void setLastCallChair(int lastCallChair) {
        this.lastCallChair = lastCallChair;
    }

    public int getMaxChairId() {
        return maxChairId;
    }

    public void setMaxChairId(int maxChairId) {
        this.maxChairId = maxChairId;
    }

    public int getStartCallChair() {
        return startCallChair;
    }

    public void setStartCallChair(int startCallChair) {
        this.startCallChair = startCallChair;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public int getNextChair() {
        return nextChair;
    }

    public void setNextChair(int nextChair) {
        this.nextChair = nextChair;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public String getDeakDetail() {
        return deakDetail;
    }

    public void setDeakDetail(String deakDetail) {
        this.deakDetail = deakDetail;
    }

    public String getDeskTip() {
        return deskTip;
    }

    public void setDeskTip(String deskTip) {
        this.deskTip = deskTip;
    }

    public DdzRule getDdzRule() {
        return ddzRule;
    }

    public void setDdzRule(DdzRule ddzRule) {
        this.ddzRule = ddzRule;
    }

    public byte[] getNextCallMark() {
        return nextCallMark;
    }

    public void setNextCallMark(byte[] nextCallMark) {
        this.nextCallMark = nextCallMark;
    }

    public boolean isRedeal() {
        return redeal;
    }

    public void setRedeal(boolean redeal) {
        this.redeal = redeal;
    }

    public int getLastCallMark() {
        return lastCallMark;
    }

    public void setLastCallMark(int lastCallMark) {
        this.lastCallMark = lastCallMark;
    }

    public int getMaxCallMark() {
        return maxCallMark;
    }

    public void setMaxCallMark(int maxCallMark) {
        this.maxCallMark = maxCallMark;
    }

    public int getCallTime() {
        return callTime;
    }

    public void setCallTime(int callTime) {
        this.callTime = callTime;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public URL getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public DeskStatus getDeskStatus() {
        return deskStatus;
    }

    public void setDeskStatus(DeskStatus deskStatus) {
        this.deskStatus = deskStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public DeskType getDeskType() {
        return deskType;
    }

    public void setDeskType(DeskType deskType) {
        this.deskType = deskType;
    }

    public byte[] getHandCards() {
        return handCards;
    }

    public void setHandCards(byte[] handCards) {
        this.handCards = handCards;
    }

    public void setExcepts(byte[] excepts) {
        this.excepts = excepts;
    }

    public byte[] getExcepts() {
        return excepts;
    }

    public boolean isSpring() {
        return spring;
    }

    public void setSpring(boolean spring) {
        this.spring = spring;
    }

    public int getBombCount() {
        return bombCount;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public int getLordChair() {
        return lordChair;
    }

    public void setLordChair(int lordChair) {
        this.lordChair = lordChair;
    }

    public int getCurrentChair() {
        return currentChair;
    }

    public void setCurrentChair(int currentChair) {
        this.currentChair = currentChair;
    }

    public int getNoCallTime() {
        return noCallTime;
    }

    public void setNoCallTime(int noCallTime) {
        this.noCallTime = noCallTime;
    }

    public int getLastWinnerChair() {
        return lastWinnerChair;
    }

    public void setLastWinnerChair(int lastWinnerChair) {
        this.lastWinnerChair = lastWinnerChair;
    }

    public int getOperateTimeForCallLord() {
        return operateTimeForCallLord;
    }

    public void setOperateTimeForCallLord(int operateTimeForCallLord) {
        this.operateTimeForCallLord = operateTimeForCallLord;
    }

    public int getOperateTimeForPullCard() {
        return operateTimeForPullCard;
    }

    public void setOperateTimeForPullCard(int operateTimeForPullCard) {
        this.operateTimeForPullCard = operateTimeForPullCard;
    }
}
