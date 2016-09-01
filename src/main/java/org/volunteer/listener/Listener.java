package org.volunteer.listener;

import io.netty.channel.ChannelInboundHandler;
import net.contentobjects.jnotify.JNotifyAdapter;
import org.apache.log4j.Logger;
import org.volunteer.constant.Const;
import org.volunteer.loader.ExtensionLoader;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description :
 * Created by YangZH on 16-6-13
 * 下午2:15
 */

public class Listener extends JNotifyAdapter {
    private Logger logger = Logger.getLogger(Listener.class);

    ExecutorService pool = Executors.newCachedThreadPool();

    @Override
    public void fileCreated(int i, String s, String filename) {
        System.out.println("创建了一个文件" + s + " | " + filename);
        String sufix = filename.substring(filename.lastIndexOf("."));
        System.out.println("文件后缀："+sufix);
        if (".class".equals(sufix)){
            String classpath =  filename.substring(0,filename.lastIndexOf("."));
            String[] segements = classpath.split(File.separator);
            String classname = segements[segements.length-1];
            System.out.println("plugin name:"+"org.volunteer.handler.http.extension."+classname);
            ExtensionLoader loader = new ExtensionLoader(Const.COMPILE_PATH);
            ChannelInboundHandler handler = loader.loadPlugin("org.volunteer.handler.http.extension."+classname);
            System.out.println("PluginName: "+handler.getClass().getName());
        }
        System.out.println("创建文件的监听结束");
    }

    @Override
    public void fileModified(int i, String s, String filename) {
    }

    @Override
    public void fileRenamed(int i, String s, String s1, String s2) {
        logger.info("重命名了一个文件 " + s + " | " + s1 + " | " + s2);
    }

    @Override
    public void fileDeleted(int wd, String rootPath, String name) {
        logger.info("删除了一个文件 " + rootPath + name);
        //方案2，先记录要删除的jar，再删除完成被监听到后，从codecat中移除
    }

}
