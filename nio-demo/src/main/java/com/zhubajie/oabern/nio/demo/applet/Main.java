package com.zhubajie.oabern.nio.demo.applet;

/**
 * Created by fengdi on 2016/8/5.
 */
public class Main {
    public static void main(String[] args) {
        final String SERVER_HOST = "localhost";
        final int SERVER_PORT = NIOServer.DEFAULT_SERVER_PORT;
        final int CLIENT_NUM = 5;

        //本地建立多个NIOClient
        for(int i=0; i<CLIENT_NUM; ) {
            i++;
            int clientPort = SERVER_PORT + i;
            NIOClient nioClient = new NIOClient(""+i, clientPort, SERVER_HOST, SERVER_PORT);
            Thread clientThread = new Thread(nioClient, "NIO-cli-"+i);
            clientThread.start();
        }
    }
}
