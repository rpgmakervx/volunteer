package org.volunteer.util;/**
 * Description : 
 * Created by YangZH on 16-8-14
 *  下午11:08
 */

import com.alibaba.fastjson.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Description :
 * Created by YangZH on 16-8-14
 * 下午11:08
 */

public class XmlUtil {

    private static SAXReader reader = new SAXReader();
    private Document document = null;
    private Element root = null;
    private JSONObject object = new JSONObject();

    public XmlUtil(InputStream is) {
        try {
            document = reader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XmlUtil(String xml_path) {
        try {
            InputStream is = new FileInputStream(xml_path);
            document = reader.read(is);
            root = document.getRootElement();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        Iterator<Element> elementIterator = root.elementIterator();
        while (elementIterator.hasNext()) {
            Element element = elementIterator.next();
            Iterator<Attribute> attrIterator = element.attributeIterator();
            while (attrIterator.hasNext()) {
                Attribute attr = attrIterator.next();
                object.put(attr.getName(), attr.getValue());
            }
        }
    }

    public String xml2Json() {
        return object.toJSONString();
    }

    public void listAll() {
        System.out.println(object.toJSONString());
    }
}
