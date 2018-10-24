package com.example.netty.client;

import com.example.netty.protocol.command.Packet;
import com.example.netty.protocol.command.PacketCodec;
import com.example.netty.protocol.command.request.LoginRequestPacket;
import com.example.netty.protocol.command.response.LoginResponsePacket;
import com.example.netty.protocol.command.response.MessageResponsePacket;
import com.example.netty.util.LoginUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Created by wanglei
 * on 2018/10/23
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {

    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("client start ........");

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(1);
        loginRequestPacket.setUserName("wangl");
        loginRequestPacket.setPassword("123456");

        ByteBuf requestByteBuf = PacketCodec.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
        ctx.channel().writeAndFlush(requestByteBuf);
    }

    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);
        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess()){
                LoginUtil.markAsLogin(ctx.channel());
                System.out.println(new Date() + "：客户端登陆成功");
            } else {
                System.out.println(new Date() + ": 客户端登陆失败, 原因：" + loginResponsePacket.getReason());
            }
        } else if (packet instanceof MessageResponsePacket) {
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());
        }
    }
}
