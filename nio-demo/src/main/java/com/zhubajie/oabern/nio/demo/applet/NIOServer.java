package com.zhubajie.oabern.nio.demo.applet;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务端
 * Created by fengdi on 2016/8/3.
 */
public class NIOServer implements Runnable, Closeable {
    public static final int DEFAULT_SERVER_PORT = 6659;
//    public static final int DEFAULT_SERVER_RW_PORT = 6658;
    private final int bindLocalPort;
    private ServerSocketChannel serverSocketChannel;
//    private SocketChannel socketChannel;
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

//            socketChannel = SocketChannel.open();
//            socketChannel.bind(new InetSocketAddress());

            //初始化selector、byteBuffer
            selector = Selector.open();
            byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

            //配置通道为非阻塞(必须) 并且将通道注册到selector，监听注册事件
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("[** NIOServer **]: Init NIOServer success! The serverSocketChannel has opened in port "+ bindLocalPort +", registered with a seletcor and interested on SelectionKey.OP_ACCEPT!");
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

                }else {     //当前Server有事件处理

                    handleSelecedKeys();
                }

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                System.out.println("[** NIOServer **]: ####IOException!");
//                close();
                e.printStackTrace();
            }
        }
    }

    private void handleSelecedKeys() throws IOException {
        SocketChannel socketChannelForClient;

        Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
        while(iter.hasNext()) {
            SelectionKey selectionKey = iter.next();

            //判断事件类型
            if(selectionKey.isAcceptable()) {   //请求连接事件
                System.out.println("[** NIOServer **]: Handle a acceptable event!");
                socketChannelForClient = serverSocketChannel.accept();
                socketChannelForClient.configureBlocking(false);

                String responseMsg = "[$#$ From NIOServer $#$]: You has connected to server on " + bindLocalPort;
                byteBuffer.put(responseMsg.getBytes());
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    socketChannelForClient.write(byteBuffer);
                }
                byteBuffer.clear();
                socketChannelForClient.register(selector, SelectionKey.OP_READ);

            }else if(selectionKey.isConnectable()) {       //连接事件
                System.out.println("[** NIOServer **]: Handle a connectable event!");

            }else if(selectionKey.isReadable()) {       //读事件
                System.out.println("[** NIOServer **]: Handle a readable event!");

                byte[] msg = readMessage(selectionKey);
                int size = 0;
                for(int i=0; i<msg.length; i++) {
                    if(msg[i] == 0) {
                        size = i;
                        break;
                    }else if(i == msg.length-1) {
                        size = msg.length;
                    }
                }

                System.out.println("[** NIOServer **]: Got a message: \"" + new String(msg) +"\" \n");

                sendMessage(selectionKey, "[$$ From NIOServer $$]: Got data size:" + size + "b !");

            }else if(selectionKey.isWritable()) {       //写事件
                System.out.println("[** NIOServer **]: Handle a writable event!");

            }

            //移除已经处理过的事件, 避免重复处理
            iter.remove();

        }
    }

    /**
     * 读取信息
     * @param key
     * @return
     * @throws IOException
     */
    public byte[] readMessage(SelectionKey key) throws IOException {
        SocketChannel socketChannelForClient = (SocketChannel) key.channel();
        ByteBuffer byteBufferTemp = ByteBuffer.allocate(BUFFER_SIZE);
        socketChannelForClient.read(byteBufferTemp);
        byteBufferTemp.flip();
        return byteBufferTemp.array();
    }

    /**
     * 发送信息
     * @param key
     * @param msg
     * @throws IOException
     */
    public void sendMessage(SelectionKey key, String msg) throws IOException {
        SocketChannel socketChannelForClient = (SocketChannel) key.channel();
        ByteBuffer byteBufferTemp = ByteBuffer.allocate(BUFFER_SIZE);
        byteBufferTemp.put(msg.getBytes());
        byteBufferTemp.flip();
        while(byteBufferTemp.hasRemaining()) {
            socketChannelForClient.write(byteBufferTemp);
        }
    }

    @Override
    public void close() {
        try {
            if(serverSocketChannel != null) {
                serverSocketChannel.close();
            }

            if(selector != null) {
                selector.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        NIOServer nioServer = new NIOServer(DEFAULT_SERVER_PORT);
        Thread serverThread = new Thread(nioServer, "server");
        serverThread.start();
    }
}
