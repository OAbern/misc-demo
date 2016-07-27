package com.zhubajie.oabern.zookeeper.demo.custom;

/**
 * Created by fengdi on 2016/7/26.
 * Zookeeper 客户端运行入口
 */
public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("******* You must input two parameters in order : hostPort, zNode *******");
            System.exit(2);
        }
        String hostPort = args[0];
        String zNode = args[1];
        ExtraWorker soutWorker = new SoutWorkerImpl();
        ZNodeWatcher zNodeWatcher = new ZNodeWatcher(zNode, soutWorker);
        try {
            new ZkClient(hostPort, zNodeWatcher).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
