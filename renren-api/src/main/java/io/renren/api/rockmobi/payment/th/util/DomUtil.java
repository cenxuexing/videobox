/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.util;

import io.renren.api.rockmobi.payment.th.service.impl.ThPayServiceImpl;
import io.renren.common.utils.LoggerUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: DomUtil, v0.1 2019年04月26日 17:50闫迎军(YanYingJun) Exp $
 */
public class DomUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ThPayServiceImpl.class);
    /**
     * 添加孩子节点元素
     * @param parent 父节点
     * @param childName 孩子节点名称
     * @param childValue 孩子节点值
     * @return 新增节点
     */
    public static Element addChild(Element parent, String childName,
            String childValue) {
        //添加节点元素
        Element child = parent.addElement(childName);
        //为元素设值
        child.setText(childValue == null ? "" : childValue);
        return child;
    }

    /**
     * DOM4j的Document对象转为XML报文串
     * @param document
     * @param charset
     * @return 经过解析后的xml字符串
     */
     public static String documentToString(Document document, String charset) {
         LoggerUtils.error(LOGGER, "进入documentToString");
        StringWriter stringWriter = new StringWriter();
         LoggerUtils.error(LOGGER, "document：" + document);
         LoggerUtils.error(LOGGER, "charset：" + charset);
        //获得格式化输出流
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置字符集,默认为UTF-8
        format.setEncoding(charset);
        format.setNewLineAfterDeclaration(false);
        //写文件流
        XMLWriter xmlWriter = new XMLWriter(stringWriter, format);
        LoggerUtils.error(LOGGER, "xmlWriter：" + xmlWriter);
        try {
            xmlWriter.write(document);
            LoggerUtils.error(LOGGER, "xmlWriter.write(document)：" + document);
            xmlWriter.flush();
            xmlWriter.close();
        } catch (IOException e) {
            LoggerUtils.error(LOGGER, "documentToString方法异常：" + e.getMessage());
            //throw new RuntimeException(e);
        }
        return stringWriter.toString();
     }

    /**
     * 去掉声明头的(即<?xml...?>去掉)
     * @param document
     * @param charset
     * @return
     */
    public static String documentToStringNoDeclaredHeader(Document document,String charset) {
        String xml = documentToString(document, charset);
        return xml.replaceFirst("\\s*<[^<>]+>\\s*", "");
    }

    public static String createXml(String type, Map<String, Object> head, Map<String, Object> charge, Map<String, Object> otp, Map<String, Object> recurring, Map<String, Object> notify, Map<String, Object> msg){

        Document document = DocumentHelper.createDocument();
        document.setXMLEncoding("UTF-8");
        Element root = document.addElement(type);
        if(!StringUtils.isEmpty(head)){
            addDocument(root, head);
        }
        if(!StringUtils.isEmpty(charge)){
            Element chargeElement = root.addElement("charge");
            addDocument(chargeElement, charge);
        }
        if(!StringUtils.isEmpty(otp)){
            Element chargeElement = root.addElement("otp");
            addDocument(chargeElement, otp);
        }
        if(!StringUtils.isEmpty(recurring)){
            Element chargeElement = root.addElement("recurring");
            addDocument(chargeElement, recurring);
        }
        if(!StringUtils.isEmpty(notify)){
            Element chargeElement = root.addElement("notify");
            addDocument(chargeElement, notify);
        }
        if(!StringUtils.isEmpty(msg)){
            Element chargeElement = root.addElement("msg");
            addDocument(chargeElement, msg);
        }
        return DomUtil.documentToString(root.getDocument(), "UTF-8");
    }


    public static void addDocument(Element root, Map<String, Object> map){
        Iterator<String> iter = map.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            String value = map.get(key).toString();
            DomUtil.addChild(root, key, value);
        }
    }
}
