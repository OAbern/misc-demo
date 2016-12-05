package com.zhubajie.oabern.redis.demo.extend.lock;

import java.util.concurrent.TimeUnit;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//import com.netease.welkin.network.SystemThreadUtil;

public class RedisLock {

    private final JedisPool jedisPool;
    private static final Integer DEFAULT_SINGLE_EXPIRE_TIME = 40;

    private static RedisLock redisLock;

    public static RedisLock getInstance(JedisPool jedisPool) {
        if (redisLock == null) {
            redisLock = new RedisLock(jedisPool);
        }
        return redisLock;
    }
    public RedisLock(JedisPool jedisPool){
        this.jedisPool = jedisPool;
    }
    public boolean tryLock(String key) {
        return tryLock(key, 0L, TimeUnit.SECONDS);
    }

    public boolean tryLock(String key, long timeout) {
        return tryLock(key, timeout, TimeUnit.SECONDS);
    }
    /**
     * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
     * @param key
     * @param timeout 尝试获取锁等待的时间
     * @param unit
     * @return
     */
    public boolean tryLock(String key, long timeout, TimeUnit unit) {

        Jedis jedis = null;
        try {
            long nano = System.nanoTime();
            jedis = jedisPool.getResource();
            do {
                System.out.println("try lock key: " + key);
//                String ip = SystemThreadUtil.getLocalIP();
                String ip = "127.0.0.1";
                Long i = jedis.setnx(key, ip);
                if (i == 1) {
                    //锁的最大时间期限
                    jedis.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
                    System.out.println("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
                    return true;
                } else { // 存在锁
                    String desc = jedis.get(key);
                    System.out.println("key: " + key + " locked by another business：" + desc);
                }
                if (timeout == 0) {
                    break;
                }
                Thread.sleep(5000);
            } while ((System.nanoTime() - nano) < unit.toNanos(timeout));
            return Boolean.FALSE;
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }
        return false;
    }


    /**
     * 释放锁
     * @param key
     */
    public void unLock(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
            System.out.println("release lock, key :" + key);
        }  catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            jedis.close();
        }
    }


}
