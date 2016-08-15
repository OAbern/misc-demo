package com.kingdee.bern.Thread;

import java.io.Serializable;

/**
 * synchronized on a non-final object, then change the object reference while synchronized-on
 *
 * Created by fengdi on 2016/8/15.
 */
public class TestThread implements Runnable , Serializable {

    private static long serialVersionUID = 3L;

    private Object obj=new Object();
    private int a;
    private int b;

    public static void main(String[] args) {

        TestThread test = new TestThread();
        for(int i=0; i<100; i++) {
            new Thread(test).start();
        }

    }


    public void run() {
        synchronized(obj)
        {
            System.out.println("1 Thread:" + Thread.currentThread().getId()+"；[a:"+ (a++) +"]");
//            System.out.println("[a:"+ (a++) +"]");

            obj = new Object();     //change obj

            //sout b value
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("2 after sleep, Thread:" +Thread.currentThread().getId()+"；[b:"+ b +"]");

            // then b++;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            b++;
        }

    }


}
