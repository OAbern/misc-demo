package com.github.oabern.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 简单的服务器
 * @author sola
 *
 */
public class SimpleServer {
    public static void main(String[] args) {
    }
    
    public void run(String port) {
        EventLoopGroup boss = new NioEventLoopGroup();      // parentGroup acceptor 用于接受请求     
        EventLoopGroup works = new NioEventLoopGroup();     // childGroup handler 用于处理acceptor接收到的请求        

        ServerBootstrap b = new ServerBootstrap();      //新建一个服务器启动类
        //设置基本的参数
        b.group(boss, works)        //配置acceptor，handler
         .channel(NioServerSocketChannel.class)     //设置parentChannel的类型，acceptor使用的管道。
         .childHandler(new ChannelInitializer<SocketChannel>() {        //添加处理器
             protected void initChannel(SocketChannel ch) throws Exception {        //设置childChannel类型
                 ch.pipeline().addLast(new SimpleServerHandler());
             };
         })
         .option(ChannelOption.SO_BACKLOG, 128)     //设置parentChannel的参数
         .childOption(ChannelOption.SO_KEEPALIVE, true);        //设置childChannel的参数
        
        try {
            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(port, 0).sync();
            
            // 等待服务器  socket 关闭 。
            // 在这个例子中，这不会发生，但你可以优雅地关闭你的服务器。
            f.channel().closeFuture().sync();
        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        

    }
}
