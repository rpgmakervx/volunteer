package org.volunteer.handler.http.gen;/**
 * Description : 
 * Created by YangZH on 16-8-29
 *  上午3:02
 */

import org.volunteer.constant.Const;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description :
 * Created by YangZH on 16-8-29
 * 上午3:02
 */

public class FileGen {

    public static AtomicInteger counter = new AtomicInteger(0);

    public static void genFile(String content){
        String filename = Const.COMPILE_FILE+Const.SIMPLE_CLASSNAME+counter.get()+".java";
        File file = new File(filename);
        File folder = new File(Const.COMPILE_LIB);
        try {
            if (!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void nextFile(){
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
            Runtime.getRuntime().exec("javac -cp "+buffer.toString()+" "+filename);
//            Runtime.getRuntime().exec("javac -cp "+buffer.toString()+" "+filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        nextFile();
    }
}
