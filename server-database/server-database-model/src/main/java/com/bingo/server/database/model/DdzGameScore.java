package com.bingo.server.database.model;

import java.io.Serializable;
import java.util.Date;

public class DdzGameScore implements Serializable {
    private Long id;

    private Long deskId;

    private Integer round;

    private Integer userScore3;

    private Integer userScore2;

    private Integer userScore1;

    private Long userId3;

    private Long userId2;

    private Long userId1;

    private Long winner;

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

    public Integer getUserScore3() {
        return userScore3;
    }

    public void setUserScore3(Integer userScore3) {
        this.userScore3 = userScore3;
    }

    public Integer getUserScore2() {
        return userScore2;
    }

    public void setUserScore2(Integer userScore2) {
        this.userScore2 = userScore2;
    }

    public Integer getUserScore1() {
        return userScore1;
    }

    public void setUserScore1(Integer userScore1) {
        this.userScore1 = userScore1;
    }

    public Long getUserId3() {
        return userId3;
    }

    public void setUserId3(Long userId3) {
        this.userId3 = userId3;
    }

    public Long getUserId2() {
        return userId2;
    }

    public void setUserId2(Long userId2) {
        this.userId2 = userId2;
    }

    public Long getUserId1() {
        return userId1;
    }

    public void setUserId1(Long userId1) {
        this.userId1 = userId1;
    }

    public Long getWinner() {
        return winner;
    }

    public void setWinner(Long winner) {
        this.winner = winner;
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
        DdzGameScore other = (DdzGameScore) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDeskId() == null ? other.getDeskId() == null : this.getDeskId().equals(other.getDeskId()))
            && (this.getRound() == null ? other.getRound() == null : this.getRound().equals(other.getRound()))
            && (this.getUserScore3() == null ? other.getUserScore3() == null : this.getUserScore3().equals(other.getUserScore3()))
            && (this.getUserScore2() == null ? other.getUserScore2() == null : this.getUserScore2().equals(other.getUserScore2()))
            && (this.getUserScore1() == null ? other.getUserScore1() == null : this.getUserScore1().equals(other.getUserScore1()))
            && (this.getUserId3() == null ? other.getUserId3() == null : this.getUserId3().equals(other.getUserId3()))
            && (this.getUserId2() == null ? other.getUserId2() == null : this.getUserId2().equals(other.getUserId2()))
            && (this.getUserId1() == null ? other.getUserId1() == null : this.getUserId1().equals(other.getUserId1()))
            && (this.getWinner() == null ? other.getWinner() == null : this.getWinner().equals(other.getWinner()))
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
        result = prime * result + ((getUserScore3() == null) ? 0 : getUserScore3().hashCode());
        result = prime * result + ((getUserScore2() == null) ? 0 : getUserScore2().hashCode());
        result = prime * result + ((getUserScore1() == null) ? 0 : getUserScore1().hashCode());
        result = prime * result + ((getUserId3() == null) ? 0 : getUserId3().hashCode());
        result = prime * result + ((getUserId2() == null) ? 0 : getUserId2().hashCode());
        result = prime * result + ((getUserId1() == null) ? 0 : getUserId1().hashCode());
        result = prime * result + ((getWinner() == null) ? 0 : getWinner().hashCode());
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
        sb.append(", userScore3=").append(userScore3);
        sb.append(", userScore2=").append(userScore2);
        sb.append(", userScore1=").append(userScore1);
        sb.append(", userId3=").append(userId3);
        sb.append(", userId2=").append(userId2);
        sb.append(", userId1=").append(userId1);
        sb.append(", winner=").append(winner);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}