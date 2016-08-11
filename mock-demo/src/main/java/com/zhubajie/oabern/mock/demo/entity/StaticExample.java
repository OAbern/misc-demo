package com.zhubajie.oabern.mock.demo.entity;

/**
 * Created by fengdi on 2016/8/10.
 */
public class StaticExample {

    public StaticExample() {
        //do somthing
    }

    public static int firstStaticMethod(int returnParam) {
        // do something
        return  returnParam;
    }

    public static int secondStaticMethod(int returnParam) {
        // do something
        return  -returnParam;
    }

    private static int isPrivteStaticMethod(int param) {
        // do something
        System.out.println("i am a private static method");
        return param * 2;
    }

    public static void isVoidStaticMethod() {
        // do something
    }

    private int isPrivateMethod(int param) {
        // do something
//        System.out.println("i am a private method");
        return param % 2;
    }

    public int callPrivateMethod(int param) {
        // do something
//        System.out.println("i will call private method");
        return isPrivateMethod(param);
    }
}
