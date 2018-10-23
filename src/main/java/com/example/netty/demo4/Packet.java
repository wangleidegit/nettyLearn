package com.example.netty.demo4;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by wanglei
 * on 2018/10/11
 * java对象的抽象类
 */
@Data
public abstract class Packet {
    /**
     * 协议版本
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 协议指令
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
