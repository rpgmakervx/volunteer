/**
 * Description : 
 * Created by YangZH on 16-9-4
 *  上午10:32
 */

import org.volunteer.handler.http.gen.FileGen;

import java.io.IOException;

/**
 * Description :
 * Created by YangZH on 16-9-4
 * 上午10:32
 */

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
//        String classname="org.volunteer.handler.http.extension.ExtensionHandler0";
//        URL[] urls;
//        try {
//            urls = new URL[]{new URL("file:///home/code4j/IDEAWorkspace/volunteer/compile/")};
//            URLClassLoader classLoader = new URLClassLoader(urls);
//            System.out.println(urls[0].toString());
//            System.out.println(classname);
//            Class<? extends ChannelInboundHandlerAdapter> clazz =
//                    (Class<? extends ChannelInboundHandlerAdapter>) classLoader.loadClass(classname);
//            System.out.println("生成新的class");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        FileGen.compilePlugins();
//        Process process = Runtime.getRuntime().exec("javac /home/code4j/IDEAWorkspace/volunteer/compile/org/volunteer/handler/http/extension/ExtensionHandler0.java");
//        int code = process.waitFor();
        System.out.println("end");
    }
}
