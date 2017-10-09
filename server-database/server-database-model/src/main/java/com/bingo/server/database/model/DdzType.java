package com.bingo.server.database.model;

import java.io.Serializable;
import java.util.Date;

public class DdzType implements Serializable {
    private Long id;

    private String typeName;

    private String typeText;

    private String typeTip;

    private Integer typeOrder;

    private String subjects;

    private Date createTime;

    private Date modifyTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getTypeText() {
        return typeText;
    }

    public void setTypeText(String typeText) {
        this.typeText = typeText == null ? null : typeText.trim();
    }

    public String getTypeTip() {
        return typeTip;
    }

    public void setTypeTip(String typeTip) {
        this.typeTip = typeTip == null ? null : typeTip.trim();
    }

    public Integer getTypeOrder() {
        return typeOrder;
    }

    public void setTypeOrder(Integer typeOrder) {
        this.typeOrder = typeOrder;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects == null ? null : subjects.trim();
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
        DdzType other = (DdzType) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTypeName() == null ? other.getTypeName() == null : this.getTypeName().equals(other.getTypeName()))
            && (this.getTypeText() == null ? other.getTypeText() == null : this.getTypeText().equals(other.getTypeText()))
            && (this.getTypeTip() == null ? other.getTypeTip() == null : this.getTypeTip().equals(other.getTypeTip()))
            && (this.getTypeOrder() == null ? other.getTypeOrder() == null : this.getTypeOrder().equals(other.getTypeOrder()))
            && (this.getSubjects() == null ? other.getSubjects() == null : this.getSubjects().equals(other.getSubjects()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getModifyTime() == null ? other.getModifyTime() == null : this.getModifyTime().equals(other.getModifyTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
        result = prime * result + ((getTypeText() == null) ? 0 : getTypeText().hashCode());
        result = prime * result + ((getTypeTip() == null) ? 0 : getTypeTip().hashCode());
        result = prime * result + ((getTypeOrder() == null) ? 0 : getTypeOrder().hashCode());
        result = prime * result + ((getSubjects() == null) ? 0 : getSubjects().hashCode());
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
        sb.append(", typeName=").append(typeName);
        sb.append(", typeText=").append(typeText);
        sb.append(", typeTip=").append(typeTip);
        sb.append(", typeOrder=").append(typeOrder);
        sb.append(", subjects=").append(subjects);
        sb.append(", createTime=").append(createTime);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}