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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:09
 */

public class BaseHttpServerChildHandler extends ChannelInitializer<SocketChannel> {

    private List<Class<? extends ChannelHandler>> handlers;

    public BaseHttpServerChildHandler(){
        handlers = new ArrayList<>();
    }
    public void addHandler(Class<? extends ChannelHandler> handler){
        if (!handlers.contains(handler))
            handlers.add(handler);
    }
    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
//        ChannelPipeline pipeline = sc.pipeline();
//        pipeline.addLast("decoder", new HttpRequestDecoder());
//        pipeline.addLast("encoder", new HttpResponseEncoder());
//        pipeline.addLast("compress", new HttpContentCompressor(9));
//        pipeline.addLast("decompress", new HttpContentDecompressor());
//        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
////        pipeline.addLast(new HttpServerHandler());
//        pipeline.addLast(new CodeGenerateHandler());
        ChannelPipeline pipeline = sc.pipeline();
        for (Class<? extends ChannelHandler> clazz:handlers){
            if (clazz == HttpObjectAggregator.class){
                Constructor<? extends ChannelHandler> constructor = clazz.getConstructor(int.class);
                pipeline.addLast(constructor.newInstance(1024000));
            }else{
                pipeline.addLast(clazz.newInstance());
            }
        }
        System.out.println("handler个数："+handlers.size());
    }
}
