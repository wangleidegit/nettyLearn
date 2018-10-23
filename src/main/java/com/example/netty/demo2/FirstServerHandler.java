package com.example.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * Created by wanglei
 * on 2018/10/8
 */
public class FirstServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 服务端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));

        //回复数据到客服端
        System.out.println(new Date() + ": 服务端写出数据");
        ByteBuf byteBuf1 = getByteBuf(ctx, "你好，我是服务器");
        ctx.channel().writeAndFlush(byteBuf1);

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println(new Date() + ": 服务端对新接入的客户端写出数据");
        ByteBuf byteBuf = getByteBuf(ctx, "你好，客户端");
        ctx.channel().writeAndFlush(byteBuf);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx, String content){
        //获取二进制抽象 ByteBuf
        ByteBuf buffer = ctx.alloc().buffer();

        //准备数据，指定字符串的字符集为 utf-8
        byte[] bytes = content.getBytes(Charset.forName("utf-8"));

        //填充数据到 ByteBuf
        buffer.writeBytes(bytes);

        return buffer;
    }
}
