package com.bingo.server.database.model;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {
    private Long id;

    private Long topic;

    private String siteUrl;

    private String username;

    private String nickName;

    private Date loginTime;

    private Date offlineTime;

    private Date createTime;

    private Date loadTime;

    private Integer money;

    private Long gameId;

    private String sex;

    private Date moneyExchangeTime;

    private String headImgUrl;

    private Integer specialMoney;

    private Integer moneyExchangeNumber;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTopic() {
        return topic;
    }

    public void setTopic(Long topic) {
        this.topic = topic;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl == null ? null : siteUrl.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Date getMoneyExchangeTime() {
        return moneyExchangeTime;
    }

    public void setMoneyExchangeTime(Date moneyExchangeTime) {
        this.moneyExchangeTime = moneyExchangeTime;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    public Integer getSpecialMoney() {
        return specialMoney;
    }

    public void setSpecialMoney(Integer specialMoney) {
        this.specialMoney = specialMoney;
    }

    public Integer getMoneyExchangeNumber() {
        return moneyExchangeNumber;
    }

    public void setMoneyExchangeNumber(Integer moneyExchangeNumber) {
        this.moneyExchangeNumber = moneyExchangeNumber;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Role other = (Role) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTopic() == null ? other.getTopic() == null : this.getTopic().equals(other.getTopic()))
            && (this.getSiteUrl() == null ? other.getSiteUrl() == null : this.getSiteUrl().equals(other.getSiteUrl()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getOfflineTime() == null ? other.getOfflineTime() == null : this.getOfflineTime().equals(other.getOfflineTime()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getLoadTime() == null ? other.getLoadTime() == null : this.getLoadTime().equals(other.getLoadTime()))
            && (this.getMoney() == null ? other.getMoney() == null : this.getMoney().equals(other.getMoney()))
            && (this.getGameId() == null ? other.getGameId() == null : this.getGameId().equals(other.getGameId()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getMoneyExchangeTime() == null ? other.getMoneyExchangeTime() == null : this.getMoneyExchangeTime().equals(other.getMoneyExchangeTime()))
            && (this.getHeadImgUrl() == null ? other.getHeadImgUrl() == null : this.getHeadImgUrl().equals(other.getHeadImgUrl()))
            && (this.getSpecialMoney() == null ? other.getSpecialMoney() == null : this.getSpecialMoney().equals(other.getSpecialMoney()))
            && (this.getMoneyExchangeNumber() == null ? other.getMoneyExchangeNumber() == null : this.getMoneyExchangeNumber().equals(other.getMoneyExchangeNumber()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTopic() == null) ? 0 : getTopic().hashCode());
        result = prime * result + ((getSiteUrl() == null) ? 0 : getSiteUrl().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getOfflineTime() == null) ? 0 : getOfflineTime().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getLoadTime() == null) ? 0 : getLoadTime().hashCode());
        result = prime * result + ((getMoney() == null) ? 0 : getMoney().hashCode());
        result = prime * result + ((getGameId() == null) ? 0 : getGameId().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getMoneyExchangeTime() == null) ? 0 : getMoneyExchangeTime().hashCode());
        result = prime * result + ((getHeadImgUrl() == null) ? 0 : getHeadImgUrl().hashCode());
        result = prime * result + ((getSpecialMoney() == null) ? 0 : getSpecialMoney().hashCode());
        result = prime * result + ((getMoneyExchangeNumber() == null) ? 0 : getMoneyExchangeNumber().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", topic=").append(topic);
        sb.append(", siteUrl=").append(siteUrl);
        sb.append(", username=").append(username);
        sb.append(", nickName=").append(nickName);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", offlineTime=").append(offlineTime);
        sb.append(", createTime=").append(createTime);
        sb.append(", loadTime=").append(loadTime);
        sb.append(", money=").append(money);
        sb.append(", gameId=").append(gameId);
        sb.append(", sex=").append(sex);
        sb.append(", moneyExchangeTime=").append(moneyExchangeTime);
        sb.append(", headImgUrl=").append(headImgUrl);
        sb.append(", specialMoney=").append(specialMoney);
        sb.append(", moneyExchangeNumber=").append(moneyExchangeNumber);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}