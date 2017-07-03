package com.kingdee.bern;

/**
 * Created by oabern on 2017/7/2.
 */
public class Demo2 {

    public static void main(String[] args) {
        A a = new A();
//        a.a();
        a.e();
    }

    static class A {
        public static int count;

        public void a() {
            System.out.println("a1");
            b();
            System.out.println("a2");
        }

        public void b() {
            System.out.println("b1");
            c();
            System.out.println("b2");
        }

        public void c() {
            System.out.println("c1");
            d();
        }

        public void d() {
            System.out.println("d");
        }

        public void e(){
//            System.out.println("e");
            System.out.println(count);
            if (count++ >=  10000) {
                System.out.println("end");
                return;
            }

            e();

//            System.out.println("s");
        }


    }
}
