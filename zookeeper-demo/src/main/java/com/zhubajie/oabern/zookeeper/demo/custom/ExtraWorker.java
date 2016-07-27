package com.zhubajie.oabern.zookeeper.demo.custom;

/**
 * Created by fengdi on 2016/7/27.
 * 额外的工作器
 */
public interface ExtraWorker {

    /**
     * 执行额外的工作
     * @param data 节点数据
     */
    void doExtraWork(byte data[]);
}
