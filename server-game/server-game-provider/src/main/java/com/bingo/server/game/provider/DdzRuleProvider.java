package com.bingo.server.game.provider;


import com.bingo.server.database.model.DdzOption;
import com.bingo.server.database.model.DdzSubject;
import com.bingo.server.database.model.DdzType;
import com.bingo.server.game.provider.bean.DdzRule;
import com.bingo.server.game.provider.bean.enums.Operation;
import com.bingo.server.game.provider.bean.enums.Relation;
import com.bingo.server.game.provider.bean.enums.Type;

import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGe on 2017/7/18.
 */
public interface DdzRuleProvider {

    /**
     * 玩法List,包含Map,SubjectList包含Map,OptionList包含Map
     * @return
     */
    List<Map> getDdzRule();

    byte[] getDdzGetRuleResponse();

    DdzRule parseRule(List<String> optionNames);

    void addType(Type type, String text, String tip, int order, List<String> subjectNames);

    void addSubject(String subjectName, String text, String tip, List<String> optionNames, Relation relation);

    void addOption(String optionName, String text, String tip, String value1, String value2, String value3, Operation operation);

    void delDdzType(long id);

    void delSubject(long id);

    void delOption(long id);

    void updateType(DdzType ddzType);

    void updateSubject(DdzSubject ddzSubject);

    void updateOption(DdzOption ddzOption);


}
