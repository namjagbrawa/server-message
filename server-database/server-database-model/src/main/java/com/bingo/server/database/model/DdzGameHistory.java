package com.bingo.server.database.model;

import java.io.Serializable;
import java.util.Date;

public class DdzGameHistory implements Serializable {
    private Long id;

    private Long deskId;

    private Integer round;

    private String siteCard1;

    private String siteCard2;

    private String siteCard3;

    private String pullCard;

    private Integer pullSite;

    private String remainCard;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeskId() {
        return deskId;
    }

    public void setDeskId(Long deskId) {
        this.deskId = deskId;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public String getSiteCard1() {
        return siteCard1;
    }

    public void setSiteCard1(String siteCard1) {
        this.siteCard1 = siteCard1 == null ? null : siteCard1.trim();
    }

    public String getSiteCard2() {
        return siteCard2;
    }

    public void setSiteCard2(String siteCard2) {
        this.siteCard2 = siteCard2 == null ? null : siteCard2.trim();
    }

    public String getSiteCard3() {
        return siteCard3;
    }

    public void setSiteCard3(String siteCard3) {
        this.siteCard3 = siteCard3 == null ? null : siteCard3.trim();
    }

    public String getPullCard() {
        return pullCard;
    }

    public void setPullCard(String pullCard) {
        this.pullCard = pullCard == null ? null : pullCard.trim();
    }

    public Integer getPullSite() {
        return pullSite;
    }

    public void setPullSite(Integer pullSite) {
        this.pullSite = pullSite;
    }

    public String getRemainCard() {
        return remainCard;
    }

    public void setRemainCard(String remainCard) {
        this.remainCard = remainCard == null ? null : remainCard.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
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
        DdzGameHistory other = (DdzGameHistory) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeskId() == null ? other.getDeskId() == null : this.getDeskId().equals(other.getDeskId()))
            && (this.getRound() == null ? other.getRound() == null : this.getRound().equals(other.getRound()))
            && (this.getSiteCard1() == null ? other.getSiteCard1() == null : this.getSiteCard1().equals(other.getSiteCard1()))
            && (this.getSiteCard2() == null ? other.getSiteCard2() == null : this.getSiteCard2().equals(other.getSiteCard2()))
            && (this.getSiteCard3() == null ? other.getSiteCard3() == null : this.getSiteCard3().equals(other.getSiteCard3()))
            && (this.getPullCard() == null ? other.getPullCard() == null : this.getPullCard().equals(other.getPullCard()))
            && (this.getPullSite() == null ? other.getPullSite() == null : this.getPullSite().equals(other.getPullSite()))
            && (this.getRemainCard() == null ? other.getRemainCard() == null : this.getRemainCard().equals(other.getRemainCard()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDeskId() == null) ? 0 : getDeskId().hashCode());
        result = prime * result + ((getRound() == null) ? 0 : getRound().hashCode());
        result = prime * result + ((getSiteCard1() == null) ? 0 : getSiteCard1().hashCode());
        result = prime * result + ((getSiteCard2() == null) ? 0 : getSiteCard2().hashCode());
        result = prime * result + ((getSiteCard3() == null) ? 0 : getSiteCard3().hashCode());
        result = prime * result + ((getPullCard() == null) ? 0 : getPullCard().hashCode());
        result = prime * result + ((getPullSite() == null) ? 0 : getPullSite().hashCode());
        result = prime * result + ((getRemainCard() == null) ? 0 : getRemainCard().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getModifyTime() == null) ? 0 : getModifyTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", deskId=").append(deskId);
        sb.append(", round=").append(round);
        sb.append(", siteCard1=").append(siteCard1);
        sb.append(", siteCard2=").append(siteCard2);
        sb.append(", siteCard3=").append(siteCard3);
        sb.append(", pullCard=").append(pullCard);
        sb.append(", pullSite=").append(pullSite);
        sb.append(", remainCard=").append(remainCard);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}