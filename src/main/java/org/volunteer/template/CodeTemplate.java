package org.volunteer.template;/**
 * Description : 
 * Created by YangZH on 16-9-7
 *  下午3:03
 */

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.volunteer.constant.Const;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Description :
 * Created by YangZH on 16-9-7
 * 下午3:03
 */

public class CodeTemplate {

    private Configuration configuration;

    private Template temp;
    public CodeTemplate(){

        configuration = new Configuration(Configuration.VERSION_2_3_22);
        // 设定去哪里读取相应的ftl模板
        try {
            configuration.setDirectoryForTemplateLoading(new File(Const.TEMPLATE_PATH));
            temp = configuration.getTemplate(Const.TEMPLATE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeTemplate(Map<String,Object> kv,String classfile){
        System.out.println("kv: \n"+kv);
        File file = new File(classfile);
        try {
            FileWriter writer = new FileWriter(file);
            temp.process(kv,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
