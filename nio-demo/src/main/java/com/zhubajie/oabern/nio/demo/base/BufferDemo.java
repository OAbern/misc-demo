package com.zhubajie.oabern.nio.demo.base;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ByteChannel;
import java.nio.charset.Charset;

/**
 * 练习Channel的基本操作
 * Created by fengdi on 2016/8/3.
 */
public class BufferDemo {
    private static final String FILE_PATH = "D:/ChannelTest.txt";
    private static final File FILE = new File(FILE_PATH);
    private static final String SYSTEM_ENCODING;

    static {
        SYSTEM_ENCODING = System.getProperty("file.encoding");
        System.out.println("The default encoding of current system is " + SYSTEM_ENCODING);
    }

    @BeforeClass
    public static void beforeClass() throws IOException {
        if(!FILE.exists()) {
            FILE.createNewFile();
            String testWord = "Hello Tomorrow! \n 你好，明天！";
            try(RandomAccessFile raf = new RandomAccessFile(FILE, "rw")) {
                raf.write(testWord.getBytes());
            }
            System.out.println("A new file is created!");
        }
        System.out.println("Data Init Success!\n");
    }

    @Test
    @Ignore
    public void testByteBuffer() throws IOException {
        final int capacity = 8;
        RandomAccessFile file = new RandomAccessFile(FILE, "r");

        ByteChannel byteChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        int i = 0;
        int end = byteChannel.read(byteBuffer);
        while(end > 0) {
            byteBuffer.flip();
            while(byteBuffer.hasRemaining()) {
                byte b = byteBuffer.get();
                i++;
                System.out.print((char)b);
            }
            byteBuffer.clear();
            end = byteChannel.read(byteBuffer);
        }
        System.out.println(i);
    }

    @Test
    public void testCharChannel() throws IOException {
        final int capacity = 64;
        RandomAccessFile file = new RandomAccessFile(FILE, "r");

        ByteChannel byteChannel = file.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
        int i = 0;
        int end = byteChannel.read(byteBuffer);
        while(end > 0) {
            byteBuffer.flip();
            CharBuffer charBuffer = Charset.forName(SYSTEM_ENCODING).decode(byteBuffer);
            System.out.print(charBuffer.array());
            i++;

            byteBuffer.clear();
            end = byteChannel.read(byteBuffer);
        }

        System.out.println("\n"+i);
    }
}
