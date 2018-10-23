package com.example.netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;


/**
 * Created by wanglei
 * on 2018/9/30
 */
public class NettyServer {

    /**
     * ServerBootStrap
     * 指定线程模型  --  group
     * IO模型  --  channel
     * 连接读写处理逻辑  --  childHandler
     *
     */
    public static void main(String[] args) {
        // boosGroup  监听端口， accept新连接的线程组
        NioEventLoopGroup boosGroup = new NioEventLoopGroup();
        // workGroup  处理每一条连接的数据读写的线程组
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        // 引导我们进行服务端的启动工作
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boosGroup, workGroup)  //给引导类配置两大线程组，这个引导类的线程模型也就定型了
                .channel(NioServerSocketChannel.class) //指定我们服务端的IO模型为NIO， 如果想指定IO模型为BIO，使用OioServerSocketChannel.class
                .childHandler(new ChannelInitializer<NioSocketChannel>() {  // 定义后续每条连接的数据读写，业务处理逻辑
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {

                    }
                });
        //普通绑定端口
//        serverBootstrap.bind(8000);
        //自动绑定递增端口  从1000端口号，向上递增绑定端口
        bind(serverBootstrap, 1000);

        //handler() 方法  服务端启动过程中的一些逻辑 （几乎不用）
//        serverBootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
//            @Override
//            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
//                System.out.println("服务端启动中");
//            }
//        });

        //attr() 方法  可以给服务端的channel，也就是NioServerSocketChannel指定一些自定义属性， 可以通过channel.attr()获取
//        serverBootstrap.attr(AttributeKey.newInstance("serverName"), "nettyServer");

        //childAttr() 方法  可以给每一条连接指定自定义属性， 可以通过channel.attr()获取
//        serverBootstrap.childAttr(AttributeKey.newInstance("clientKey"), "clientValue");

        //childOption() 方法  给每条连接设置一些tcp底层相关属性. ChannelOption.SO_KEEPALIVE 是否开启tcp底层心跳机制，true为开启
//        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        //option() 方法  给服务端channel设置一些属性
        // ChannelOption.SO_BACKLOG 表示系统用于临时存放已完成三次握手的请求的队列的最大长度，如果连接建立频繁，服务器创建新连接较慢，可以适当调大
//        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
    }

    private static void bind(ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("端口["+port+"]绑定成功");
                }else{
                    System.out.println("端口["+port+"]绑定失败");
                    bind(serverBootstrap, port + 1);
                }
            }
        });
    }
}
