package org.volunteer;/**
 * Description : 
 * Created by YangZH on 16-8-30
 *  下午4:28
 */

import org.volunteer.configuration.Config;
import org.volunteer.constant.Const;
import org.volunteer.handler.http.gen.FileGen;
import org.volunteer.listener.JarWatcher;
import org.volunteer.util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.volunteer.configuration.Config.getString;
import static org.volunteer.constant.Const.*;

/**
 * Description :
 * Created by YangZH on 16-8-30
 * 下午4:28
 */

public class Test {

    public static void main(String[] args) throws IOException {
//        System.out.println(System.getProperty("java.library.path"));
//        System.out.println(COMPILE_LIB+"libjnotify.so");
//        System.load(COMPILE_LIB+"libjnotify.so");
        final JarWatcher watcher = new JarWatcher();
        new Thread(new Runnable() {
            @Override
            public void run() {
                watcher.watchJarFolder();
            }
        }).start();
        Test test = new Test();
        test.compile();
//        ExtensionLoader loader = new ExtensionLoader(COMPILE_PATH);
//        ChannelInboundHandler handler = loader.loadPlugin("org.volunteer.handler.http.extension.ExtensionHandler00");
//        System.out.println(handler.getClass().getName());
    }

    public void compile() throws IOException {
        new Config(Config.class.getResourceAsStream("/volunteer.xml"));
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(Test.class.getResourceAsStream("/data.json")));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = reader.readLine())!=null){
            buffer.append(line);
        }
        System.out.println(buffer.toString());
        test(JSONUtil.strToMap(buffer.toString()));
    }

    public void test(Map<String,Object> param){
        List<Map<String,Object>> inputParam = new ArrayList<>();
        List<Map<String,Object>> outputParam = new ArrayList<>();
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
        StringBuffer buffer = new StringBuffer();
        buffer.append("package org.volunteer.handler.http.extension;\n");
        buffer.append("import io.netty.buffer.ByteBuf;\n" +
                "import io.netty.buffer.Unpooled;\n" +
                "import io.netty.channel.ChannelHandlerContext;\n" +
                "import io.netty.channel.ChannelInboundHandlerAdapter;\n" +
                "import io.netty.handler.codec.http.*;\n" +
                "import org.volunteer.configuration.Config;\n" +
                "import org.volunteer.constant.Const;\n" +
                "import org.volunteer.handler.http.gen.ValueGen;\n" +
                "import org.volunteer.handler.http.param.ParamGetter;\n" +
                "import org.volunteer.util.JSONUtil;\n" +
                "import static org.volunteer.constant.Const.*;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n");
        buffer.append("public class "+Const.SIMPLE_CLASSNAME+FileGen.counter.get()+" extends ChannelInboundHandlerAdapter {\n");
        buffer.append("\t@Override\n\tpublic void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {\n");
        buffer.append("\t\tMap<String, Object> param = ParamGetter.getRequestParams(msg);\n");
        buffer.append("\t\tHttpRequest request = (HttpRequest) msg;\n");
        buffer.append("\t\tString interfaces = (String) param.get(Config.getString(INTERFACE));\n");
        buffer.append("\t\tif (!request.uri().equals(interfaces)){ctx.fireChannelRead(request);return;}\n");
        /*service*/
        for(Map<String, Object> map:inputParam){
//            System.out.println("adding input param");
            buffer.append("\t\t" + map.get(PARAMTYPE) + " " + map.get(PARAMNAME) + "=(" + map.get(PARAMTYPE) + ")param.get(\"" + map.get(PARAMNAME) + "\");\n");
        }
        buffer.append("\t\tMap<String,Object> result = new HashMap<String,Object>();\n");
        buffer.append("\t\tValueGen valueGen = new ValueGen();\n");
        for (Map<String,Object> map:outputParam){
//            System.out.println("adding output param");
            String type = (String) map.get(getString(PARAMTYPE));
            String name = (String) map.get(getString(PARAMNAME));
            buffer.append("\t\tresult.put(\"" + name + "\",valueGen.getValue(" + type + ".class));\n");
        }
        buffer.append("\t\tString json = JSONUtil.mapToStr(result);\n");
        buffer.append("\t\tbyte[] bytes = json.getBytes();\n");
        buffer.append("\t\tByteBuf byteBuf = Unpooled.wrappedBuffer(bytes, 0, bytes.length);\n");
        buffer.append("\t\tFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK, byteBuf);\n");
        buffer.append("\t\tresponse.headers().set(HttpHeaderNames.CONTENT_TYPE, Const.JSON);\n");
        buffer.append("\t\tctx.channel().writeAndFlush(response);\n");
        buffer.append("\t\t;\n");
        buffer.append("\t}\n");
        buffer.append("}\n");
        FileGen.genFile(buffer.toString());
        FileGen.compilePlugins();
//        response(ctx, HttpResponseStatus.OK,"success".getBytes());
    }

    public void gamble(){
        //1.随机从奖池中抽取，抽取到25元，则标记 变量level 5次 index=0 index++  add 50 to List
    }
}
