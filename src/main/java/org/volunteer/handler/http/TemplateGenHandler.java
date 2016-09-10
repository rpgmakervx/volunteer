package org.volunteer.handler.http;/**
 * Description : 
 * Created by YangZH on 16-9-9
 *  上午10:22
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.gen.FileGen;
import org.volunteer.loader.ClassPool;
import org.volunteer.loader.ExtensionLoader;
import org.volunteer.template.CodeTemplate;
import org.volunteer.util.JSONUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.volunteer.configuration.Config.getString;
import static org.volunteer.constant.Const.*;

/**
 * Description :
 * Created by YangZH on 16-9-9
 * 上午10:22
 */

public class TemplateGenHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Map<String,Object> template = new HashMap<>();
        FullHttpRequest request = (FullHttpRequest)msg;
        String uri = request.uri();
        Pattern pattern = Pattern.compile(getString(PATH));
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.matches()){
            ctx.fireChannelRead(request);
            return;
        }
        String message = request.content().toString(CharsetUtil.UTF_8);
        System.out.println("content: "+message);
        Map<String,Object> param = JSONUtil.strToMap(message);
        List<Map<String,Object>> inputParam = new ArrayList<>();
        List<Map<String,Object>> outputParam = new ArrayList<>();
        String interfaces = (String) param.get(getString(INTERFACE));
        String input = getString(INPUT);
//        System.out.println("input:"+input);
        String output = getString(OUTPUT);
//        System.out.println("output:"+output);
        for (Map.Entry<String,Object> entry:param.entrySet()){
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List){
                List<Map<String,Object>> params = (List<Map<String, Object>>) entry.getValue();
//                System.out.println("key:"+key);
                if (key.equals(input)){
                    inputParam.addAll(params);
                }else if (key.equals(output)){
                    outputParam.addAll(params);
                }
            }
        }
        template.put(CLASSNAME,Const.SIMPLE_CLASSNAME+ FileGen.counter.get());
        template.put(getString(INTERFACE),interfaces);
        template.put(getString(INPUT),inputParam);
        template.put(getString(OUTPUT),outputParam);
        CodeTemplate ct = new CodeTemplate();
        FileGen.genFile(ct, template);
        FileGen.compilePlugins();
        ExtensionLoader loader = new ExtensionLoader(Const.COMPILE_PATH);
        ChannelInboundHandler handler = loader.loadPlugin("org.volunteer.handler.http.extension."+Const.SIMPLE_CLASSNAME+FileGen.counter.get());
        ClassPool.addPlugin(handler.getClass());
        System.out.println("PluginName: "+handler.getClass().getName());
        FileGen.nextFile();
        response(ctx,HttpResponseStatus.OK,"ok".getBytes());
    }

    public void response(ChannelHandlerContext ctx,HttpResponseStatus status,byte[] bytes){
        ByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 0, bytes.length);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                status, byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, Const.JSON);
        ctx.channel().writeAndFlush(response);
    }
}
