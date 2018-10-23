package com.example.netty.serialize;

import com.example.netty.protocol.command.Packet;

/**
 * Created by wanglei
 * on 2018/10/23
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();
    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
