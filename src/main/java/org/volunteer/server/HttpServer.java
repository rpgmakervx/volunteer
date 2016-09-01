package org.volunteer.server;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午7:02
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.volunteer.configuration.Config;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.BaseHttpServerChildHandler;
import org.volunteer.listener.JarWatcher;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:02
 */

public class HttpServer {

    public HttpServer(String path) {
        if (Const.DEFAULT_CONFIGPATH.equals(path)){
            new Config(Config.class.getResourceAsStream(path));
        }else{
            new Config(path);
        }

    }

    public void startup(){
        launch(Config.getInt(Const.LISTEN));
    }

    public void startup(int port){
        launch(port);
    }

    private void launch(int port) {
        System.out.println("正在启动服务。。。,服务端口:" + port);
        JarWatcher watcher = new JarWatcher();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        ChannelFuture f = null;
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new BaseHttpServerChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .option(ChannelOption.TCP_NODELAY,true);
            f = b.bind(port).sync();
            System.out.println("服务已启动");
            watcher.watchJarFolder();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
