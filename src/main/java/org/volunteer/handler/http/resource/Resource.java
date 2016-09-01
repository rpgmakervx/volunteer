package org.volunteer.handler.http.resource;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午7:53
 */

import org.volunteer.configuration.Config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.volunteer.constant.Const.*;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午7:53
 */

public class Resource {

    private String contentType;

    private int statusCode;

    public byte[] getResource(String resource){
        FileInputStream fis = null;
        File file = new File(WEB_ROOT+resource);
        if (!file.exists()){
            System.out.println(file.getAbsolutePath()+"文件不存在");
            return null;
        }
        try {
            fis = new FileInputStream(file);
            String type = file.getName().substring(file.getName().lastIndexOf(".")+1);
            boolean skip = true;
            switch (type){
                case HTML:contentType = TEXT_HTML;break;
                case JS:contentType = JS_SCRIPT;break;
                case CSS:contentType = TEXT_CSS;break;
                default:skip = false;break;
            }
            if (!skip){
                Pattern pattern = Pattern.compile(IMAGE_PNG);
                Matcher matcher = pattern.matcher(type);
                if (matcher.matches()){
                    contentType = IMAGE;
                }
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = fis.read(bytes)) != -1){
                baos.write(bytes,0,len);
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getErrorResource(int type){
        String resource;
        String default_path = Config.getString(DEFAULT_DIR)+File.separator;
        switch (type){
            case CODE_NOTFOUND:
                resource = default_path+Config.getString(NOTFOUND_PAGE);
                break;
            case CODE_FORBIDDEN:
                resource = default_path+Config.getString(FORBIDDEN_PAGE);
                break;
            case CODE_BADREQUEST:
                resource = default_path+Config.getString(BADREQUEST_PAGE);
                break;
            default:
                resource = default_path+Config.getString(NOTFOUND_PAGE);
                break;
        }
        return getResource(resource);
    }

    public String getContentType(){
        if (contentType == null||contentType.isEmpty()){
            return TEXT;
        }
        return contentType;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
