package com.zhubajie.oabern.zookeeper.demo.custom;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.UnsupportedEncodingException;

/**
 * Created by fengdi on 2016/7/26.
 * 自定义的节点Watcher
 */
public class ZNodeWatcher implements Watcher, AsyncCallback.StatCallback {
    private ZkClient zkClient;
    private ExtraWorker worker;
    private final String zNode;

    ZNodeWatcher(String zNode, ExtraWorker worker) {
        this.zNode = zNode;
        this.worker = worker;
    }

    ZNodeWatcher(String zNode) {
        this(zNode, null);
    }

    /**
     * @see org.apache.zookeeper.Watcher#process(WatchedEvent)
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            // We are are being told that the state of the
            // connection has changed
            switch (event.getState()) {
                case SyncConnected:
                    // In this particular example we don't need to do anything
                    // here - watches are automatically re-registered with
                    // server and any watches triggered while the client was
                    // disconnected will be delivered (in order of course)
                    System.out.println("[zNodeWatcher]: zookeeper client is syncconnected with server!");
                    break;

                case Disconnected:
                    System.out.println("[zNodeWatcher]: zookeeper client is disconnected with server!");
                    zkClient.closing();
                    break;

                case Expired:
                    // It's all over
                    System.out.println("[zNodeWatcher]: zookeeper client's session invalid!");
                    zkClient.closing();
                    break;
            }
        } else {
            if (path != null && path.equals(zNode)) {
                // Something has changed on the node, let's find out
                //验证节点是否存在，若存在则添加watcher
                zkClient.zooKeeper.exists(zNode, true, this, null);
            }
        }

        System.out.println("\n");
    }

    /**
     * @see org.apache.zookeeper.AsyncCallback.StatCallback#processResult(int, String, Object, Stat)
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case KeeperException.Code.Ok:
                exists = true;
                break;
            case KeeperException.Code.NoNode:
                exists = false;
                break;
            case KeeperException.Code.SessionExpired:
            case KeeperException.Code.NoAuth:
                zkClient.closing();
                return;
            default:
                // Retry errors
                zkClient.zooKeeper.exists(zNode, true, this, null);
                return;
        }

        byte data[];
        if (exists) {
            try {
                data = zkClient.zooKeeper.getData(zNode, false, null);
                System.out.println("[zNodeWatcher]:  the data in zNode named "+ zNode + "has changed! ");
                /**
                 * 执行额外的工作
                 */
                if(worker != null) {
                    System.out.println("[zNodeWatcher]:  begin to do the extra work! ");
                    worker.doExtraWork(data);
                }
                System.out.println("\n");
            } catch (KeeperException e) {
                // We don't need to worry about recovering now. The watch
                // callbacks will kick off any exception handling
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public ZkClient getZkClient() {
        return zkClient;
    }

    /**
     * 设置ZkClient 并且 为zNode设置Watcher
     * @param zkClient
     */
    public void setZkClientAndSetWatcher(ZkClient zkClient) {
        this.zkClient = zkClient;
        zkClient.zooKeeper.exists(zNode, true, this, null);
    }

    public String getZNode() {
        return zNode;
    }
}
