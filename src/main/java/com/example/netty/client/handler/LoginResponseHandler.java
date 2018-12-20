package com.example.netty.client.handler;

import com.example.netty.protocol.request.LoginRequestPacket;
import com.example.netty.protocol.response.LoginResponsePacket;
import com.example.netty.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * Created by wanglei
 * on 2018/12/20
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //创建登陆对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setUsername("wanglei");
        loginRequestPacket.setPassword("123456");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
        if(loginResponsePacket.isSuccess()){
            System.out.println(new Date() + ": 客户端登录成功");
            LoginUtil.markAsLogin(channelHandlerContext.channel());
        }else{
            System.out.println(new Date() + ": 客户端登录失败，原因：" + loginResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("客户端连接被关闭!");
    }
}
