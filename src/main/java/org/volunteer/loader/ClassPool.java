package org.volunteer.loader;/**
 * Description : 
 * Created by YangZH on 16-9-4
 *  下午3:00
 */

import io.netty.channel.ChannelHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-9-4
 * 下午3:00
 */

public class ClassPool {

    public static List<Class<? extends ChannelHandler>> handlers = new ArrayList<>();

    public static void addPlugin(Class<? extends ChannelHandler> handler){
        handlers.add(handler);
    }

    public static Class[] getPlugins(){
        Class<? extends ChannelHandler> []array = new Class[handlers.size()];
        return handlers.toArray(array);
    }
}
