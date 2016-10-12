package org.volunteer.handler.http;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午7:09
 */

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import org.volunteer.loader.ClassPool;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:09
 */

public class BaseHttpServerChildHandler extends ChannelInitializer<SocketChannel> {

    public static ChannelPipeline pipeline;

    public static void trigger(){
        int length = pipeline.names().size();
        for (int i=0;i<length-1;i++){
            System.out.println("remove:"+i);
            pipeline.removeLast();
        }
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpContentCompressor(9));
        pipeline.addLast(new HttpContentDecompressor());
        pipeline.addLast(new HttpObjectAggregator(1024000));
        pipeline.addLast(new TemplateGenHandler());
        Class<? extends ChannelHandler>[] array = ClassPool.getPlugins();
        System.out.println("plugin size : "+array.length);
        if (array.length != 0){
            for (Class<? extends ChannelHandler> clazz:array){
                ChannelHandler handler = null;
                try {
                    handler = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("handler name --> "+handler.getClass().getName());
                pipeline.addLast(handler);
            }
        }
        pipeline.addLast(new HttpServerHandler());
    }
    @Override
    protected synchronized void initChannel(SocketChannel sc) throws Exception {
//        ChannelPipeline pipeline = pipeline;
        BaseHttpServerChildHandler.pipeline = sc.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("compress", new HttpContentCompressor(9));
        pipeline.addLast("decompress", new HttpContentDecompressor());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
        pipeline.addLast(new TemplateGenHandler());
        Class<? extends ChannelHandler>[] array = ClassPool.getPlugins();
        System.out.println("plugin size : "+array.length);
        if (array.length != 0){
            for (Class<? extends ChannelHandler> clazz:array){
                ChannelHandler handler = clazz.newInstance();
                System.out.println("handler name --> "+handler.getClass().getName());
                pipeline.addLast(handler);
            }
        }
        pipeline.addLast("pagehandler", new HttpServerHandler());
    }
}
