package com.example.netty.protocol.command.response;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.LOGIN_RESPONSE;

/**
 * Created by wanglei
 * on 2018/10/23
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
