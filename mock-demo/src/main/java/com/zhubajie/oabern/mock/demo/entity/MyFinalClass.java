package com.zhubajie.oabern.mock.demo.entity;

/**
 * Created by fengdi on 2016/8/11.
 */
public final class MyFinalClass {

    public int normalMethod(int param) {
        return -param;
    }

    public native int isNativeMethod();

    public MyClass newMyClass(int param) {
        // do something
        return new MyClass(param);
    }

    public MyClass newMyClass() {
        // do something
        return new MyClass();
    }
}
