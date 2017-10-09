package com.bingo.server.database.model;

import java.io.Serializable;
import java.util.Date;

public class CuFriend implements Serializable {
    private Long id;

    private Long cuUserId;

    private Long cuFriendUserId;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCuUserId() {
        return cuUserId;
    }

    public void setCuUserId(Long cuUserId) {
        this.cuUserId = cuUserId;
    }

    public Long getCuFriendUserId() {
        return cuFriendUserId;
    }

    public void setCuFriendUserId(Long cuFriendUserId) {
        this.cuFriendUserId = cuFriendUserId;
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
        CuFriend other = (CuFriend) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCuUserId() == null ? other.getCuUserId() == null : this.getCuUserId().equals(other.getCuUserId()))
            && (this.getCuFriendUserId() == null ? other.getCuFriendUserId() == null : this.getCuFriendUserId().equals(other.getCuFriendUserId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCuUserId() == null) ? 0 : getCuUserId().hashCode());
        result = prime * result + ((getCuFriendUserId() == null) ? 0 : getCuFriendUserId().hashCode());
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
        sb.append(", cuUserId=").append(cuUserId);
        sb.append(", cuFriendUserId=").append(cuFriendUserId);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}