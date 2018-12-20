package com.example.netty.client;

import com.example.netty.client.handler.LoginResponseHandler;
import com.example.netty.client.handler.MessageResponseHandler;
import com.example.netty.codec.PacketDecoder;
import com.example.netty.codec.PacketEncoder;
import com.example.netty.codec.Spliter;
import com.example.netty.protocol.request.MessageRequestPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by wanglei
 * on 2018/12/19
 */
public class NettyClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final int MAX_RETRY = 5;

    public static void main(String[] args) {
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new Spliter());
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry){
        bootstrap.connect(HOST, PORT).addListener(future -> {
            if(future.isSuccess()){
                System.out.println(new Date() + ": 连接成功，启动控制台线程······");
                Channel channel = ((ChannelFuture) future).channel();
                new Thread(()->{
                    while (!Thread.interrupted()){
                        System.out.println("输入消息发送至服务器：");
                        Scanner scanner = new Scanner(System.in);
                        String next = scanner.next();

                        channel.writeAndFlush(new MessageRequestPacket(next));
                    }
                }).start();
            } else if (retry == 0){
                System.out.println("重试次数已用完，放弃连接！");
            } else {
                //第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次连接的间隔
                int delay = 1 << order;
                System.out.println(new Date() + "：连接失败，第" + order + "次重连······");
                bootstrap.config().group().schedule(()-> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }
}
