package com.example.netty.protocol.command;

import com.example.netty.protocol.command.request.LoginRequestPacket;
import com.example.netty.protocol.command.request.MessageRequestPacket;
import com.example.netty.protocol.command.response.LoginResponsePacket;
import com.example.netty.protocol.command.response.MessageResponsePacket;
import com.example.netty.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.example.netty.protocol.command.Command.*;

/**
 * Created by wanglei
 * on 2018/10/23
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    private static final int MAGIC_NUMBER = 0x12345678;
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serialzerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serialzerMap = new HashMap<>();
        Serializer serializer = Serializer.DEFAULT;
        serialzerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet){
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();

        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        byteBuf.skipBytes(4);
        byteBuf.skipBytes(1);

        byte serializerAlgorithm = byteBuf.readByte();
        byte command = byteBuf.readByte();
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> packetType = getPacketType(command);
        Serializer serializer = getSerializer(serializerAlgorithm);

        if(packetType != null && serializer != null) {
            return serializer.deserialize(packetType, bytes);
        }
        return null;
    }

    private Class<? extends Packet> getPacketType(byte command){
        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(byte serializerAlgorithm){
        return serialzerMap.get(serializerAlgorithm);
    }
}
