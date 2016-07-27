package com.zhubajie.oabern.zookeeper.demo.custom;

import java.io.UnsupportedEncodingException;

/**
 * Created by fengdi on 2016/7/27.
 * 输出Znode节点数据的工作器
 */
public class SoutWorkerImpl implements ExtraWorker {

    @Override
    public void doExtraWork(byte[] data) {
        String dataStr = null;
        if(data != null) {
            try {
                dataStr = new String(data, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[SoutWorkerImpl] : get data from 'zNode', the value is '" + dataStr + "'");
    }
}
