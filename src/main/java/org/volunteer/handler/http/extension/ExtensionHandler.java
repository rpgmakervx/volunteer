package org.volunteer.handler.http.extension;/**
 * Description : 
 * Created by YangZH on 16-8-29
 *  上午1:51
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.volunteer.configuration.Config;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.gen.ValueGen;
import org.volunteer.handler.http.param.ParamGetter;
import org.volunteer.util.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.volunteer.configuration.Config.getString;
import static org.volunteer.constant.Const.*;

/**
 * Description :
 * Created by YangZH on 16-8-29
 * 上午1:51
 */

public class ExtensionHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Map<String, Object> param = ParamGetter.getRequestParams(msg);
        HttpRequest request = (HttpRequest) msg;
        String interfaces = (String) param.get(Config.getString(INTERFACE));
        if (!request.uri().equals(interfaces)){ctx.fireChannelRead(request);return;}
        List<Map<String,Object>> inputParam = new ArrayList<>();
        List<Map<String,Object>> outputParam = new ArrayList<>();
        for (Map.Entry<String,Object> entry:param.entrySet()){
            String key = entry.getKey();
            List<Map<String,Object>> params = (List<Map<String, Object>>) entry.getValue();
            if (key.equals(getString(INPUT))){
                inputParam.addAll(params);
            }else if (key.equals(getString(OUTPUT))){
                outputParam.addAll(params);
            }
        }
        for (Map<String,Object> map:outputParam){
            String type = (String) map.get(getString(PARAMTYPE));
            ValueGen valueGen = new ValueGen();
//            valueGen.getValue()
        }
        Map<String,Object> jsonmap = JSONUtil.mapToJson(param);
        param.put("msg","success");
        param.put("code","200");
        String json = JSONUtil.mapToStr(param);
        byte[] bytes = json.getBytes();
        response(ctx, HttpResponseStatus.OK, bytes);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        Map<String,String> result = new HashMap<>();
        result.put("msg","error");
        result.put("code","500");
        String json = JSONUtil.mapToStr(result);
        byte[] bytes = json.getBytes();
        response(ctx,HttpResponseStatus.INTERNAL_SERVER_ERROR,bytes);
    }

    public void response(ChannelHandlerContext ctx,HttpResponseStatus status,byte[] bytes){
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 0, bytes.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, Const.JSON);
        ctx.channel().writeAndFlush(response);
    }
}
