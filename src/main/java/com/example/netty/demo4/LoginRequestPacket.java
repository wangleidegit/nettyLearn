package com.example.netty.demo4;

import lombok.Data;

import static com.example.netty.demo4.Command.LOGIN_REQUEST;

/**
 * Created by wanglei
 * on 2018/10/11
 * 客户端登陆
 */
@Data
public class LoginRequestPacket extends Packet{

    private Integer userId;

    private String userName;

    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
