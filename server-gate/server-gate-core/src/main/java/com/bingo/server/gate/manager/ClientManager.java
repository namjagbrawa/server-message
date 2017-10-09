package com.bingo.server.gate.manager;

import com.bingo.server.exception.ServerException;
import com.bingo.server.gate.bean.Client;
import com.bingo.server.utils.AbstractMemory;

/**
 * Key Is User Topic, Get it From DB
 * Created by ZhangGe on 2017/7/12.
 */
public class ClientManager extends AbstractMemory<Long, Client>{

    private static class ClientManagerHolder {
        private static final ClientManager instance = new ClientManager();
    }

    public static ClientManager get() {
        return ClientManager.ClientManagerHolder.instance;
    }

}
