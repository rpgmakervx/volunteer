package org.volunteer.handler.http;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午7:42
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.resource.Resource;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:42
 */

public class HttpServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HttpRequest request = (HttpRequest) msg;
        String uri = request.uri();
        System.out.println("uri:"+uri);
//        Pattern pattern = Pattern.compile(Config.getString(Const.PATH));
//        Matcher matcher = pattern.matcher(uri);
//        if (!matcher.matches()){
//            ctx.fireChannelRead(request);
//            return;
//        }
        Resource resource = new Resource();
        HttpResponseStatus status = HttpResponseStatus.OK;
        byte[] bytes = resource.getResource(uri);
//        System.out.println(bytes.length);
        if (bytes==null){
            status = HttpResponseStatus.NOT_FOUND;
            bytes = resource.getErrorResource(Const.CODE_NOTFOUND);
        }
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 0, bytes.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, resource.getContentType());
        ctx.channel().writeAndFlush(response);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }


}
