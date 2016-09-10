package org.volunteer.handler.http.gen;/**
 * Description : 
 * Created by YangZH on 16-8-29
 *  上午3:02
 */

import org.volunteer.constant.Const;
import org.volunteer.template.CodeTemplate;

import java.io.File;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description :
 * Created by YangZH on 16-8-29
 * 上午3:02
 */

public class FileGen {

    public static AtomicInteger counter = new AtomicInteger(0);

    public static void genFile(CodeTemplate template,Map<String,Object> param){
        String classname = Const.COMPILE_FILE+Const.SIMPLE_CLASSNAME+counter.get()+".java";
        template.makeTemplate(param,classname);
    }

    public static void nextFile(){
        counter.getAndIncrement();
    }

    public static void compilePlugins(){
        File folder = new File(Const.COMPILE_LIB);
        String filename = Const.COMPILE_FILE+Const.SIMPLE_CLASSNAME+counter.get()+".java";
        System.out.println("command : "+"javac "+filename);
        StringBuffer buffer = new StringBuffer();
        int len = folder.listFiles().length;
        int index = 1;
        for (File jar:folder.listFiles()){
            String path = jar.getAbsolutePath();
            if (index == len){
                buffer.append(path);
            }else{
                buffer.append(path+":");
            }
            index++;
        }
        System.out.println("javac -cp "+buffer.toString()+" "+filename);
        try {
            Process process = Runtime.getRuntime().exec("javac -cp "+buffer.toString()+" "+filename);
            process.waitFor();
//            Runtime.getRuntime().exec("javac -cp "+buffer.toString()+" "+filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
