package com.bingo.server.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DdzTypeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitRows;

    public DdzTypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitRows(Integer limitRows) {
        this.limitRows=limitRows;
    }

    public Integer getLimitRows() {
        return limitRows;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTypeNameIsNull() {
            addCriterion("type_name is null");
            return (Criteria) this;
        }

        public Criteria andTypeNameIsNotNull() {
            addCriterion("type_name is not null");
            return (Criteria) this;
        }

        public Criteria andTypeNameEqualTo(String value) {
            addCriterion("type_name =", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotEqualTo(String value) {
            addCriterion("type_name <>", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThan(String value) {
            addCriterion("type_name >", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameGreaterThanOrEqualTo(String value) {
            addCriterion("type_name >=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThan(String value) {
            addCriterion("type_name <", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLessThanOrEqualTo(String value) {
            addCriterion("type_name <=", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameLike(String value) {
            addCriterion("type_name like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotLike(String value) {
            addCriterion("type_name not like", value, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameIn(List<String> values) {
            addCriterion("type_name in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotIn(List<String> values) {
            addCriterion("type_name not in", values, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameBetween(String value1, String value2) {
            addCriterion("type_name between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeNameNotBetween(String value1, String value2) {
            addCriterion("type_name not between", value1, value2, "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeTextIsNull() {
            addCriterion("type_text is null");
            return (Criteria) this;
        }

        public Criteria andTypeTextIsNotNull() {
            addCriterion("type_text is not null");
            return (Criteria) this;
        }

        public Criteria andTypeTextEqualTo(String value) {
            addCriterion("type_text =", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextNotEqualTo(String value) {
            addCriterion("type_text <>", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextGreaterThan(String value) {
            addCriterion("type_text >", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextGreaterThanOrEqualTo(String value) {
            addCriterion("type_text >=", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextLessThan(String value) {
            addCriterion("type_text <", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextLessThanOrEqualTo(String value) {
            addCriterion("type_text <=", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextLike(String value) {
            addCriterion("type_text like", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextNotLike(String value) {
            addCriterion("type_text not like", value, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextIn(List<String> values) {
            addCriterion("type_text in", values, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextNotIn(List<String> values) {
            addCriterion("type_text not in", values, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextBetween(String value1, String value2) {
            addCriterion("type_text between", value1, value2, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTextNotBetween(String value1, String value2) {
            addCriterion("type_text not between", value1, value2, "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTipIsNull() {
            addCriterion("type_tip is null");
            return (Criteria) this;
        }

        public Criteria andTypeTipIsNotNull() {
            addCriterion("type_tip is not null");
            return (Criteria) this;
        }

        public Criteria andTypeTipEqualTo(String value) {
            addCriterion("type_tip =", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipNotEqualTo(String value) {
            addCriterion("type_tip <>", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipGreaterThan(String value) {
            addCriterion("type_tip >", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipGreaterThanOrEqualTo(String value) {
            addCriterion("type_tip >=", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipLessThan(String value) {
            addCriterion("type_tip <", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipLessThanOrEqualTo(String value) {
            addCriterion("type_tip <=", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipLike(String value) {
            addCriterion("type_tip like", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipNotLike(String value) {
            addCriterion("type_tip not like", value, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipIn(List<String> values) {
            addCriterion("type_tip in", values, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipNotIn(List<String> values) {
            addCriterion("type_tip not in", values, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipBetween(String value1, String value2) {
            addCriterion("type_tip between", value1, value2, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeTipNotBetween(String value1, String value2) {
            addCriterion("type_tip not between", value1, value2, "typeTip");
            return (Criteria) this;
        }

        public Criteria andTypeOrderIsNull() {
            addCriterion("type_order is null");
            return (Criteria) this;
        }

        public Criteria andTypeOrderIsNotNull() {
            addCriterion("type_order is not null");
            return (Criteria) this;
        }

        public Criteria andTypeOrderEqualTo(Integer value) {
            addCriterion("type_order =", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderNotEqualTo(Integer value) {
            addCriterion("type_order <>", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderGreaterThan(Integer value) {
            addCriterion("type_order >", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_order >=", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderLessThan(Integer value) {
            addCriterion("type_order <", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderLessThanOrEqualTo(Integer value) {
            addCriterion("type_order <=", value, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderIn(List<Integer> values) {
            addCriterion("type_order in", values, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderNotIn(List<Integer> values) {
            addCriterion("type_order not in", values, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderBetween(Integer value1, Integer value2) {
            addCriterion("type_order between", value1, value2, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andTypeOrderNotBetween(Integer value1, Integer value2) {
            addCriterion("type_order not between", value1, value2, "typeOrder");
            return (Criteria) this;
        }

        public Criteria andSubjectsIsNull() {
            addCriterion("subjects is null");
            return (Criteria) this;
        }

        public Criteria andSubjectsIsNotNull() {
            addCriterion("subjects is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectsEqualTo(String value) {
            addCriterion("subjects =", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotEqualTo(String value) {
            addCriterion("subjects <>", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsGreaterThan(String value) {
            addCriterion("subjects >", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsGreaterThanOrEqualTo(String value) {
            addCriterion("subjects >=", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLessThan(String value) {
            addCriterion("subjects <", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLessThanOrEqualTo(String value) {
            addCriterion("subjects <=", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLike(String value) {
            addCriterion("subjects like", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotLike(String value) {
            addCriterion("subjects not like", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsIn(List<String> values) {
            addCriterion("subjects in", values, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotIn(List<String> values) {
            addCriterion("subjects not in", values, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsBetween(String value1, String value2) {
            addCriterion("subjects between", value1, value2, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotBetween(String value1, String value2) {
            addCriterion("subjects not between", value1, value2, "subjects");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andTypeNameLikeInsensitive(String value) {
            addCriterion("upper(type_name) like", value.toUpperCase(), "typeName");
            return (Criteria) this;
        }

        public Criteria andTypeTextLikeInsensitive(String value) {
            addCriterion("upper(type_text) like", value.toUpperCase(), "typeText");
            return (Criteria) this;
        }

        public Criteria andTypeTipLikeInsensitive(String value) {
            addCriterion("upper(type_tip) like", value.toUpperCase(), "typeTip");
            return (Criteria) this;
        }

        public Criteria andSubjectsLikeInsensitive(String value) {
            addCriterion("upper(subjects) like", value.toUpperCase(), "subjects");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}