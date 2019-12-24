/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.util;

import io.renren.api.rockmobi.payment.ph.model.vo.ExtensionInfo;
import org.assertj.core.util.Lists;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * XML工具类
 * @author 闫迎军(YanYingJun)
 * @version $Id: XmlUtil, v0.1 2019年02月18日 9:24闫迎军(YanYingJun) Exp $
 */
public class XmlUtil {

    public static Map<String,String> map = new HashMap<>();

    public static Map parse(String soap) throws DocumentException {
        Document doc = DocumentHelper.parseText(soap);//报文转成doc对象
        Element root = doc.getRootElement();//获取根元素，准备递归解析这个XML树
        map = new HashMap<>(); // fix bug: 防止上一个map的属性残留到下次解析结果中
        getCode(root);
        return map;
    }

    public static void getCode(Element root){
        if(root.elements()!=null){
            List<ExtensionInfo> listInfo = Lists.newArrayList();
            List<Element> list = root.elements();//如果当前跟节点有子节点，找到子节点
            for(Element e:list){//遍历每个节点
                if(e.elements().size()>0){
                    getCode(e);//当前节点不为空的话，递归遍历子节点；
                }
                if(e.elements().size()==0){
                    ExtensionInfo extensionInfo = new ExtensionInfo();
                    if(e.getName().equalsIgnoreCase("key")){
                        extensionInfo.setKey(e.getName());
                        extensionInfo.setValue(e.getTextTrim());
                        listInfo.add(extensionInfo);
                    }else if(e.getName().equalsIgnoreCase("value")){
                        extensionInfo.setKey(e.getName());
                        extensionInfo.setValue(e.getTextTrim());
                        listInfo.add(extensionInfo);
                    }else{
                        map.put(e.getName(), e.getTextTrim());
                    }

                }//如果为叶子节点，那么直接把名字和值放入map
            }
            if(!StringUtils.isEmpty(listInfo) && listInfo.size() > 0){
                for(int i = 0;i < listInfo.size(); i++){
                    if(i%2 != 0){
                        map.put(listInfo.get(0).getValue(), listInfo.get(1).getValue());
                    }
                }
            }

        }
    }

    /**
     * @功能 读取流
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            outSteam.write(buffer, 0, len);
        }
        outSteam.close();
        inStream.close();
        return outSteam.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        String strXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:loc=\"http://www.csapi.org/schema/parlayx/subscribe/manage/v1_0/local\"> \n" +
                "   <soapenv:Header> \n" +
                "      <tns:RequestSOAPHeader xmlns:tns=\"http://www.huawei.com.cn/schema/common/v2_1\"> \n" +
                "         <tns:spId>35000001</tns:spId> \n" +
                "         <tns:spPassword>C5216E519A071D601BEDD150F3FCD026</tns:spPassword> \n" +
                "         <tns:timeStamp>20080101010101</tns:timeStamp> \n" +
                "      </tns:RequestSOAPHeader> \n" +
                "   </soapenv:Header> \n" +
                "   <soapenv:Body> \n" +
                "      <loc:subscribeProductRequest> \n" +
                "         <loc:subscribeProductReq> \n" +
                "            <userID> \n" +
                "               <ID>8612312312123</ID> \n" +
                "               <type>0</type> \n" +
                "            </userID> \n" +
                "            <subInfo> \n" +
                "               <productID>12345678</productID> \n" +
                "               <operCode>zh</operCode> \n" +
                "               <isAutoExtend>0</isAutoExtend> \n" +
                "               <channelID>2</channelID>               \n" +
                "               <extensionInfo> \n" +
                "                 <namedParameters> \n" +
                "                   <key>keyword</key> \n" +
                "                   <value>sub</value> \n" +
                "                </namedParameters> \n" +
                "              </extensionInfo> \n" +
                "            </subInfo> \n" +
                "         </loc:subscribeProductReq> \n" +
                "      </loc:subscribeProductRequest> \n" +
                "   </soapenv:Body> \n" +
                "</soapenv:Envelope>";
        //初始化报文，调用parse方法，获得结果map，然后按照需求取得字段，或者转化为其他格式
        Map map = parse(strXML);
        //获得字段s:SourceSysId的值;
        System.out.println(map);

    }

    @SuppressWarnings("rawtypes")
    public static void xml2String2() throws Exception{

        String strXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> \r\n" +
                "   <soapenv:Body> \r\n" +
                "      <ns1:syncOrderRelation xmlns:ns1=\"http://www.csapi.org/schema/parlayx/data/sync/v1_0/local\"> \r\n" +
                "         <ns1:userID> \r\n" +
                "            <ID>8619800000001</ID> \r\n" +
                "            <type>0</type> \r\n" +
                "         </ns1:userID> \r\n" +
                "         <ns1:spID>001100</ns1:spID> \r\n" +
                "         <ns1:productID>1000000423</ns1:productID> \r\n" +
                "         <ns1:serviceID>0011002000001100</ns1:serviceID> \r\n" +
                "         <ns1:serviceList>0011002000001100</ns1:serviceList> \r\n" +
                "         <ns1:updateType>1</ns1:updateType> \r\n" +
                "         <ns1:updateTime>20130723082551</ns1:updateTime> \r\n" +
                "         <ns1:updateDesc>Addition</ns1:updateDesc> \r\n" +
                "         <ns1:effectiveTime>20130723082551</ns1:effectiveTime> \r\n" +
                "         <ns1:expiryTime>20361231160000</ns1:expiryTime> \r\n" +
                "         <ns1:extensionInfo> \r\n" +
                "            <item> \r\n" +
                "               <key>accessCode</key> \r\n" +
                "               <value>20086</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>chargeMode</key> \r\n" +
                "               <value>0</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>MDSPSUBEXPMODE</key> \r\n" +
                "               <value>1</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>objectType</key> \r\n" +
                "               <value>1</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>isFreePeriod</key> \r\n" +
                "               <value>false</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>payType</key> \r\n" +
                "               <value>0</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>transactionID</key> \r\n" +
                "               <value>504016000001307231624304170004</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>orderKey</key> \r\n" +
                "               <value>999000000000000194</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>keyword</key> \r\n" +
                "               <value>sub</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>cycleEndTime</key> \r\n" +
                "               <value>20130822160000</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>durationOfGracePeriod</key> \r\n" +
                "               <value>-1</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>serviceAvailability</key> \r\n" +
                "               <value>0</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>channelID</key> \r\n" +
                "               <value>1</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>TraceUniqueID</key> \r\n" +
                "               <value>504016000001307231624304170005</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>operCode</key> \r\n" +
                "               <value>zh</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>rentSuccess</key> \r\n" +
                "               <value>true</value> \r\n" +
                "            </item> \r\n" +
                "            <item> \r\n" +
                "               <key>try</key> \r\n" +
                "               <value>false</value> \r\n" +
                "            </item>             \r\n" +
                "            <item> \r\n" +
                "               <key>shortMessage</key> \r\n" +
                "               <value>Hello world.</value> \r\n" +
                "            </item> \r\n" +
                "         </ns1:extensionInfo> \r\n" +
                "      </ns1:syncOrderRelation> \r\n" +
                "   </soapenv:Body> \r\n" +
                "</soapenv:Envelope>\r\n";
        //初始化报文，调用parse方法，获得结果map，然后按照需求取得字段，或者转化为其他格式
        Map map = parse(strXML);
        //获得字段s:SourceSysId的值;
        System.out.println(map);

    }

}
