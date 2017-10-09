package com.bingo.server.database.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DdzGameHistoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitRows;

    public DdzGameHistoryExample() {
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

        public Criteria andSiteCard1IsNull() {
            addCriterion("site_card_1 is null");
            return (Criteria) this;
        }

        public Criteria andSiteCard1IsNotNull() {
            addCriterion("site_card_1 is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCard1EqualTo(String value) {
            addCriterion("site_card_1 =", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1NotEqualTo(String value) {
            addCriterion("site_card_1 <>", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1GreaterThan(String value) {
            addCriterion("site_card_1 >", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1GreaterThanOrEqualTo(String value) {
            addCriterion("site_card_1 >=", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1LessThan(String value) {
            addCriterion("site_card_1 <", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1LessThanOrEqualTo(String value) {
            addCriterion("site_card_1 <=", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1Like(String value) {
            addCriterion("site_card_1 like", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1NotLike(String value) {
            addCriterion("site_card_1 not like", value, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1In(List<String> values) {
            addCriterion("site_card_1 in", values, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1NotIn(List<String> values) {
            addCriterion("site_card_1 not in", values, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1Between(String value1, String value2) {
            addCriterion("site_card_1 between", value1, value2, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard1NotBetween(String value1, String value2) {
            addCriterion("site_card_1 not between", value1, value2, "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard2IsNull() {
            addCriterion("site_card_2 is null");
            return (Criteria) this;
        }

        public Criteria andSiteCard2IsNotNull() {
            addCriterion("site_card_2 is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCard2EqualTo(String value) {
            addCriterion("site_card_2 =", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2NotEqualTo(String value) {
            addCriterion("site_card_2 <>", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2GreaterThan(String value) {
            addCriterion("site_card_2 >", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2GreaterThanOrEqualTo(String value) {
            addCriterion("site_card_2 >=", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2LessThan(String value) {
            addCriterion("site_card_2 <", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2LessThanOrEqualTo(String value) {
            addCriterion("site_card_2 <=", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2Like(String value) {
            addCriterion("site_card_2 like", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2NotLike(String value) {
            addCriterion("site_card_2 not like", value, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2In(List<String> values) {
            addCriterion("site_card_2 in", values, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2NotIn(List<String> values) {
            addCriterion("site_card_2 not in", values, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2Between(String value1, String value2) {
            addCriterion("site_card_2 between", value1, value2, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard2NotBetween(String value1, String value2) {
            addCriterion("site_card_2 not between", value1, value2, "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard3IsNull() {
            addCriterion("site_card_3 is null");
            return (Criteria) this;
        }

        public Criteria andSiteCard3IsNotNull() {
            addCriterion("site_card_3 is not null");
            return (Criteria) this;
        }

        public Criteria andSiteCard3EqualTo(String value) {
            addCriterion("site_card_3 =", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3NotEqualTo(String value) {
            addCriterion("site_card_3 <>", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3GreaterThan(String value) {
            addCriterion("site_card_3 >", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3GreaterThanOrEqualTo(String value) {
            addCriterion("site_card_3 >=", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3LessThan(String value) {
            addCriterion("site_card_3 <", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3LessThanOrEqualTo(String value) {
            addCriterion("site_card_3 <=", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3Like(String value) {
            addCriterion("site_card_3 like", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3NotLike(String value) {
            addCriterion("site_card_3 not like", value, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3In(List<String> values) {
            addCriterion("site_card_3 in", values, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3NotIn(List<String> values) {
            addCriterion("site_card_3 not in", values, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3Between(String value1, String value2) {
            addCriterion("site_card_3 between", value1, value2, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andSiteCard3NotBetween(String value1, String value2) {
            addCriterion("site_card_3 not between", value1, value2, "siteCard3");
            return (Criteria) this;
        }

        public Criteria andPullCardIsNull() {
            addCriterion("pull_card is null");
            return (Criteria) this;
        }

        public Criteria andPullCardIsNotNull() {
            addCriterion("pull_card is not null");
            return (Criteria) this;
        }

        public Criteria andPullCardEqualTo(String value) {
            addCriterion("pull_card =", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardNotEqualTo(String value) {
            addCriterion("pull_card <>", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardGreaterThan(String value) {
            addCriterion("pull_card >", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardGreaterThanOrEqualTo(String value) {
            addCriterion("pull_card >=", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardLessThan(String value) {
            addCriterion("pull_card <", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardLessThanOrEqualTo(String value) {
            addCriterion("pull_card <=", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardLike(String value) {
            addCriterion("pull_card like", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardNotLike(String value) {
            addCriterion("pull_card not like", value, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardIn(List<String> values) {
            addCriterion("pull_card in", values, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardNotIn(List<String> values) {
            addCriterion("pull_card not in", values, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardBetween(String value1, String value2) {
            addCriterion("pull_card between", value1, value2, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullCardNotBetween(String value1, String value2) {
            addCriterion("pull_card not between", value1, value2, "pullCard");
            return (Criteria) this;
        }

        public Criteria andPullSiteIsNull() {
            addCriterion("pull_site is null");
            return (Criteria) this;
        }

        public Criteria andPullSiteIsNotNull() {
            addCriterion("pull_site is not null");
            return (Criteria) this;
        }

        public Criteria andPullSiteEqualTo(Integer value) {
            addCriterion("pull_site =", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteNotEqualTo(Integer value) {
            addCriterion("pull_site <>", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteGreaterThan(Integer value) {
            addCriterion("pull_site >", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteGreaterThanOrEqualTo(Integer value) {
            addCriterion("pull_site >=", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteLessThan(Integer value) {
            addCriterion("pull_site <", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteLessThanOrEqualTo(Integer value) {
            addCriterion("pull_site <=", value, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteIn(List<Integer> values) {
            addCriterion("pull_site in", values, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteNotIn(List<Integer> values) {
            addCriterion("pull_site not in", values, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteBetween(Integer value1, Integer value2) {
            addCriterion("pull_site between", value1, value2, "pullSite");
            return (Criteria) this;
        }

        public Criteria andPullSiteNotBetween(Integer value1, Integer value2) {
            addCriterion("pull_site not between", value1, value2, "pullSite");
            return (Criteria) this;
        }

        public Criteria andRemainCardIsNull() {
            addCriterion("remain_card is null");
            return (Criteria) this;
        }

        public Criteria andRemainCardIsNotNull() {
            addCriterion("remain_card is not null");
            return (Criteria) this;
        }

        public Criteria andRemainCardEqualTo(String value) {
            addCriterion("remain_card =", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardNotEqualTo(String value) {
            addCriterion("remain_card <>", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardGreaterThan(String value) {
            addCriterion("remain_card >", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardGreaterThanOrEqualTo(String value) {
            addCriterion("remain_card >=", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardLessThan(String value) {
            addCriterion("remain_card <", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardLessThanOrEqualTo(String value) {
            addCriterion("remain_card <=", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardLike(String value) {
            addCriterion("remain_card like", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardNotLike(String value) {
            addCriterion("remain_card not like", value, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardIn(List<String> values) {
            addCriterion("remain_card in", values, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardNotIn(List<String> values) {
            addCriterion("remain_card not in", values, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardBetween(String value1, String value2) {
            addCriterion("remain_card between", value1, value2, "remainCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardNotBetween(String value1, String value2) {
            addCriterion("remain_card not between", value1, value2, "remainCard");
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

        public Criteria andSiteCard1LikeInsensitive(String value) {
            addCriterion("upper(site_card_1) like", value.toUpperCase(), "siteCard1");
            return (Criteria) this;
        }

        public Criteria andSiteCard2LikeInsensitive(String value) {
            addCriterion("upper(site_card_2) like", value.toUpperCase(), "siteCard2");
            return (Criteria) this;
        }

        public Criteria andSiteCard3LikeInsensitive(String value) {
            addCriterion("upper(site_card_3) like", value.toUpperCase(), "siteCard3");
            return (Criteria) this;
        }

        public Criteria andPullCardLikeInsensitive(String value) {
            addCriterion("upper(pull_card) like", value.toUpperCase(), "pullCard");
            return (Criteria) this;
        }

        public Criteria andRemainCardLikeInsensitive(String value) {
            addCriterion("upper(remain_card) like", value.toUpperCase(), "remainCard");
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