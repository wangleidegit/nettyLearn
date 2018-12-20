package com.example.netty.serialize;

import com.example.netty.serialize.impl.JSONSerializer;

/**
 * Created by wanglei
 * on 2018/12/19
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java 对象转换成二进制
     * @param o
     * @return
     */
    byte[] serialize(Object o);

    /**
     * 二进制转换成 java 对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
