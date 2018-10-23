package com.example.netty.protocol.command.request;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

//静态导入
import static com.example.netty.protocol.command.Command.LOGIN_REQUEST;

/**
 * Created by wanglei
 * on 2018/10/23
 */
@Data
public class LoginRequestPacket extends Packet {
    private Integer userId;
    private String userName;
    private String password;

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
