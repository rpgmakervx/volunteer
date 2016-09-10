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

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:02
 */

public class HttpServer {

    private ChannelFuture f = null;
    private ServerBootstrap b;
    private EventLoopGroup bossGroup ;
    private EventLoopGroup workerGroup;
    private ServerBootstrap bootstrap;
    private int port;
    private BaseHttpServerChildHandler handler;
//    private JarWatcher watcher = new JarWatcher();

    public HttpServer(String path) {
        handler = new BaseHttpServerChildHandler();
        if (Const.DEFAULT_CONFIGPATH.equals(path)){
            new Config(Config.class.getResourceAsStream(path));
        }else{
            new Config(path);
        }
    }

    public void startup(){
        port = Config.getInt(Const.LISTEN);
        launch();
    }

    public void startup(int port){
        this.port = port;
        launch();
    }

    private void launch() {
        System.out.println("正在启动服务。。。,服务端口:" + port);
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        b = new ServerBootstrap();
        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new BaseHttpServerChildHandler())
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .option(ChannelOption.TCP_NODELAY, true);
            close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private void close() throws Exception {
        f = b.bind(port).sync();
        System.out.println("服务已启动");
//        watcher.watchJarFolder();
        f.channel().closeFuture().sync();
    }
}
