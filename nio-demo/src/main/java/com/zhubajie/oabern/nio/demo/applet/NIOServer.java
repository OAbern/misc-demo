package com.zhubajie.oabern.nio.demo.applet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO服务端
 * Created by fengdi on 2016/8/3.
 */
public class NIOServer implements Runnable {
    public static final int DEFAULT_SERVER_PORT = 6659;
    private final int bindLocalPort;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer byteBuffer;
    private final int BUFFER_SIZE = 1 << 10;
    private boolean INIT_FLAG;

    NIOServer(int bindLocalPort) {
        this.bindLocalPort = bindLocalPort;
        try {
            //初始化serverSocketChannel 并且绑定本地端口
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(bindLocalPort));

            //初始化selector、byteBuffer
            selector = Selector.open();
            byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            //配置通道为非阻塞(必须) 并且将通道注册到selector，监听注册事件
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("[** NIOServer **]: Init NIOServer success! The serverSocketChannel has opened in port "+bindLocalPort+", registered with a seletcor and interested on SelectionKey.OP_ACCEPT!");
            INIT_FLAG = true;
        } catch(IOException e) {
            INIT_FLAG = false;
            System.out.println("[** NIOServer **]: Init NIOServer failed!");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(INIT_FLAG) {
            try {
                if(selector.select(1000) == 0) {       //当前Server没有任何事件
                    System.out.println("[** NIOServer **]: Nothing in selector!");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {     //当前Server有事件处理

                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while(iter.hasNext()) {
                        SelectionKey selectionKey = iter.next();

                        //判断事件类型
                        if(selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            System.out.println(socketChannel.getLocalAddress().toString());
                            System.out.println(socketChannel.getRemoteAddress().toString());
                            System.out.println("[** NIOServer **]: Handle a acceptable event!");

                        }else if(selectionKey.isConnectable()) {
                            System.out.println("[** NIOServer **]: Handle a connectable event!");

                        }else if(selectionKey.isReadable()) {
                            System.out.println("[** NIOServer **]: Handle a readable event!");

                        }else if(selectionKey.isWritable()) {
                            System.out.println("[** NIOServer **]: Handle a writable event!");
                        }
                        //移除已经处理过的事件
                        try {
                            iter.remove();
                        }catch(UnsupportedOperationException e) {
                            System.out.println("[** NIOServer **]: ####UnsupportedOperationException!");
                        }
                    }
                }


            } catch (IOException e) {
                System.out.println("[** NIOServer **]: ####IOException!");
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer(DEFAULT_SERVER_PORT);
        Thread serverThread = new Thread(nioServer, "server");
        serverThread.start();
    }

}
