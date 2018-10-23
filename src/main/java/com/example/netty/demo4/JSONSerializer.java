package com.example.netty.demo4;


import com.alibaba.fastjson.JSON;

/**
 * Created by wanglei
 * on 2018/10/11
 */
public class JSONSerializer implements Serializer{
    @Override
    public byte getSerializerAlgorithm() {
        return JSONSerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serializer(Object o) {
        return JSON.toJSONBytes(o);
    }

    @Override
    public <T> T deserializer(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
