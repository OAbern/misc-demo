package com.zhubajie.oabern.zookeeper.demo.custom;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by fengdi on 2016/7/26.
 * 客户端连接的执行器
 */
public class ZkClient implements Runnable {
    public final ZooKeeper zooKeeper;
    private final String hostPort;
    private boolean alive = true;

    public ZkClient(String hostPort, ZNodeWatcher zNodeWatcher) throws IOException {
        this.hostPort = hostPort;
        zooKeeper = new ZooKeeper(hostPort, 30000, zNodeWatcher);
        zNodeWatcher.setZkClientAndSetWatcher(this);
    }

    public ZkClient(String hostPort) throws IOException {
        this.hostPort = hostPort;
        zooKeeper = new ZooKeeper(hostPort, 30000, null);
    }

    /**
     * 获取相应节点的数据（字符串形式）
     * @param zNode 将要获取的节点路径
     * @param charsetName 将要转换成的字符串编码
     * @return 字符串形式的节点数据
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getDataStr(String zNode, String charsetName) throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData(zNode, false, null);
        String dataStr = null;
        if(data != null) {
            try {
                dataStr = new String(data, charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return dataStr;
    }


    @Override
    public void run() {
        synchronized (this) {
            while(alive) {
                try {
                    System.out.println("[ZkClient]:  zkClient zookeeper's state is "+zooKeeper.getState()+"!");
                    System.out.println("[ZkClient]:  alive = "+ alive + "; Thread is going to wait!");
                    wait();
                } catch (InterruptedException e) {
                    System.out.println("[ZkClient]:  Offline?! zkClient zookeeper's state is "+zooKeeper.getState()+"!");
                    System.out.println("[ZkClient]:  alive = "+ alive + ";");
                    e.printStackTrace();
                }
            }
        }
    }

    public void closing() {
        synchronized (this) {
            alive = false;
            notifyAll();
        }
    }
}
