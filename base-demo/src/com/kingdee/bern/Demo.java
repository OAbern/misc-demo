package com.kingdee.bern;

/**
 * Created by oabern on 2017/7/2.
 */
public class Demo {

    public static void main(String[] args) {
        System.out.println("main");

        Son son1 = new Son(1);

    }

    static class Father {
        public static String str;

        static {
            System.out.println("f - static init - a1");
        }

        {
            System.out.println("f -  init - b1");
        }

        public Father() {
            System.out.println("f init - d1");
        }

        public Father(int i) {
            System.out.println("f init - d2");
        }
    }

    static class Son extends Father {

        static {
            System.out.println("son - static init - a2");
        }

        {
            System.out.println("son -  init - b2");
        }

        public Son() {

            System.out.println("son - init - c1");
        }

        public Son(int i) {
            System.out.println("son- init - c2");
        }
    }
}
