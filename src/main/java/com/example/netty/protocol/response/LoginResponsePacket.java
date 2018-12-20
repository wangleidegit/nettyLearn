package com.example.netty.protocol.response;

import com.example.netty.protocol.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.LOGIN_RESPONSE;

/**
 * Created by wanglei
 * on 2018/12/19
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
