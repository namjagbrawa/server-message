package com.bingo.server.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DdzGameScoreExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitRows;

    public DdzGameScoreExample() {
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

        public Criteria andDeskIdIsNull() {
            addCriterion("desk_id is null");
            return (Criteria) this;
        }

        public Criteria andDeskIdIsNotNull() {
            addCriterion("desk_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeskIdEqualTo(Long value) {
            addCriterion("desk_id =", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdNotEqualTo(Long value) {
            addCriterion("desk_id <>", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdGreaterThan(Long value) {
            addCriterion("desk_id >", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdGreaterThanOrEqualTo(Long value) {
            addCriterion("desk_id >=", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdLessThan(Long value) {
            addCriterion("desk_id <", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdLessThanOrEqualTo(Long value) {
            addCriterion("desk_id <=", value, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdIn(List<Long> values) {
            addCriterion("desk_id in", values, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdNotIn(List<Long> values) {
            addCriterion("desk_id not in", values, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdBetween(Long value1, Long value2) {
            addCriterion("desk_id between", value1, value2, "deskId");
            return (Criteria) this;
        }

        public Criteria andDeskIdNotBetween(Long value1, Long value2) {
            addCriterion("desk_id not between", value1, value2, "deskId");
            return (Criteria) this;
        }

        public Criteria andRoundIsNull() {
            addCriterion("round is null");
            return (Criteria) this;
        }

        public Criteria andRoundIsNotNull() {
            addCriterion("round is not null");
            return (Criteria) this;
        }

        public Criteria andRoundEqualTo(Integer value) {
            addCriterion("round =", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundNotEqualTo(Integer value) {
            addCriterion("round <>", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundGreaterThan(Integer value) {
            addCriterion("round >", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundGreaterThanOrEqualTo(Integer value) {
            addCriterion("round >=", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundLessThan(Integer value) {
            addCriterion("round <", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundLessThanOrEqualTo(Integer value) {
            addCriterion("round <=", value, "round");
            return (Criteria) this;
        }

        public Criteria andRoundIn(List<Integer> values) {
            addCriterion("round in", values, "round");
            return (Criteria) this;
        }

        public Criteria andRoundNotIn(List<Integer> values) {
            addCriterion("round not in", values, "round");
            return (Criteria) this;
        }

        public Criteria andRoundBetween(Integer value1, Integer value2) {
            addCriterion("round between", value1, value2, "round");
            return (Criteria) this;
        }

        public Criteria andRoundNotBetween(Integer value1, Integer value2) {
            addCriterion("round not between", value1, value2, "round");
            return (Criteria) this;
        }

        public Criteria andUserScore3IsNull() {
            addCriterion("user_score_3 is null");
            return (Criteria) this;
        }

        public Criteria andUserScore3IsNotNull() {
            addCriterion("user_score_3 is not null");
            return (Criteria) this;
        }

        public Criteria andUserScore3EqualTo(Integer value) {
            addCriterion("user_score_3 =", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3NotEqualTo(Integer value) {
            addCriterion("user_score_3 <>", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3GreaterThan(Integer value) {
            addCriterion("user_score_3 >", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3GreaterThanOrEqualTo(Integer value) {
            addCriterion("user_score_3 >=", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3LessThan(Integer value) {
            addCriterion("user_score_3 <", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3LessThanOrEqualTo(Integer value) {
            addCriterion("user_score_3 <=", value, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3In(List<Integer> values) {
            addCriterion("user_score_3 in", values, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3NotIn(List<Integer> values) {
            addCriterion("user_score_3 not in", values, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3Between(Integer value1, Integer value2) {
            addCriterion("user_score_3 between", value1, value2, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore3NotBetween(Integer value1, Integer value2) {
            addCriterion("user_score_3 not between", value1, value2, "userScore3");
            return (Criteria) this;
        }

        public Criteria andUserScore2IsNull() {
            addCriterion("user_score_2 is null");
            return (Criteria) this;
        }

        public Criteria andUserScore2IsNotNull() {
            addCriterion("user_score_2 is not null");
            return (Criteria) this;
        }

        public Criteria andUserScore2EqualTo(Integer value) {
            addCriterion("user_score_2 =", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2NotEqualTo(Integer value) {
            addCriterion("user_score_2 <>", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2GreaterThan(Integer value) {
            addCriterion("user_score_2 >", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2GreaterThanOrEqualTo(Integer value) {
            addCriterion("user_score_2 >=", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2LessThan(Integer value) {
            addCriterion("user_score_2 <", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2LessThanOrEqualTo(Integer value) {
            addCriterion("user_score_2 <=", value, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2In(List<Integer> values) {
            addCriterion("user_score_2 in", values, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2NotIn(List<Integer> values) {
            addCriterion("user_score_2 not in", values, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2Between(Integer value1, Integer value2) {
            addCriterion("user_score_2 between", value1, value2, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore2NotBetween(Integer value1, Integer value2) {
            addCriterion("user_score_2 not between", value1, value2, "userScore2");
            return (Criteria) this;
        }

        public Criteria andUserScore1IsNull() {
            addCriterion("user_score_1 is null");
            return (Criteria) this;
        }

        public Criteria andUserScore1IsNotNull() {
            addCriterion("user_score_1 is not null");
            return (Criteria) this;
        }

        public Criteria andUserScore1EqualTo(Integer value) {
            addCriterion("user_score_1 =", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1NotEqualTo(Integer value) {
            addCriterion("user_score_1 <>", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1GreaterThan(Integer value) {
            addCriterion("user_score_1 >", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1GreaterThanOrEqualTo(Integer value) {
            addCriterion("user_score_1 >=", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1LessThan(Integer value) {
            addCriterion("user_score_1 <", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1LessThanOrEqualTo(Integer value) {
            addCriterion("user_score_1 <=", value, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1In(List<Integer> values) {
            addCriterion("user_score_1 in", values, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1NotIn(List<Integer> values) {
            addCriterion("user_score_1 not in", values, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1Between(Integer value1, Integer value2) {
            addCriterion("user_score_1 between", value1, value2, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserScore1NotBetween(Integer value1, Integer value2) {
            addCriterion("user_score_1 not between", value1, value2, "userScore1");
            return (Criteria) this;
        }

        public Criteria andUserId3IsNull() {
            addCriterion("user_id_3 is null");
            return (Criteria) this;
        }

        public Criteria andUserId3IsNotNull() {
            addCriterion("user_id_3 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId3EqualTo(Long value) {
            addCriterion("user_id_3 =", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3NotEqualTo(Long value) {
            addCriterion("user_id_3 <>", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3GreaterThan(Long value) {
            addCriterion("user_id_3 >", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3GreaterThanOrEqualTo(Long value) {
            addCriterion("user_id_3 >=", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3LessThan(Long value) {
            addCriterion("user_id_3 <", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3LessThanOrEqualTo(Long value) {
            addCriterion("user_id_3 <=", value, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3In(List<Long> values) {
            addCriterion("user_id_3 in", values, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3NotIn(List<Long> values) {
            addCriterion("user_id_3 not in", values, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3Between(Long value1, Long value2) {
            addCriterion("user_id_3 between", value1, value2, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId3NotBetween(Long value1, Long value2) {
            addCriterion("user_id_3 not between", value1, value2, "userId3");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNull() {
            addCriterion("user_id_2 is null");
            return (Criteria) this;
        }

        public Criteria andUserId2IsNotNull() {
            addCriterion("user_id_2 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId2EqualTo(Long value) {
            addCriterion("user_id_2 =", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotEqualTo(Long value) {
            addCriterion("user_id_2 <>", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThan(Long value) {
            addCriterion("user_id_2 >", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2GreaterThanOrEqualTo(Long value) {
            addCriterion("user_id_2 >=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThan(Long value) {
            addCriterion("user_id_2 <", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2LessThanOrEqualTo(Long value) {
            addCriterion("user_id_2 <=", value, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2In(List<Long> values) {
            addCriterion("user_id_2 in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotIn(List<Long> values) {
            addCriterion("user_id_2 not in", values, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2Between(Long value1, Long value2) {
            addCriterion("user_id_2 between", value1, value2, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId2NotBetween(Long value1, Long value2) {
            addCriterion("user_id_2 not between", value1, value2, "userId2");
            return (Criteria) this;
        }

        public Criteria andUserId1IsNull() {
            addCriterion("user_id_1 is null");
            return (Criteria) this;
        }

        public Criteria andUserId1IsNotNull() {
            addCriterion("user_id_1 is not null");
            return (Criteria) this;
        }

        public Criteria andUserId1EqualTo(Long value) {
            addCriterion("user_id_1 =", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotEqualTo(Long value) {
            addCriterion("user_id_1 <>", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThan(Long value) {
            addCriterion("user_id_1 >", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1GreaterThanOrEqualTo(Long value) {
            addCriterion("user_id_1 >=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThan(Long value) {
            addCriterion("user_id_1 <", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1LessThanOrEqualTo(Long value) {
            addCriterion("user_id_1 <=", value, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1In(List<Long> values) {
            addCriterion("user_id_1 in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotIn(List<Long> values) {
            addCriterion("user_id_1 not in", values, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1Between(Long value1, Long value2) {
            addCriterion("user_id_1 between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andUserId1NotBetween(Long value1, Long value2) {
            addCriterion("user_id_1 not between", value1, value2, "userId1");
            return (Criteria) this;
        }

        public Criteria andWinnerIsNull() {
            addCriterion("winner is null");
            return (Criteria) this;
        }

        public Criteria andWinnerIsNotNull() {
            addCriterion("winner is not null");
            return (Criteria) this;
        }

        public Criteria andWinnerEqualTo(Long value) {
            addCriterion("winner =", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerNotEqualTo(Long value) {
            addCriterion("winner <>", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerGreaterThan(Long value) {
            addCriterion("winner >", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerGreaterThanOrEqualTo(Long value) {
            addCriterion("winner >=", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerLessThan(Long value) {
            addCriterion("winner <", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerLessThanOrEqualTo(Long value) {
            addCriterion("winner <=", value, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerIn(List<Long> values) {
            addCriterion("winner in", values, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerNotIn(List<Long> values) {
            addCriterion("winner not in", values, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerBetween(Long value1, Long value2) {
            addCriterion("winner between", value1, value2, "winner");
            return (Criteria) this;
        }

        public Criteria andWinnerNotBetween(Long value1, Long value2) {
            addCriterion("winner not between", value1, value2, "winner");
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