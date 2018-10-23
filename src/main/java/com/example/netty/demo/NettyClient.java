package com.example.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanglei
 * on 2018/9/30
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        //启动客户端以及连接服务端  客户端启动引导类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                //1.指定线程类型
                .group(workGroup)
                //2.指定IO模型为NIO    BOI的IO模型为OioSocketChannel
                .channel(NioSocketChannel.class)
                //3.IO处理逻辑  定义连接的业务处理逻辑
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {

                    }
                });
        //4.建立连接  地址+端口 监听是否成功  普通做法  connect方法是异步的  异步回调机制
//        bootstrap.connect("juejin.im", 80).addListener(future -> {
//           if (future.isSuccess()){
//               System.out.println("连接成功");
//           }else{
//               System.out.println("连接失败");
//           }
//        });

        //4.建立连接  失败重连
        connect(bootstrap, "juejin.im", 80, MAX_RETRY);

        //attr() 方法 给客户端channel，也就是NioSocketChannel绑定自定义属性   通过channel.attr()获取
//        bootstrap.attr(AttributeKey.newInstance("clientName"), "nettyClient");

        //option() 方法  给连接设置tcp底层相关属性
        // ChannelOption.CONNECT_TIMEOUT_MILLIS 表示连接的超时时间，超过这个时间还是建立不上的话则代表连接失败
//        bootstrap
//                .option(ChannelOption.SO_KEEPALIVE, true)
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()){
                System.out.println("连接成功");
            }else if(retry == 0){
                System.out.println("重试次数已用完，放弃连接");
            }else{
                //如果连接失败但是重试次数仍然没有用完，则计算下一次重连间隔 delay，然后定期重连
                System.out.println("连接失败");
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                //本次重连间隔
                int delay = 1 << order;  //  << [左移运算符]
                System.out.println(new Date() + "连接失败，第" +order+ "次重连");
                // workGroup.schedule()  定时任务逻辑
                bootstrap.config().group().schedule(
                        () -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS
                );
            }
        });
    }
}
