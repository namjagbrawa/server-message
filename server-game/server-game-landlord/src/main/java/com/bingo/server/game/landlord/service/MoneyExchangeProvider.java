package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.template.ObserveBaseServiceInterface;
import com.bingo.server.msg.ServerMessage;

public interface MoneyExchangeProvider extends ObserveBaseServiceInterface {

    ServerMessage.SC moneyExchange(Role role, boolean add, int num);

    boolean exchangeMoney(Role role, int money, boolean add);

    void newRoleInit(Role role);

}
