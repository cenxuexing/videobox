/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.rockmobi.test;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.util.HttpUtil;
import io.renren.api.rockmobi.payment.th.model.vo.ChargeRecurringResp;
import io.renren.api.rockmobi.payment.th.service.ThOrderService;
import io.renren.api.rockmobi.payment.th.service.ThPayService;
import io.renren.api.rockmobi.payment.th.util.MapUtil;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.entity.MmMerchantEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.service.MmMerchantService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 推广短信测试发送
 * @author 闫迎军(YanYingJun)
 * @version $Id: TestPromotionSMSSending, v0.1 2019年02月26日 21:10闫迎军(YanYingJun) Exp $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TestPromotionSMSSending {

    @Value("${bsnl_south_send_sm}")
    private String bsnlSouthSendSmUrl;

    @Value("${spring.secretkey}")
    private String secretkey;
    
    @Value("${ph.sm.product_id}")
    private String productId;

    @Autowired
    private PhPayService indiaService;

    @Autowired
    private PhPayService phPayService;

    @Autowired
    private MmMerchantService mmMerchantService;

    @Autowired
    private ThPayService thPayService;

    @Autowired
    private MmProductOrderService mmProductOrderService;

    @Autowired
    private ThOrderService thOrderService;

    @Autowired
    private MmProductService mmProductService;

    @Test
    public void test0044(){
        MmMerchantEntity mmMerchantEntity = mmMerchantService.queryMmMerchantEntityById(2);
        System.out.println(JSON.toJSON(mmMerchantEntity));
    }

    /**
     * 推广短信发送测试
     */
    //@Test
    /*public void testPromotionSend() throws Exception{
        String content = "Want to know when your Good luck will shine on you? Click http://bit.ly/2tJTwug for Daily Horoscope, Fengshui Tips and Chinese Zodiac.";
        boolean isSuccess = true;
        String sendSmUrl = bsnlSouthSendSmUrl + "/ph/"+ "bsnl-west" +"/sendSms";
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("secretKey", secretkey));
        //区分是欢迎短信还是推广短信
        params.add(new BasicNameValuePair("pipe", MessageTypeEnum.TRANSACTIONAL.getCode()));
        params.add(new BasicNameValuePair("phoneNumber", "918275870667"));
        params.add(new BasicNameValuePair("content", Base64Utils.encodeToString(content.getBytes())));
        String response = HttpUtil.postToServer(sendSmUrl, params);
        //LoggerUtils.info(LOGGER, "request and response of sendMt,msisdn:" + msisdn + ",smsCode:" + smsCode + ",content:" + content + ",response:" + response);
        Result result = JSONObject.parseObject(response, Result.class);
        //发送成功 ！
        if (!result.getStatus().equals("200")) {
            isSuccess = false;
        }
        System.out.println(JSON.toJSON(result));
    }*/

    @Test
    public void test001() throws Exception{

        String json = "{\n" +
                "\t'subscription': {\n" +
                "\n" +
                "\t\t'callbackReference': {\n" +
                "\n" +
                "\t\t\t'notifyURL': 'http://10.135.178.84:9088/',\n" +
                "\n" +
                "\t\t\t'callbackData': '123',\n" +
                "\n" +
                "\t\t\t'notificationFormat': 'json'\n" +
                "\n" +
                "\t\t},\n" +
                "\n" +
                "\t\t'destinationAddress': '5840',\n" +
                "\n" +
                "\t\t'criteria': 'order'\n" +
                "\n" +
                "\t}\n" +
                "\n" +
                "}";
        String nonce = UUID.randomUUID().toString();
        String created = DateUtils.format(new Date(), DateUtils.DATE_TIME4_PATTERN);
        String passwordDigstSHA = DigestUtils.sha1Hex(nonce + created + "0lS90z2h");
        String passwordDigst = null;
        try {
            passwordDigst = Base64.encodeBase64String(passwordDigstSHA.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            //LoggerUtils.error(LOGGER, "passwordDigst BASE64加密异常，异常信息：" + e.getMessage());
        }
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost("http://125.60.148.174:8312/1/smsmessaging/inbound/subscriptions");
        try {
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            //httpPost.setHeader("Content-length", "1024");
            httpPost.setHeader("Authorization", "WSSE realm=CDP,profile=UsernameToken");
            httpPost.setHeader("X-WSSE", "UsernameToken Username=008400,PasswordDigest="+passwordDigst+",Nonce="+nonce+",Created="+ created);
            httpPost.setHeader("X-RequestHeader", "request ServiceId=0084002000008781");
            StringEntity data = new StringEntity(json,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                String retStr = EntityUtils.toString(httpEntity, "UTF-8");
                System.out.println(retStr);
                //LoggerUtils.info(LOGGER, "response:" + retStr);
            }
            response.close();
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            //LoggerUtils.error(LOGGER, "exception in doPostSoap1_1", e);
        }
        //return retStr;
    }

    /*@Test
    public void test002() throws Exception{
        RequestSOAPHeader requestSOAPHeader = new RequestSOAPHeader();
        UserID userID = new UserID();
        userID.setID("963852741");
        userID.setType(0);
        SubInfo subInfo = new SubInfo();
        subInfo.setChannelID(111);
        subInfo.setIsAutoExtend(222);
        subInfo.setOperCode("987456");
        subInfo.setProductID("654321");
        String xml = paymentService.unSubscribeProductRequest(requestSOAPHeader, userID, subInfo);
        System.out.println(xml);
    }*/

    @Test
    public void test003() throws Exception{
        //LoggerUtils.info(LOGGER, "泰国CAT续订请求定时任务开始执行");
        //获取当天需要续订的订单集合
        List<MmProductOrderEntity> list = mmProductOrderService.listQueryProductOrder(null, "th");
        for(MmProductOrderEntity mmProductOrderEntity : list){
            //MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode("G201904300937050037");
            ChargeRecurringResp chargeRecurringResp = thPayService.renewSubscribe(mmProductOrderEntity);
            if(chargeRecurringResp.getStatus().intValue() == 0){
                MmProductEntity mmProductEntity = mmProductService.queryProductById(mmProductOrderEntity.getProductId());
                Map map = MapUtil.objectToMap(chargeRecurringResp);
                //创建续订订单
                thOrderService.createIndiaReNewWal(mmProductEntity, new Date(), mmProductOrderEntity.getUserPhone(), chargeRecurringResp.getSerialnumber(), map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.RENEW.getCode());
            }
        }
        //LoggerUtils.info(LOGGER, "泰国CAT续订请求定时任务执行结束");

        //SendSmsResp sendSmsResp = thPayService.sendSmsNotify("190430163457919642211283");
        //System.out.println(JSON.toJSON(sendSmsResp));
    }

    @Test
    public void test004(){
        String timeStamp = DateUtils.format(new Date(), DateUtils.DATE_TIME1_PATTERN);
        String phPassword = DigestUtils.md5Hex("008400" + "0lS90z2h" + timeStamp);
        String str = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:loc='http://www.csapi.org/schema/parlayx/subscribe/manage/v1_0/local'><soapenv:Header>\n" +
                "<tns:RequestSOAPHeader\n" +
                "xmlns:tns='http://www.huawei.com.cn/schema/common/v2_1'>\n" +
                "<tns:spId>008400</tns:spId>\n" +
                "<tns:spPassword>"+phPassword+"</tns:spPassword>\n" +
                "<tns:timeStamp>"+timeStamp+"</tns:timeStamp>\n" +
                "</tns:RequestSOAPHeader>\n" +
                "</soapenv:Header>\n" +
                "<soapenv:Body>\n" +
                "<loc:unSubscribeProductRequest>\n" +
                "<loc:unSubscribeProductReq>\n" +
                "<userID>\n" +
                "<ID>9991832535</ID>\n" +
                "<type>0</type>\n" +
                "</userID>\n" +
                "<subInfo>\n" +
                "<productID>"+productId+"</productID>\n" +
                "<channelID>2</channelID>\n" +
                "<extensionInfo>\n" +
                "<namedParameters>\n" +
                "<key>keyword</key>\n" +
                "<value>unsub</value>\n" +
                "</namedParameters>\n" +
                "</extensionInfo>\n" +
                "</subInfo>\n" +
                "</loc:unSubscribeProductReq>\n" +
                "</loc:unSubscribeProductRequest>\n" +
                "</soapenv:Body>\n" +
                "</soapenv:Envelope>";
        String result = HttpUtil.doPostSoap1_1("http://125.60.148.174:8310/SubscribeManageService/services/SubscribeManage", str,"");
        System.out.println(result);

    }

    @Test
    public void test005(){
        String str = phPayService.inboundSmsSub("");
        System.out.println(str);
    }

    @Test
    public void test006(){
        try {
            String str = phPayService.smsOutBoundSubscribeProductRequest("9991832535", null);
            System.out.println(str);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void test008(){
        String str = phPayService.inboundSmsRetrieveAndDel();
        System.out.println(str);
    }

    @Test
    public void test007(){
        String nonce = "LU69E611YDFCJBNE6TTJV56S8QGAB8";//String.valueOf(RandomUtil.getUpperCode(30, RandomUtil.SecurityCodeLevel.Medium, true));//UUID.randomUUID().toString().replace("-", "");
        System.out.println("nonce: " + nonce);
        String created = "2019-06-03T09:55:23Z";//DateUtils.format(new Date(), DateUtils.DATE_TIME4_PATTERN);
        System.out.println("created: " + created);
        String str = nonce + created + "0lS90z2h";

        String passwordDigst = null;
        try {
            byte[] passwordDigstSHA = DigestUtils.sha1(str.getBytes("utf-8"));
            passwordDigst = Base64.encodeBase64String(passwordDigstSHA);
            System.out.println("passwordDigst: " + passwordDigst);
        } catch (Exception e) {
            //LoggerUtils.error(LOGGER, "passwordDigst BASE64加密异常，异常信息：" + e.getMessage());
        }
    }

    @Test
    public void test009(){
        String str = phPayService.individualInboundSmsCertification("http://125.60.148.174:8312/1/smsmessaging/inbound/subscriptions/10001906040316520001175000","");
        System.out.println(str);
    }


}
