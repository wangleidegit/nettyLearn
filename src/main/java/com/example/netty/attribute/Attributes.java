package com.example.netty.attribute;


import io.netty.util.AttributeKey;

/**
 * Created by wanglei
 * on 2018/12/20
 */
public interface Attributes {
    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
