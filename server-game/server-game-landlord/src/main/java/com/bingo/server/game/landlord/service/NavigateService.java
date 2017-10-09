package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.exception.ServerException;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.game.landlord.service.match.MatchProvider;
import com.bingo.server.game.provider.landlord.LandlordProvider;
import com.bingo.server.msg.*;
import com.bingo.server.utils.Check;
import com.google.protobuf.Descriptors;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ZhangGe on 2017/8/3.
 */
@Service
public class NavigateService implements LandlordProvider {

    private final Logger logger = LoggerFactory.getLogger(NavigateService.class);

    @Autowired
    private MoneyExchangeProvider moneyExchangeService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private FightProvider fightService;
    @Autowired
    private MatchProvider matchService;

    @Override
    public byte[] process(byte[] bytes, long userId, long topic) {
        if (Check.isNull(bytes)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "CS Message is null");
        }
        ClientMessage.CS message = null;
        try {
            message = ClientMessage.CS.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            logger.error("解析ClientMessage错误, bytes : " + bytes);

        }
        Map<Descriptors.FieldDescriptor, Object> allFields = message.getAllFields();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entrySet : allFields.entrySet()) {
            String name = entrySet.getKey().getName();
            Role role = RoleCache.getRoleById(userId);
            if (Check.isNull(role)) {
                role = roleService.createRole(userId, topic);
                if (Check.isNull(role)) {
                    throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "创建Role失败");
                }
            }
            try {
                logger.info("message : " + message);
                switch (name) {
                    case "MatchCreateGameRequest":
                        Match.MatchCreateGameRequest matchCreateGameRequest = message.getMatchCreateGameRequest();
                        return matchService.createRoom(role, matchCreateGameRequest.getGameConfig()).toByteArray();
                    case "MatchJoinGameRequest":
                        Match.MatchJoinGameRequest matchJoinGameRequest = message.getMatchJoinGameRequest();
                        return matchService.joinGame(role, matchJoinGameRequest.getLockString()).toByteArray();
                    case "MatchRoleRequest":
                        matchService.match(role);
                        return null;
                    case "MatchAIRequest":
                        matchService.matchAI(role);
                        return null;
                    case "MoneyExchangeRequest":
                        MoneyExchange.MoneyExchangeRequest request = message.getMoneyExchangeRequest();
                        return moneyExchangeService.moneyExchange(role, request.getAdd(), request.getNum()).toByteArray();
                    case "FightReadyRequest":
                        fightService.readyGame(role);
                        return null;
                    case "FightMingPaiRequest":
                        fightService.mingPai(role);
                        return null;
                    case "FightRecommandRequest":
                        return fightService.recommandCardList(role).toByteArray();
                    case "FightSendCardRequest":
                        Fight.FightSendCardRequest fightSendCardRequest = message.getFightSendCardRequest();
                        fightService.sendCard(role, fightSendCardRequest.getPaiList());
                        return null;
                    case "FightGetlastRoundRequest":
                        return fightService.getLastRecord(role.getGameId()).toByteArray();
                    case "FightExitGameRequest":
                        return fightService.exitGame(role).toByteArray();
                    case "FightCallLandLordRequest":
                        Fight.FightCallLandLordRequest fightCallLandLordRequest = message.getFightCallLandLordRequest();
                        fightService.callLandlord(role, fightCallLandLordRequest.getFen());
                        return null;
                    default:
                        throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "没有这个命令, CS Message : " + message);
                }
            } catch (Exception e) {
                logger.error("执行错误 protocol：" + name, e);
                throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "执行错误, CS Message : " + message);
            }
        }
        throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "错误的协议, CS Message : " + message);
    }
}
