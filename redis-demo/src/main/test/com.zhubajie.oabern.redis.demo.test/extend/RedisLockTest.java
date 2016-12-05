package com.zhubajie.oabern.redis.demo.test.extend;

import com.zhubajie.oabern.redis.demo.extend.lock.BizLock;
import com.zhubajie.oabern.redis.demo.extend.lock.RedisLock;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fengdi
 * Created by fengdi on 2016/11/30.
 */
public class RedisLockTest {
    private static RedisLock redisLock;
    private static DataDao dataDao = new DataDao();
    public final static String BIZ_KEY = "oabern:test:count";

    static {
        Set<String> keySet = new HashSet<String>();
        keySet.add(BIZ_KEY);
        BizLock.bizLockKeySet = keySet;
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        redisLock = RedisLock.getInstance(pool);
    }

    public static void main(String[] args) {
        final int threadNum = 2;
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadNum);

        BizThread bizThread1 = new BizThread(redisLock, dataDao);
        BizThread bizThread2 = new BizThread(redisLock, dataDao);

        executor.execute(bizThread1);
        executor.execute(bizThread2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true) {
            if(executor.getActiveCount() == threadNum) {
                System.out.println("At last, the count value is 【"+ dataDao.getCount() + "】. ThreadPool shutdown!");
                executor.shutdown();
                System.exit(-1);
            }
            try {
                System.out.println("shutdown fail.. there is some thread is in use!");
                System.out.println("current count value : " + dataDao.getCount());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 模拟业务线程
     */
    static class BizThread implements Runnable {
        private RedisLock redisLock;
        private DataDao dao;

        public BizThread(RedisLock redisLock, DataDao dao) {
            this.redisLock = redisLock;
            this.dao = dao;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" is run");
            int i = 0;
            while(i<10) {
                System.out.println(Thread.currentThread().getName()+" is operate..");
                if(redisLock.tryLock(BIZ_KEY)) {
                    dao.increCount();
                    System.out.println(Thread.currentThread().getName() + " : " + dao.getCount());
                    redisLock.unLock(BIZ_KEY);
                    i++;

                }else {

                    System.out.println(Thread.currentThread().getName()+" tryLock fail, wait next call");
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 模拟数据访问层
     */
    static class DataDao {
        private int count = 0;

        public int getCount() {
            return count;
        }

        public void increCount() {
            count++;
        }
    }


}
