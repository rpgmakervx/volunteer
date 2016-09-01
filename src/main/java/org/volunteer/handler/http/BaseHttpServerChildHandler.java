package org.volunteer.handler.http;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午7:09
 */

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:09
 */

public class BaseHttpServerChildHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline pipeline = sc.pipeline();
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("compress", new HttpContentCompressor(9));
        pipeline.addLast("decompress", new HttpContentDecompressor());
        pipeline.addLast("aggregator", new HttpObjectAggregator(1024000));
//        pipeline.addLast(new HttpServerHandler());
        pipeline.addLast(new CodeGenerateHandler());

    }
}
