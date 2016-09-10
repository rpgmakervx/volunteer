package org.volunteer.loader;/**
 * Description : 
 * Created by YangZH on 16-8-31
 *  上午1:08
 */

import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Description :
 * Created by YangZH on 16-8-31
 * 上午1:08
 */

public class ExtensionLoader {

    private String pluginPath;

    public ExtensionLoader(String pluginPath) {
        this.pluginPath = pluginPath;
    }

    public ChannelInboundHandlerAdapter loadPlugin(String classname){
        URL[] urls;
        try {
            urls = new URL[]{new URL("file://"+this.pluginPath)};
            URLClassLoader classLoader = new URLClassLoader(urls);
            System.out.println(urls[0].toString());
            System.out.println(classname);
//            Thread.sleep(2000);
            Class<? extends ChannelInboundHandlerAdapter> clazz =
                    (Class<? extends ChannelInboundHandlerAdapter>) classLoader.loadClass(classname);
            System.out.println("生成新的class");
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
