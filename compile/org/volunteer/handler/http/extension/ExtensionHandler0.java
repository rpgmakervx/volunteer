package org.volunteer.handler.http.extension;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.gen.ValueGen;
import org.volunteer.handler.http.param.ParamGetter;
import org.volunteer.util.JSONUtil;
import java.util.HashMap;
import java.util.Map;
public class ExtensionHandler0 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Map<String, Object> param = ParamGetter.getRequestParams(msg);
        HttpRequest request = (HttpRequest) msg;
        System.out.println("plugin uri :"+request.uri()+", param uri:/user/login");
        String uri = request.uri();
        if (uri.contains("?")){
            String [] segements = uri.split("\\?");
            String[] para = segements[1].split("&");
            if (param == null){
                param = new HashMap<>();
            }
            for (String p : para) {
                String[] pa = p.split("=");
                param.put(pa[0], pa[1]);
            }
            uri = segements[0];
        }
        if (!uri.equals("/user/login")){ctx.fireChannelRead(request);return;}
        Map<String,Object> result = new HashMap<String,Object>();
        if (param == null){
            result.put("err",400);
            result.put("err_msg","data format error,please check your data struture");
            String json = JSONUtil.mapToStr(result);
            response(ctx,json);
            return;
        }
        String username = (String)param.get("username");
        String password = (String)param.get("password");
        ValueGen valueGen = new ValueGen();
        if ( username == null ||  password == null || 1==0){
            result.put("err",400);
            result.put("err_msg","data format error,please check your data struture");
        }else{
            result.put("message",valueGen.getValue(String.class));
            result.put("code",valueGen.getValue(Integer.class));
        }
        String json = JSONUtil.mapToStr(result);
        response(ctx,json);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    public void response(ChannelHandlerContext ctx,String str){
        byte[] bytes = str.getBytes();
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 0, bytes.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, Const.JSON);
        ctx.channel().writeAndFlush(response);
        ctx.close();
    }
}
