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

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:09
 */

public class BaseHttpServerChildHandler extends ChannelInitializer<SocketChannel> {

    private List<ChannelHandler> handlers;
    public BaseHttpServerChildHandler(){
        handlers = new ArrayList<>();
        handlers.add(new HttpRequestDecoder());
        handlers.add(new HttpResponseEncoder());
        handlers.add(new HttpContentCompressor(9));
        handlers.add(new HttpContentDecompressor());
        handlers.add(new HttpObjectAggregator(1024000));
        handlers.add(new CodeGenerateHandler());
        handlers.add(new HttpServerHandler());
    }
    public void addHandler(ChannelHandler handler){
        if (!handlers.contains(handler)){
            handlers.remove(handlers.size()-1);
            handlers.add(handler);
            handlers.add(new HttpServerHandler());
        }
    }
    @Override
    protected synchronized void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();

        for (ChannelHandler handler:handlers){
            pipeline.addLast(handler);
        }
//        pipeline.addLast("decoder", new HttpRequestDecoder());
//        pipeline.addLast("encoder", new HttpResponseEncoder());
//        pipeline.addLast("compress", new HttpContentCompressor(9));
//        pipeline.addLast("decompress", new HttpContentDecompressor());
//        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
//        pipeline.addLast(new CodeGenerateHandler());
//        pipeline.addLast(new HttpServerHandler());
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
