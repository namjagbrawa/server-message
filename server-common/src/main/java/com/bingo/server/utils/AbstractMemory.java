package com.bingo.server.utils;

import com.bingo.framework.common.exception.BingoException;
import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ZhangGe on 2017/6/20.
 */
public class AbstractMemory<K, V> {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final Map<K, V> memory = new ConcurrentHashMap<>();

    public void put(K key, V value, boolean override, boolean log) {
        V t = memory.get(key);
        if (t != null) {
            if (override) {
                if (log) {
                    logger.warn("memory cache key is exist . key : " + key + " value : " + value + ", exist value : " + t);
                }
                memory.put(key, value);
            } else {
                throw new ServerException(MSG.MsgCode.BUSINESS_ERROR, "memory cache key is exist . key : " + key + " value : " + value + ", exist value : " + t);
            }
        } else {
            memory.put(key, value);
        }
    }

    public <V> V getValue(K key, boolean error, boolean log) {
        V t = (V) memory.get(key);
        if (t == null) {
            if (log) {
                logger.warn("memory cache is not exist , key : " + key);
            }
            if (error) {
                throw  new ServerException(MSG.MsgCode.BUSINESS_ERROR, "memory cache is not exist , key : " + key);
            }
            return null;
        }
        return t;
    }

    public boolean exist(K key) {
        return memory.containsKey(key);
    }

    public <T> T remove(K key, boolean log) {
        T remove = (T) memory.remove(key);
        if (remove == null) {
            logger.warn(key + " the key is not exist");
            return null;
        }
        return remove;
    }

}
