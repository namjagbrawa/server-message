package com.bingo.server.utils;

import com.bingo.framework.common.URL;
import com.bingo.framework.remoting.exchange.ExchangeServer;
import com.bingo.framework.rpc.Exporter;
import com.bingo.framework.rpc.protocol.bingo.BingoProtocol;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by ZhangGe on 2017/6/20.
 */
public class LocalService {

    public static URL getUrl() {
        Collection<ExchangeServer> servers = BingoProtocol.getBingoProtocol().getServers();
        if (servers != null && servers.size() == 1) {
            return servers.iterator().next().getUrl();
        }
        if (servers.size() > 1) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "存在多个服务，请使用getInfo(Name)方法获取");
        }
        throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "本地服务不存在");
    }

    public static URL getUrl(Class service) {
        String serviceName = service.getName();
        Collection<Exporter<?>> serverKeys = BingoProtocol.getBingoProtocol().getExporters();
        if (serverKeys != null) {
            Iterator<Exporter<?>> iterator = serverKeys.iterator();
            while (iterator.hasNext()) {
                Exporter<?> next = iterator.next();
                if (next.getInvoker().getUrl().toServiceString().contains(serviceName)) {
                    return next.getInvoker().getUrl();
                }
            }
        }
        throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "本地服务不存在");
    }
}
