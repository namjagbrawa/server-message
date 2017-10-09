/**
 * Project: dubbo.registry.console-2.2.0-SNAPSHOT
 * <p>
 * File Created at Mar 21, 2012
 * $Id: RegistryServerInfo.java 182143 2012-06-27 03:25:50Z tony.chenl $
 * <p>
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.bingo.server.utils;

import com.bingo.framework.common.URL;
import com.bingo.framework.common.utils.LoadBalance;
import com.bingo.framework.registry.sync.RegistryServerSync;
import com.bingo.framework.rpc.protocol.bingo.BingoProtocol;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;

import java.util.List;

public final class RegistryCenter {

    public static URL select(Class service) {
        String serviceName = service.getName();
        List<URL> providers = RegistryServerSync.getProviders(serviceName);
        URL url = LoadBalance.doSelect(providers);
        if (Check.isNull(url)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "服务器选择错误, interface : " + serviceName + " serviceName : " + serviceName + " providers : " + providers);
        }
        return url;
    }
}

