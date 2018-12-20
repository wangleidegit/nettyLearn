package com.example.netty.protocol.request;

import com.example.netty.protocol.Packet;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.example.netty.protocol.command.Command.MESSAGE_REQUEST;

/**
 * Created by wanglei
 * on 2018/12/19
 */
@Data
@NoArgsConstructor
public class MessageRequestPacket extends Packet {

    private String message;

    public MessageRequestPacket(String message) {
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
