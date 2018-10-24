package com.example.netty.protocol.command.request;

import com.example.netty.protocol.command.Packet;
import lombok.Data;

import static com.example.netty.protocol.command.Command.MESSAGE_REQUEST;

/**
 * Created by wanglei
 * on 2018/10/24
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
