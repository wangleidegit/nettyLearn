package com.example.netty.server.handler;

import com.example.netty.utils.LoginUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by wanglei
 * on 2018/12/20
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!LoginUtil.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            ctx.pipeline().remove(this);
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(LoginUtil.hasLogin(ctx.channel())){
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else{
            System.out.println("无登录验证，强制关闭连接!");
        }
    }


}
