package com.example.netty.utils;


import com.example.netty.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;


/**
 * Created by wanglei
 * on 2018/12/20
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> attr = channel.attr(Attributes.LOGIN);

        return attr.get() != null;
    }
}
