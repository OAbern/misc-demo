package com.kingdee.bern.Thread;

/**
 * multi-thread concurrently access a class instance synchronized area and nonsynchronized area
 * Created by oabern on 2017/6/29.
 */
public class MultiThreadDemo {

    public static void main(String[] args) {
        A a = new A(0, new Object());

        //combine the same object instance named a
        ConsumerA consumerA1 = new ConsumerA(a);
        ConsumerA consumerA2 = new ConsumerA(a);
        ConsumerB consumerB = new ConsumerB(a);


        Thread t1 = new Thread(consumerA1);
        Thread t2 = new Thread(consumerA2);
        Thread t3 = new Thread(consumerB);
        t1.start();
        t2.start();
        t3.start();

    }

    /**
     * cousumerA increase count by one
     */
    static class ConsumerA implements Runnable {
        A objectA;

        public ConsumerA(A a) {
            objectA = a;
        }

        public void run() {
            int i =0;
            while(i<10) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                objectA.increase();
                i++;
            }
        }
    }

    /**
     * cousumerA increase count by two
     */
    static class ConsumerB implements Runnable {
        A objectA;

        public ConsumerB(A a) {
            objectA = a;
        }

        public void run() {
            int i =0;
            while(i<30) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                objectA.increaseByTwo();
                i++;
            }
        }
    }

    static class A {
        int count;
        Object object;

        public A(int count, Object object) {
            this.count = count;
            this.object = object;
        }

        public synchronized void increase() {
            System.out.println("in synchronized area...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
            System.out.println(Thread.currentThread().getId() + ": do something in synchronized area... the count value is " + count);
        }

        public void increaseByTwo() {
            count = count + 2;
            System.out.println(Thread.currentThread().getId() + ": I can set the value, the count value is " + count);
        }
    }
}
