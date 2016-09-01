package org.volunteer.configuration;/**
 * Description : 
 * Created by YangZH on 16-8-14
 *  下午11:08
 */

import com.alibaba.fastjson.JSONObject;
import org.volunteer.util.JSONUtil;
import org.volunteer.util.XmlUtil;

import java.io.InputStream;

/**
 * Description :
 * Created by YangZH on 16-8-14
 * 下午11:08
 */

public class Config {
    private XmlUtil xmlUtil;
    private static JSONObject params;
    public Config(String path) {
        xmlUtil = new XmlUtil(path);
        String json = xmlUtil.xml2Json();
        System.out.println("param: "+json);
        params = JSONUtil.str2Json(json);
    }

    public Config(InputStream is){
        xmlUtil = new XmlUtil(is);
        String json = xmlUtil.xml2Json();
        System.out.println("param: "+json);
        params = JSONUtil.str2Json(json);
    }

    public static String getString(String param) {
        if (params == null)
            return "";
        return params.getString(param);
    }

    public static Integer getInt(String param) {
        if (params == null)
            return 0;
        return params.getIntValue(param);
    }

    public static void listAll(){
        System.out.println(JSONObject.toJSONString(params));
    }


}
