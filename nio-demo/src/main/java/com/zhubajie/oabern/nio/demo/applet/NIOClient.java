package com.zhubajie.oabern.nio.demo.applet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by fengdi on 2016/8/3.
 */
public class NIOClient implements Runnable {
    private SocketChannel socketChannel;
    private Selector selector;
    private InetSocketAddress localSocketAddress;
    private InetSocketAddress remoteSocketAddress;
    private final String clientName;
    private boolean alive = true;
    private ByteBuffer byteBuffer;
    private final int BUFFER_SIZE = 1 << 10;

    NIOClient(String clientName, int localPort, String remoteHost, int remotePort) {
        this.clientName = clientName;
        localSocketAddress = new InetSocketAddress(localPort);
        remoteSocketAddress = new InetSocketAddress(remoteHost, remotePort);

        try {
            //初始化socketchannel 并绑定本地端口
            socketChannel = SocketChannel.open();
            socketChannel.bind(localSocketAddress);

            //初始化selector 注册感兴趣的事件
            selector = Selector.open();
            int interestSet = SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, interestSet);

            byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        } catch (IOException e) {
            System.out.println("[NIOClient"+clientName+"]: Init NIOClient failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(alive) {
            //判断是否已经建立连接
            if(socketChannel.isConnected()) {   //已经建立连接
                System.out.println("[NIOClient"+clientName+"]: Connected to "+ remoteSocketAddress.toString()+" success! Ready to sleep 10s");
            }else {
                connect();
            }
            try {
                if(selector.select() == 0) {
                    System.out.println("[NIOClient"+clientName+"]: Nothing in selector!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    handleSelectedKeys();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        //线程结束 关闭资源
        try {
            socketChannel.close();
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleSelectedKeys() {
        final Iterator<SelectionKey> keysIter = selector.selectedKeys().iterator();
        while(keysIter.hasNext()) {
            SelectionKey key = keysIter.next();
            if(key.isConnectable()) {
                System.out.println("[NIOClient"+clientName+"]: Got a connect event!");

            }else if(key.isWritable()) {
                System.out.println("[NIOClient"+clientName+"]: Nothing in selector!");

            }else if(key.isReadable()) {
                System.out.println("[NIOClient"+clientName+"]: Nothing in selector!");

            }
        }
    }

    public void sendMessage(String message) throws IOException {
        byteBuffer.put(message.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }

    public void connect() {
        try {
            System.out.println("[NIOClient"+clientName+"]: Ready to connect to "+ remoteSocketAddress.toString());
            socketChannel.connect(remoteSocketAddress);
        } catch (IOException e) {
            System.out.println("[NIOClient"+clientName+"]: Connected to "+ remoteSocketAddress.toString()+" failed! It will retry after 5s!");
            e.printStackTrace();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final String serverHost = "localhost";
        final int serverPort = 6659;

        NIOClient nioClient1 = new NIOClient("0", 6660, serverHost, serverPort);
        Thread clientThread1 = new Thread(nioClient1, "cli-0");
        clientThread1.start();
    }
}
