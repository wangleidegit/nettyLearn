package com.example.netty.serialize.impl;


import com.alibaba.fastjson.JSON;
import com.example.netty.serialize.Serializer;
import com.example.netty.serialize.SerializerAlogrithm;


/**
 * Created by wanglei
 * on 2018/12/19
 */
public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlogrithm.JSON;
    }

    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
