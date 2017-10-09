package com.bingo.server.game.ddz.service;

import com.bingo.framework.common.utils.IDGen;
import com.bingo.server.database.dao.DdzOptionMapper;
import com.bingo.server.database.dao.DdzSubjectMapper;
import com.bingo.server.database.dao.DdzTypeMapper;
import com.bingo.server.database.model.*;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.provider.DdzRuleProvider;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.Operation;
import com.bingo.server.game.provider.bean.enums.Relation;
import com.bingo.server.game.provider.bean.enums.Type;
import com.bingo.server.msg.BASE;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.RESP;
import com.bingo.server.utils.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ZhangGe on 2017/7/18.
 */
@Service
public class DdzRuleService implements DdzRuleProvider {

    private static Logger logger = LoggerFactory.getLogger(DdzRuleService.class);

    private static volatile Map<String, DdzType> ddzTypeMap;
    private static volatile Map<String, DdzSubject> ddzSubjectMap;
    private static volatile Map<String, DdzOption> ddzOptionMap;

    private static volatile List<Map> rules;
    private static volatile RESP.DdzGetRuleResponse ddzGetRuleResponse;

    @Autowired
    private DdzTypeMapper ddzTypeMapper;
    @Autowired
    private DdzSubjectMapper ddzSubjectMapper;
    @Autowired
    private DdzOptionMapper ddzOptionMapper;

    public void ruleReferesh() {
        List<DdzType> ddzTypes = ddzTypeMapper.selectByExample(new DdzTypeExample());
        List<DdzSubject> ddzSubjects = ddzSubjectMapper.selectByExample(new DdzSubjectExample());
        List<DdzOption> ddzOptions = ddzOptionMapper.selectByExample(new DdzOptionExample());

        Map<String, DdzType> newDdzTypeMap = new HashMap<>();
        for (DdzType ddzType : ddzTypes) {
            String typeName = ddzType.getTypeName();
            newDdzTypeMap.put(typeName, ddzType);
        }
        this.ddzTypeMap = newDdzTypeMap;
        if (Check.isNull(ddzTypeMap)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "没有配置Type类型");
        }

        Map<String, DdzSubject> newDdzSubjectMap = new HashMap<>();
        for (DdzSubject ddzSubject : ddzSubjects) {
            String subjectName = ddzSubject.getSubjectName();
            newDdzSubjectMap.put(subjectName, ddzSubject);
        }
        this.ddzSubjectMap = newDdzSubjectMap;

        Map<String, DdzOption> newDdzOptionMap = new HashMap<>();
        for (DdzOption ddzOption : ddzOptions) {
            String optionName = ddzOption.getOptionName();
            newDdzOptionMap.put(optionName, ddzOption);
        }
        this.ddzOptionMap = newDdzOptionMap;

        List<Map> newRules = new ArrayList<>();
        RESP.DdzGetRuleResponse.Builder ruleBuilder = RESP.DdzGetRuleResponse.newBuilder();
        for (DdzType ddzType : ddzTypeMap.values()) {
            Map<String, Object> types = new HashMap<>();
            BASE.DdzType.Builder typeBuilder = BASE.DdzType.newBuilder();
            String[] subjectNames = ddzType.getSubjects().split(",");
            List<Map> subjects = new ArrayList<>();
            for (String subjectName : subjectNames) {
                Map<String, Object> subject = new HashMap<>();
                BASE.Subject.Builder subjectBuilder = BASE.Subject.newBuilder();
                DdzSubject ddzSubject = ddzSubjectMap.get(subjectName);
                if (Check.isNull(ddzSubject)) {
                    throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "此Subject类型不存在,未配置,请添加后重试, subjectName : " + subjectName);
                }
                String[] optionNames = ddzSubject.getOptions().split(",");
                List<Map> options = new ArrayList<>();
                for (String optionName : optionNames) {
                    Map<String, String> option = new HashMap<>();
                    BASE.Option.Builder optionBuilder = BASE.Option.newBuilder();
                    DdzOption ddzOption = ddzOptionMap.get(optionName);
                    if (Check.isNull(ddzOption)) {
                        throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "此Option类型不存在,未配置,请添加后重试, optionName : " + optionName);
                    }
                    option.put("name", ddzOption.getOptionName());
                    option.put("text", ddzOption.getOptionText());
                    option.put("tip", ddzOption.getOptionTip());
                    options.add(option);
                    optionBuilder.setName(ddzOption.getOptionName());
                    optionBuilder.setText(ddzOption.getOptionText());
                    optionBuilder.setTip(ddzOption.getOptionTip());
                    subjectBuilder.addOptions(optionBuilder);
                }
                subject.put("name", ddzSubject.getSubjectName());
                subject.put("text", ddzSubject.getSubjectText());
                subject.put("tip", ddzSubject.getSubjectTip());
                subject.put("relation", ddzSubject.getRelation());
                subject.put("options", options);
                subjects.add(subject);
                subjectBuilder.setName(ddzSubject.getSubjectName());
                subjectBuilder.setText(ddzSubject.getSubjectText());
                subjectBuilder.setTip(ddzSubject.getSubjectTip());
                subjectBuilder.setRelation(ddzSubject.getRelation());
                typeBuilder.addSubjects(subjectBuilder);
            }
            types.put("name", ddzType.getTypeName());
            types.put("text", ddzType.getTypeText());
            types.put("tip", ddzType.getTypeTip());
            types.put("order", ddzType.getTypeOrder());
            types.put("subjects", subjects);
            newRules.add(types);
            typeBuilder.setName(ddzType.getTypeName());
            typeBuilder.setText(ddzType.getTypeText());
            typeBuilder.setTip(ddzType.getTypeTip());
            typeBuilder.setOrder(ddzType.getTypeOrder());
            ruleBuilder.addDdzType(typeBuilder);
        }
        this.rules = newRules;
        this.ddzGetRuleResponse = ruleBuilder.build();
    }

    // TODO 可以做成定时,存入缓存,还要写配置验证程序,可以在配置的时候做配置校验
    @Override
    public List<Map> getDdzRule() {
        if (Check.isNull(rules)) {
            ruleReferesh();
        }
        return rules;
    }

    @Override
    public byte[] getDdzGetRuleResponse() {
        if (Check.isNull(ddzGetRuleResponse)) {
            ruleReferesh();
        }
        return ddzGetRuleResponse.toByteArray();
    }

    @Override
    public DdzRule parseRule(List<String> optionNames) {
        DdzOptionExample optionExample = new DdzOptionExample();
        optionExample.createCriteria().andOptionNameIn(optionNames);
        List<DdzOption> options = ddzOptionMapper.selectByExample(optionExample);
        if (Check.isNull(options)) {
            logger.error("没有配置项存在, optionNames : " + optionNames);
            return null;
        }

        DdzRule ddzRule = new DdzRule();
        for (DdzOption ddzOption : options) {
            setField(ddzRule, ddzOption);
        }

        return ddzRule;
    }

    private void setField(DdzRule ddzRule, DdzOption ddzOption) {
        String operationName = ddzOption.getOperation();
        Operation operation = Operation.valueOf(operationName);
        switch (operation) {
            case ddzClassic:
                ddzRule.setDdzType(operation);
                break;
            case ddzRascal:
                ddzRule.setDdzType(operation);
                break;
            case ddzLighting:
                ddzRule.setDdzType(operation);
                break;
            case playNumber:
                ddzRule.setPlayNumber(Integer.valueOf(ddzOption.getValue1()));
                ddzRule.setRoomCard(Integer.valueOf(ddzOption.getValue2()));
                break;
            case rascalNumber:
                ddzRule.setRascalNumber(Integer.valueOf(ddzOption.getValue1()));
                break;
            case upperLimit:
                ddzRule.setUpperLimit(Integer.valueOf(ddzOption.getValue1()));
                break;
            case except:
                List<Integer> cards = new ArrayList<>();
                for (String card : ddzOption.getValue1().split(",")) {
                    cards.add(Integer.valueOf(card));
                }
                ddzRule.setExcept(cards);
                break;
            case callLordWinner:
                ddzRule.setCallLord(operation);
                break;
            case callLordPolling:
                ddzRule.setCallLord(operation);
                break;
            case snatchLordDirect:
                ddzRule.setSnatchLord(operation);
                break;
            case snatchLordThreeUpper:
                ddzRule.setSnatchLord(operation);
                break;
            case snatchLordDouble:
                ddzRule.setSnatchLord(operation);
                break;
            case kickPullOnce:
                ddzRule.setKickPull(operation);
                break;
            case kickPullOut:
                ddzRule.setKickPull(operation);
                break;
            case kickPullNo:
                ddzRule.setKickPull(operation);
                break;
            case dealCard:
                ddzRule.setDealCardTime(Integer.valueOf(ddzOption.getValue1()));
                ddzRule.setMinBomb(Integer.valueOf(ddzOption.getValue2()));
                ddzRule.setMaxBomb(Integer.valueOf(ddzOption.getValue3()));
                break;
            case handCard:
                ddzRule.setHandCard(Integer.valueOf(ddzOption.getValue1()));
                break;
            case seenCard:
                ddzRule.setSeenCard(true);
                break;
            case tracker:
                ddzRule.setTracker(true);
                break;
            case rascalCardNoKing:
                ddzRule.setRascalCardNoKing(true);
                break;
            case threeCardNoOne:
                ddzRule.setThreeCardNoOne(true);
                break;
            case threeCardNoPair:
                ddzRule.setThreeCardNoPair(true);
                break;
            case fourCardNoPair:
                ddzRule.setFourCardNoPair(true);
                break;
            case fourCardNoOneAndPair:
                ddzRule.setFourCardNoOneAndPair(true);
                break;
            case flyNoPair:
                ddzRule.setFlyNoPair(true);
                break;
            case kingBombNoBreak:
                ddzRule.setKingBombNoBreak(true);
                break;
            default:
                throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "没有此类型的Option, Option : " + ddzOption);
        }
    }

    @Override
    public void addType(Type type, String text, String tip, int order, List<String> subjectNames) {
        DdzTypeExample ddzTypeExample = new DdzTypeExample();
        ddzTypeExample.createCriteria().andTypeNameEqualTo(type.name());
        List<DdzType> ddzTypes = ddzTypeMapper.selectByExample(ddzTypeExample);
        if (!Check.isNull(ddzTypes)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "规则类型已存在, ddzType : " + ddzTypes.get(0));
        }
        DdzType ddzType = new DdzType();
        ddzType.setId(IDGen.getId());
        ddzType.setCreateTime(new Date());
        StringBuilder sb = new StringBuilder();
        for (String subjectName : subjectNames) {
            sb.append(subjectName).append(",");
        }
        ddzType.setSubjects(sb.substring(0, sb.length() - 1));
        ddzType.setTypeName(type.name());
        ddzType.setTypeOrder(order);
        ddzType.setTypeText(text);
        ddzType.setTypeTip(tip);
        ddzTypeMapper.insert(ddzType);
    }

    @Override
    public void addSubject(String subjectName, String text, String tip, List<String> optionNames, Relation relation) {
        DdzSubjectExample ddzSubjectExample = new DdzSubjectExample();
        ddzSubjectExample.createCriteria().andSubjectNameEqualTo(subjectName);
        List<DdzSubject> ddzSubjects = ddzSubjectMapper.selectByExample(ddzSubjectExample);
        if (!Check.isNull(ddzSubjects)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "选项已存在, ddzSubject : " + ddzSubjects.get(0));
        }
        DdzSubject ddzSubject = new DdzSubject();
        ddzSubject.setId(IDGen.getId());
        ddzSubject.setSubjectName(subjectName);
        ddzSubject.setCreateTime(new Date());
        StringBuilder sb = new StringBuilder();
        for (String optionName : optionNames) {
            sb.append(optionName).append(",");
        }
        ddzSubject.setOptions(sb.substring(0, sb.length() - 1));
        ddzSubject.setRelation(relation.name());
        ddzSubject.setSubjectText(text);
        ddzSubject.setSubjectTip(tip);
        ddzSubjectMapper.insert(ddzSubject);
    }

    @Override
    public void addOption(String optionName, String text, String tip, String value1, String value2, String value3, Operation operation) {
        DdzOptionExample ddzOptionExample = new DdzOptionExample();
        ddzOptionExample.createCriteria().andOptionNameEqualTo(optionName);
        List<DdzOption> ddzOptions = ddzOptionMapper.selectByExample(ddzOptionExample);
        if (!Check.isNull(ddzOptions)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "配置项已存在, DdzOption : " + ddzOptions.get(0));
        }
        DdzOption ddzOption = new DdzOption();
        ddzOption.setId(IDGen.getId());
        ddzOption.setOptionName(optionName);
        ddzOption.setCreateTime(new Date());
        ddzOption.setOperation(operation.name());
        ddzOption.setOptionText(text);
        ddzOption.setOptionTip(tip);
        ddzOption.setValue1(value1);
        ddzOption.setValue2(value2);
        ddzOption.setValue3(value3);
        ddzOptionMapper.insert(ddzOption);
    }

    @Override
    public void delDdzType(long id) {
        ddzTypeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delSubject(long id) {
        ddzSubjectMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void delOption(long id) {
        ddzOptionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateType(DdzType ddzType) {
        DdzType db = ddzTypeMapper.selectByPrimaryKey(ddzType.getId());
        if (Check.isNull(db)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "不存在此项记录, ddzType : " + ddzType);
        }
        ddzType.setModifyTime(new Date());
        ddzTypeMapper.updateByPrimaryKey(ddzType);
    }

    @Override
    public void updateSubject(DdzSubject ddzSubject) {
        DdzSubject db = ddzSubjectMapper.selectByPrimaryKey(ddzSubject.getId());
        if (Check.isNull(db)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "不存在此项记录, ddzSubject : " + ddzSubject);
        }
        ddzSubject.setModifyTime(new Date());
        ddzSubjectMapper.updateByPrimaryKey(ddzSubject);
    }

    @Override
    public void updateOption(DdzOption ddzOption) {
        DdzOption db = ddzOptionMapper.selectByPrimaryKey(ddzOption.getId());
        if (Check.isNull(db)) {
            throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "不存在此项记录, ddzOption : " + ddzOption);
        }
        ddzOption.setModifyTime(new Date());
        ddzOptionMapper.updateByPrimaryKey(ddzOption);
    }
}
