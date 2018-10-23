package com.example.netty.demo4;

/**
 * Created by wanglei
 * on 2018/10/11
 */
public interface Serializer {

    byte JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     * @return
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换成二进制
     * @return
     */
    byte[] serializer(Object o);

    /**
     * 二进制转换成java对象
     * @param clazz
     * @param bytes
     * @param <T>
     * @return
     */
    <T> T deserializer(Class<T> clazz, byte[] bytes);

}
