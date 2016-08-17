package com.zhubajie.oabern.nio.demo.applet;

/**
 * Created by fengdi on 2016/8/8.
 */
public interface Closeable {
    /**
     * 所有声明的资源，应该在此方法中安全关闭
     */
    void close();
}
