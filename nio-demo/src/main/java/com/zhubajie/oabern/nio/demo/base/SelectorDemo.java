package com.zhubajie.oabern.nio.demo.base;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;

/**
 * 练习Selector的基本操作
 * Created by fengdi on 2016/8/4.
 */
public class SelectorDemo {

    @Test
    public void test() throws IOException, InterruptedException {
        try(Selector selector = Selector.open()) {

            try(SocketChannel socketChannel = SocketChannel.open()) {
                socketChannel.bind(new InetSocketAddress(8080));
                socketChannel.connect(new InetSocketAddress(8081));
                socketChannel.configureBlocking(false);
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);

                System.out.println(selectionKey);

                String word = "Hello tomorrow!";
                ByteBuffer byteBuffer = ByteBuffer.allocate(128);
                byteBuffer.put(word.getBytes());

                socketChannel.read(byteBuffer);
            }
            while(true) {
                int key = selector.select();
                if(key == SelectionKey.OP_READ) {
                    System.out.println("[selector]:Listen a read event!");
                }
                Thread.sleep(1000);
            }

        }
    }


}
