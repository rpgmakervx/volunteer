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

//    public void init(){
//        handlers.clear();
//    }
//    public void addHandler(ChannelHandler handler){
//        if (!handlers.contains(handler)){
//            handlers.remove(handlers.size()-1);
//            handlers.add(handler);
//            handlers.add(new HttpServerHandler());
//        }
//    }
    @Override
    protected synchronized void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
//        handlers.add(new HttpRequestDecoder());
//        handlers.add(new HttpResponseEncoder());
//        handlers.add(new HttpContentCompressor(9));
//        handlers.add(new HttpContentDecompressor());
//        handlers.add(new HttpObjectAggregator(1024000));
//        handlers.add(new CodeGenerateHandler());
//        handlers.add(new HttpServerHandler());

        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("compress", new HttpContentCompressor(9));
        pipeline.addLast("decompress", new HttpContentDecompressor());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
        pipeline.addLast(new TemplateGenHandler());
//        pipeline.addLast(null);
        Class<? extends ChannelHandler>[] array = ClassPool.getPlugins();
        System.out.println("plugin size : "+array.length);
        if (array.length != 0){
            for (Class<? extends ChannelHandler> clazz:array){
                ChannelHandler handler = clazz.newInstance();
                System.out.println("handler name --> "+handler.getClass().getName());
                pipeline.addLast(handler);
            }
        }
        pipeline.addLast(new HttpServerHandler());
//        for (Class<? extends ChannelHandler> clazz:handlers){
//            System.out.println("handler: "+clazz.getSimpleName());
//            if (clazz == HttpObjectAggregator.class){
//                Constructor<? extends ChannelHandler> constructor = clazz.getConstructor(int.class);
//                pipeline.addLast(constructor.newInstance(1024000));
//            }else{
//                pipeline.addLast(clazz.newInstance());
//            }
//        }
//        System.out.println("handler个数："+handlers.size());
    }
}
