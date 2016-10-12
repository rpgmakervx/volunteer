package org.volunteer.constant;/**
 * Description : 
 * Created by YangZH on 16-8-27
 *  下午8:05
 */

import java.io.File;

/**
 * Description :
 * Created by YangZH on 16-8-27
 * 下午8:05
 */

public class Const {

    public static final String LISTEN = "listen";
    public static final String LOCALHOST = "localhost";
    public static final String PATH = "path";

    public static final String DEFAULT_CONFIGPATH = "/volunteer.xml";


    public static final String DIR = System.getProperty("user.dir")+ File.separator+".."+File.separator;
    public static final String WEB_ROOT = DIR+"html"+File.separator;
    public static final String COMPILE_FILE = DIR+"compile"+File.separator+"org"+File.separator+"volunteer"+
            File.separator+"handler"+File.separator+"http"+File.separator+"extension"+File.separator;
    public static final String COMPILE_PATH = DIR+"compile"+File.separator;
    public static final String LISTEN_PATH = DIR+"listen"+File.separator;
    public static final String TEMPLATE_PATH = DIR+"template"+File.separator;

    public static final String COMPILE_LIB = DIR+"compile"+File.separator+"libs"+File.separator;
    public static final String DEFAULT_CLASSNAME = "org.volunteer.handler.http.extension.ExtensionHandler";
    public static final String SIMPLE_CLASSNAME = "ExtensionHandler";
    public static final String TEMPLATE_NAME = "Template.ftl";


    public static final String DEFAULT_DIR = "default_dir";
    public static final String NOTFOUND_PAGE = "notfound_page";
    public static final String ERROR_PAGE = "error_page";
    public static final String FORBIDDEN_PAGE = "forbidden_page";
    public static final String BADREQUEST_PAGE = "bad_request";


    public static final String INPUT = "input";
    public static final String OUTPUT = "output";
    public static final String PARAMNAME = "paramname";
    public static final String PARAMTYPE = "paramtype";
    public static final String INTERFACE = "interface";
    public static final String CLASSNAME = "classname";


    public static final String TEXT = "text/plain";
    public static final String TEXT_HTML = "text/html";
    public static final String HTML = "html";
    public static final String TEXT_CSS = "text/css";
    public static final String CSS = "css";
    public static final String JS_SCRIPT = "application/javascript";
    public static final String JSON = "application/json";
    public static final String JS = "js";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE = "(png|ico|jpg|jpeg|bmp|swf)";


    public static final int CODE_OK = 200;
    public static final int CODE_BADREQUEST= 400;
    public static final int CODE_FORBIDDEN = 403;
    public static final int CODE_NOTFOUND = 404;
    public static final int CODE_SERVERERROR = 500;

}
