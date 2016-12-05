package com.zhubajie.oabern.redis.demo.extend.lock;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 分布式业务锁
 * @author OABern
 * Created by OABern on 2016/11/29.
 */
public class BizLock {
    private static final String LOCK = "T";
    private static final String UNLOCK = "F";
    private static final String HM_KEY = "OABern:BizLock";
    private static String host;
    private static int port;
    private static BizLock bizLock;

    public static Set<String> bizLockKeySet;

    private volatile boolean isLive = false;
    private JedisPool jedisPool;

    /**
     * 获取单例
     * @return
     */
    public static BizLock getSingleInstance() {
        if(bizLock != null) {
            return bizLock;
        }else {
            synchronized(LOCK) {
                if(bizLock != null) {
                    return bizLock;
                }else {
                    bizLock = new BizLock();
                    bizLock.init(bizLockKeySet);
                    return bizLock;
                }
            }
        }
    }

    /**
     * 初始化JedisPool，业务锁键
     * @param bizLockKeySet
     */
    private void init(Set<String> bizLockKeySet) {
        //初始化业务锁
        host = "localhost";
        port = 6379;
        jedisPool = new JedisPool(new JedisPoolConfig(), host, port);

        //初始化业务锁键Map
        Map<String, String> map = new HashMap<String, String>();
        for(String k : bizLockKeySet) {
            map.put(k, UNLOCK);
        }
        jedisPool.getResource().hmset(HM_KEY, map);

        //标志业务锁可用
        isLive = true;
    }

    /**
     * 尝试获取锁
     * @param bizKey 业务锁键
     * @return
     */
    public boolean tryLock(final String bizKey) {
        if(!check()) {
            return false;
        }
        Jedis jedis = jedisPool.getResource();

        jedis.watch(bizKey);    //监视key变化
        final Boolean lock = getLockFlag(jedis, bizKey);
        if(lock == null) {     //key不存在
           return false;
        }

        if(lock) {      //锁定中
            return false;

        }else {     //未锁定
            Transaction tx = jedis.multi();
            tx.hset(HM_KEY, bizKey, LOCK);

            try {
                List<Object> result = tx.exec();
                System.out.println(JSON.toJSON(result));

                if (result == null) {    //乐观锁，事务操作失败
                    System.out.println("事务操作失败！");
                    return false;

                } else {     //事务操作成功

                    return true;
                }
            } catch(Exception e) {
                System.out.println("事务执行异常！释放锁..");
                release(bizKey);
                e.printStackTrace();
                return false;
            }
        }

    }

    /**
     * 释放锁
     * @return
     */
    public boolean release(String bizKey) {
        if(!check()) {
            return false;
        }
        Jedis jedis = jedisPool.getResource();

        String lockFlag = jedis.hget(HM_KEY, bizKey);
        if(LOCK.equals(lockFlag)) {
            jedis.hset(HM_KEY, bizKey, UNLOCK);
        }

        return true;
    }

    private boolean check() {
        return isLive;
    }

    /**
     * 获取锁状态
     * @param bizKey 业务锁键
     * @return null表示不存在锁键， true表示锁定，false表示未锁定
     */
    private Boolean getLockFlag(final Jedis jedis ,String bizKey) {
        String value = jedis.hget(HM_KEY, bizKey);
        if(value==null) {
            return null;
        }

        return LOCK.equals(value);
    }

}
