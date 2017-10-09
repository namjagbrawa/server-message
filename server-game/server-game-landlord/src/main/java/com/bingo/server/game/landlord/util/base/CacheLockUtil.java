package com.bingo.server.game.landlord.util.base;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 弱值缓存锁
 *
 * @author xjd
 */
@SuppressWarnings("deprecation")
public class CacheLockUtil {
    private static final Logger log = LoggerFactory
            .getLogger(CacheLockUtil.class);

    private final static Cache<String, ReentrantLock> cacheLockMap = CacheBuilder.newBuilder().weakValues().build();

    /**
     * 根据传入的对象获取与之对应的锁
     *
     * @param cls
     * @param key
     * @return
     */
    public static ReentrantLock getLock(Class<?> cls, int key) {
        return getReentrantLock(cls, Integer.valueOf(key));
    }

    /**
     * 根据传入的对象获取与之对应的锁
     *
     * @param cls
     * @param key
     * @return
     */
    public static ReentrantLock getLock(Class<?> cls, long key) {
        return getReentrantLock(cls, Long.valueOf(key));
    }

    /**
     * 根据传入的对象获取与之对应的锁
     *
     * @param cls
     * @param key
     * @return
     */
    public static ReentrantLock getLock(Class<?> cls, String key) {
        return getReentrantLock(cls, key);
    }

    /**
     * 用于监控和调试
     */
    protected static long size() {
        return cacheLockMap.size();
    }

    /**
     * 用于监控和调试
     */
    protected static boolean isEmpty() {
        return cacheLockMap.asMap().isEmpty();
    }

    private static ReentrantLock getReentrantLock(Class<?> cls, Object key) {
        StringBuilder sb = new StringBuilder();
        sb.append(cls.getName()).append("_");
        if (key != null) {
            sb.append(key.toString());
        } else {
            log.error("lock key为null. class : {}", cls.getName());
        }
        try {
            return cacheLockMap.get(sb.toString(), new Callable<ReentrantLock>() {
                @Override
                public ReentrantLock call() throws Exception {
                    return new ReentrantLock();
                }
            });
        } catch (ExecutionException e) {
            log.error("获取锁错误", e);
        }
        return null;
    }
}
