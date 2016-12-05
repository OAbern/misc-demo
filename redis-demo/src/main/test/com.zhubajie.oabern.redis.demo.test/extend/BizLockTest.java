package com.zhubajie.oabern.redis.demo.test.extend;

import com.zhubajie.oabern.redis.demo.extend.lock.BizLock;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author fengdi
 * Created by fengdi on 2016/11/30.
 */
public class BizLockTest {
    private static BizLock bizLock;
    private static DataDao dataDao = new DataDao();
    public final static String BIZ_KEY = "oabern:test:count";

    static {
        Set<String> keySet = new HashSet<String>();
        keySet.add(BIZ_KEY);
        BizLock.bizLockKeySet = keySet;
        bizLock = BizLock.getSingleInstance();
    }

    public static void main(String[] args) {
        final int threadNum = 2;
        ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(threadNum);

        BizThread bizThread1 = new BizThread(bizLock, dataDao);
        BizThread bizThread2 = new BizThread(bizLock, dataDao);

        executor.execute(bizThread1);
        executor.submit(bizThread2);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        while(true) {
////            service.
//            if(executor.getActiveCount() == threadNum) {
//                System.out.println("At last, the count value is 【"+ dataDao.getCount() + "】. ThreadPool shutdown!");
//                executor.shutdown();
//                System.exit(-1);
//            }
//            try {
//                System.out.println("shutdown fail.. there is some thread is in use!");
//                System.out.println("current count value : " + dataDao.getCount());
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 模拟业务线程
     */
    static class BizThread implements Runnable {
        private BizLock bizLock;
        private DataDao dao;

        public BizThread(BizLock bizLock, DataDao dao) {
            this.bizLock = bizLock;
            this.dao = dao;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()+" is run");
            int i = 0;
            while(i<10) {
                System.out.println(Thread.currentThread().getName()+" is operate..");
                if(bizLock.tryLock(BIZ_KEY)) {
                    dao.increCount();
                    System.out.println(Thread.currentThread().getName() + " : " + dao.getCount());
                    bizLock.release(BIZ_KEY);
                    i++;

                }else {

                    System.out.println(Thread.currentThread().getName()+" tryLock fail, wait next call");
                }

                try {
                    Thread.sleep(1000);
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
