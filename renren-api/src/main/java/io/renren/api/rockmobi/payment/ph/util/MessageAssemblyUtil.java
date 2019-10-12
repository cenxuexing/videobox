/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.util;

import io.renren.api.rockmobi.payment.ph.model.vo.AoParams;
import io.renren.api.rockmobi.payment.ph.phenum.PhApiTypeEnum;
import io.renren.common.utils.LoggerUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import javax.xml.soap.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: MessageAssemblyUtil, v0.1 2019年02月16日 15:48闫迎军(YanYingJun) Exp $
 */
public class MessageAssemblyUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAssemblyUtil.class);

    public static String buildSoap(Map<String,Object> mapHead, Map<String, Object> mapBody, String type, String dataPrefix) {
        SOAPMessage soapMessage = null;
        StringWriter output = null;
        try {
            String url = null;
            soapMessage = MessageFactory.newInstance().createMessage();
            SOAPEnvelope soapEnvelope = soapMessage.getSOAPPart().getEnvelope();
            if(type.equals(PhApiTypeEnum.DATASYNC.getCode())){
                url = "http://www.csapi.org/schema/parlayx/data/sync/v1_0/local";
                soapEnvelope.addNamespaceDeclaration("loc", url);
            }else if(type.equals(PhApiTypeEnum.SUBSCRIBE.getCode()) || type.equals(PhApiTypeEnum.UNSUBSCRIBE.getCode())){
                url = "http://www.csapi.org/schema/parlayx/subscribe/manage/v1_0/local";
                soapEnvelope.addNamespaceDeclaration("loc", url);
            }else {
                url = "http://www.sdp.com/schema/subscription/v1_0/local";
                soapEnvelope.addNamespaceDeclaration("loc", url);
            }

            SOAPHeader soapHeader = null;
            if(!StringUtils.isEmpty(mapHead)){
                soapHeader = soapEnvelope.getHeader();
                SOAPElement soapHeaderElement = soapHeader.addChildElement(soapEnvelope.addChildElement("RequestSOAPHeader", "tns", "http://www.huawei.com.cn/schema/common/v2_1"));
                for (Map.Entry<String,Object> entry:mapHead.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue().toString();
                    recursiveAccessElement(soapHeaderElement,key,value, "tns");
                }
            }

            SOAPElement soapBody = soapEnvelope.getBody();
            SOAPElement soapElement = null;
            if(type.equals(PhApiTypeEnum.SUBSCRIBE.getCode())){
                soapElement = soapBody.addChildElement(soapEnvelope.addChildElement("subscribeProductRequest", "loc"));
                soapElement.addChildElement(soapBody.addChildElement("subscribeProductReq", "loc"));
            }else if(type.equals(PhApiTypeEnum.UNSUBSCRIBE.getCode())){
                soapElement = soapBody.addChildElement(soapEnvelope.addChildElement("unSubscribeProductRequest", "loc"));
                soapElement.addChildElement(soapBody.addChildElement("unSubscribeProductReq", "loc"));
            }else if(type.equals(PhApiTypeEnum.DATASYNC.getCode())){
                soapElement = soapBody.addChildElement(soapEnvelope.addChildElement("syncOrderRelationResponse", "loc"));
            }else {
                soapElement = soapBody.addChildElement(soapEnvelope.addChildElement("getSubScriptionListReq", "loc"));
            }

            for (Map.Entry<String,Object> entry:mapBody.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                recursiveAccessElement(soapElement,key,value, dataPrefix);
            }

            /*for (Map.Entry<String,Object> entry:mapParams.entrySet()) {
                String key = Pattern.compile("[\\d]").matcher(entry.getKey()).replaceAll("");
                String value = entry.getValue().toString();
                recursiveAccessElement1(soapElement,key,value, dataPrefix);
            }*/


            // Save the message
            soapMessage.saveChanges();
            //将构建的soap的xml打印出来 便于调试
            Document doc = soapMessage.getSOAPPart().getEnvelope().getOwnerDocument();
            output = new StringWriter();
            TransformerFactory.newInstance().newTransformer().transform( new DOMSource(doc), new StreamResult(output));
            LoggerUtils.info(LOGGER, "构建soap的xml文件：" + soapMessage.toString());
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "构建soap的xml文件异常，异常原因：" + e.getMessage());
        }
        return output.toString().replaceAll("SOAP-ENV", "soapenv");
    }

    /**
     * 递归调用判断key是否存在.号。组建SOAPElement
     * @param element
     * @param key
     * @param value
     * @return
     * @throws SOAPException
     */
    private static SOAPElement recursiveAccessElement(SOAPElement element,String key,String value, String prefix) throws SOAPException{
        if (key.contains(".")){
            String[] arr = key.split("\\.",2);
            Iterator<SOAPElement> iterator = element.getChildElements();
            Map<String,SOAPElement> childs = new HashMap<>();
            while (iterator.hasNext()){
                SOAPElement e = iterator.next();
                childs.put(e.getLocalName(),e);
            }
            boolean flag = false;
            for (Map.Entry<String,SOAPElement> entry:childs.entrySet()) {
                if (entry.getKey().equals(arr[0])){
                    flag =true;
                    recursiveAccessElement(entry.getValue(),arr[1],value, null);
                }
            }
            if (!flag){
                SOAPElement soapElement = null;
                if(StringUtils.isEmpty(prefix)){
                    soapElement = element.addChildElement(arr[0]);
                }else{
                    soapElement = element.addChildElement(arr[0],prefix);
                }
                recursiveAccessElement(soapElement,arr[1],value, null);
            }
        }else {
            if(StringUtils.isEmpty(prefix)){
                element.addChildElement(key).addTextNode(value);
            }else{
                element.addChildElement(key,prefix).addTextNode(value);
            }

        }
        return element;
    }


    /**
     * 递归调用判断key是否存在.号。组建SOAPElement
     * @param element
     * @param key
     * @param value
     * @return
     * @throws SOAPException
     */
    private static SOAPElement recursiveAccessElement1(SOAPElement element,String key,String value, String prefix) throws SOAPException{
        if (key.contains(".")){
            String[] arr = key.split("\\.",2);
            Iterator<SOAPElement> iterator = element.getChildElements();
            //Map<String,SOAPElement> childs = new HashMap<>();
            List<AoParams> list = null;
            while (iterator.hasNext()) {
                list = Lists.newArrayList();
                AoParams params = new AoParams();
                SOAPElement e = iterator.next();
                params.setStr(e.getLocalName());
                params.setSoapElement(e);
                list.add(params);
            }
            boolean flag = false;
            SOAPElement soapElement = null;
            if (!StringUtils.isEmpty(list)){
                for(AoParams aoParams : list){
                    if(aoParams.getStr().equals(arr[0])){
                        if(arr[1].equals("value")){
                            flag =true;
                            recursiveAccessElement1(aoParams.getSoapElement(),arr[1],value, null);
                        }
                    }
                }
            }
            if (!flag){
                if(StringUtils.isEmpty(prefix)){
                    soapElement = element.addChildElement(arr[0]);
                }else{
                    soapElement = element.addChildElement(arr[0],prefix);
                }
                recursiveAccessElement1(soapElement,arr[1],value, null);
            }

        }else {
            if(StringUtils.isEmpty(prefix)){
                element.addChildElement(key).addTextNode(value);
            }else{
                element.addChildElement(key,prefix).addTextNode(value);
            }

        }
        return element;
    }
}
