/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.util;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.RandomUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: HttpUtil, v0.1 2019年02月12日 14:39闫迎军(YanYingJun) Exp $
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

    @Value("${ph.sm.server_id}")
    private static String smsServiceId;

    @Value("${ph.sm.sp_password}")
    private static String smsSpPassword;

    @Value("${ph.sm.partner_id}")
    private static String smspartnerId;
    /**
     * 拼接字符串
     * @return
     */
    public static String CombiningStrings(String url, Map<String,Object> params){
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0) {
                param.append("?");
            } else {
                param.append("&");
            }
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        return apiUrl += param;
    }

    /**
     * 从请求头中获取手机号码
     * @param request
     * @return
     */
    public static String getBsnlMsisdn(HttpServletRequest request){
        //south west
        String msisdn =  request.getHeader("x-msisdn");
        if (StringUtils.isEmpty(msisdn)) {
            msisdn=request.getHeader("msisdn");
        }
        if (StringUtils.isEmpty(msisdn)) {
            msisdn=request.getHeader("x-Mobile");
        }
        if (StringUtils.isEmpty(msisdn)) {
            msisdn=request.getHeader("header-x_msisdn");
        }
        return msisdn;
    }

    /**
     * 获取request用户 IP
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 利用HttpURLConnection对象,我们可以从网络中获取网页数据
     * @param path
     * @return
     * @throws Exception
     */
    public static InputStream getRemoteFileIs(String path) throws Exception {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();//利用HttpURLConnection对象,我们可以从网络中获取网页数据.
        conn.setDoInput(true);
        conn.connect();
        InputStream is = conn.getInputStream();	//得到网络返回的输入流

        return is;
    }

    public static String doGet(String url) {
        String strResult = null;
        try {
            HttpGet httpRequest = new HttpGet(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                strResult = EntityUtils.toString(httpResponse.getEntity());
            } else if (httpResponse.getStatusLine().getStatusCode() == 400) {
                strResult = "error";
            } else if (httpResponse.getStatusLine().getStatusCode() == 500) {
                strResult = "error";
            } else {
                strResult = "error";
            }
            if (httpResponse.getStatusLine().getStatusCode() != 200) {
                HttpEntity entiy = httpResponse.getEntity();
                if (entiy != null) {
                    LoggerUtils.info(LOGGER, "request url: {"+ url +"}, response: {"+ httpResponse.getStatusLine() +"}, {"+ EntityUtils.toString(entiy) +"}");
                } else {
                    LoggerUtils.info(LOGGER, "request url: {"+ url +"}, response: {"+ httpResponse.getStatusLine() +"}");
                }
            }
        } catch (Exception e) {
            strResult = "error";
            LoggerUtils.error(LOGGER, "http doGet error.", e);
        }
        return strResult;
    }

    /**
     * 发送POST请求
     * @param url
     * @param requestData
     * @return
     */
    public static String doPost(String url, String requestData){
        CloseableHttpClient httpClient = null;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            httpClient = httpClientBuilder.build();
            HttpPost httppost = new HttpPost(url);
            HttpEntity re = new StringEntity(requestData, "UTF-8");
            httppost.setHeader("Content-Type","application/soap+xml; charset=utf-8");
            httppost.setEntity(re);
            //调用接口
            HttpResponse response = httpClient.execute(httppost);
            //调用状态
            if(response.getStatusLine().getStatusCode() == 200) {
                String xmlString = EntityUtils.toString(response.getEntity());
                LoggerUtils.info(LOGGER, "HttpClient请求返回的结果：" + xmlString);
                return xmlString;
            }
        } catch (Exception e) {
            LoggerUtils.info(LOGGER, "HttpClient请求返回结果异常,异常信息：" + e.getMessage());
        } finally {
            try {
                //关闭连接
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 使用SOAP1.1发送消息
     *
     * @param postUrl
     * @param soapXml
     * @param soapAction
     * @return
     */
    public static String doPostSoap1_1(String postUrl, String soapXml,
                                       String soapAction) {
        int socketTimeout = 30000;// 请求超时时间
        int connectTimeout = 30000;// 传输超时时间
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        //  设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)
                .setConnectTimeout(connectTimeout).build();
        httpPost.setConfig(requestConfig);
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", soapAction);
            StringEntity data = new StringEntity(soapXml,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                String result = EntityUtils.toString(httpEntity, "UTF-8");
                Map map = XmlUtil.parse(result);
                retStr = JSONObject.toJSONString(map);
                LoggerUtils.info(LOGGER, "response string:" + result);
                LoggerUtils.info(LOGGER, "response xml:" + result);
            }
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "exception in doPostSoap1_1,异常原因：" + e.getMessage());
        }
        return retStr;
    }

    /**
     * 从输入流中读取数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;
    }


    public static String doPostSms(String postUrl, String json) {
        String retStr = "";
        String uuid = UUID.randomUUID().toString();
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        try {
            httpPost.setHeader("Authorization", "WSSE realm=CDP,profile=UsernameToken");
            httpPost.setHeader("X-WSSE", "UsernameToken Username=00"+smspartnerId+",PasswordDigest="+smsSpPassword+",Nonce="+uuid+",Created="+ DateUtils.format(new Date(), DateUtils.DATE_TIME4_PATTERN));
            httpPost.setHeader("X-RequestHeader", "request ServiceId=00"+smsServiceId+",ProductId");
            //httpPost.setHeader("Content-length", );

            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            StringEntity data = new StringEntity(json,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                LoggerUtils.info(LOGGER, "response:" + retStr);
            }
            response.close();
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "exception in doPostSoap1_1", e);
        }
        return retStr;
    }

    /**
     * 菲律宾短信订阅发送请求
     * @param postUrl
     * @param json
     * @param spPassword
     * @param smsServiceId
     * @param productId
     * @return
     */
    public static String doPostSmsSub(String postUrl, String json, String spPassword, String smsServiceId, String productId, String type, String phoneNo) {
        String retStr = "";
        String nonce = String.valueOf(RandomUtil.getUpperCode(30, RandomUtil.SecurityCodeLevel.Medium, true));
        String created = DateUtils.format(new Date(), DateUtils.DATE_TIME4_PATTERN);
        String passwordDigst = null;
        try {
            String passwordSHA = nonce + created + spPassword;
            byte[] passwordDigstSHA = DigestUtils.sha1(passwordSHA.getBytes("utf-8"));
            passwordDigst = Base64.encodeBase64String(passwordDigstSHA);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "passwordDigst BASE64加密异常，异常信息：" + e.getMessage());
        }
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpPost httpPost = new HttpPost(postUrl);
        try {
            httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            //httpPost.setHeader("Content-length", String.valueOf(json.length()));
            //httpPost.setHeader("Host", "168.63.246.122:80");
            httpPost.setHeader("Authorization", "WSSE realm=\"CDP\",profile=\"UsernameToken\"");
            httpPost.setHeader("X-WSSE", "UsernameToken Username=\"006409\",PasswordDigest=\""+passwordDigst+"\",Nonce=\""+nonce+"\",Created=\""+created+"\"" );
            if(type.equals("inbound")){
                httpPost.setHeader("X-RequestHeader", "request ServiceId=\""+smsServiceId+"\"");//\"++ \"");
            }else{
                httpPost.setHeader("X-RequestHeader", "request ProductId=\""+ productId +"\",ServiceId=\""+smsServiceId+"\",FA=\""+"tel:"+phoneNo+"\"");
            }

            StringEntity data = new StringEntity(json,
                    Charset.forName("UTF-8"));
            httpPost.setEntity(data);
            CloseableHttpResponse response = closeableHttpClient
                    .execute(httpPost);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                // 打印响应内容
                retStr = EntityUtils.toString(httpEntity, "UTF-8");
                LoggerUtils.info(LOGGER, "response:" + retStr);
            }
            response.close();
            // 释放资源
            closeableHttpClient.close();
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "exception in doPostSoap1_1", e);
        }
        return retStr;
    }

    public static String doDelete(String type, String uri, String smsServiceId, String productId,String smsSpPassword, String phoneNo)  {
        String retStr = "";
        String nonce = String.valueOf(RandomUtil.getUpperCode(30, RandomUtil.SecurityCodeLevel.Medium, true));
        String created = DateUtils.format(new Date(), DateUtils.DATE_TIME4_PATTERN);
        String passwordDigst = null;
        try {
            String passwordSHA = nonce + created + smsSpPassword;
            byte[] passwordDigstSHA = DigestUtils.sha1(passwordSHA.getBytes("utf-8"));
            passwordDigst = Base64.encodeBase64String(passwordDigstSHA);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "passwordDigst BASE64加密异常，异常信息：" + e.getMessage());
        }
        CloseableHttpClient client = null;
        HttpDelete httpDelete = null;
        String result = null;
        try {
            // 创建HttpClientBuilder
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            // HttpClient
            client = httpClientBuilder.build();
            httpDelete = new HttpDelete(uri);

            httpDelete.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpDelete.setHeader("Authorization", "WSSE realm=\"CDP\",profile=\"UsernameToken\"");
            httpDelete.setHeader("X-WSSE", "UsernameToken Username=\"006409\",PasswordDigest=\""+passwordDigst+"\",Nonce=\""+nonce+"\",Created=\""+created+"\"" );
            if(type.equals("inbound")){
                httpDelete.setHeader("X-RequestHeader", "request ServiceId=\""+smsServiceId+"\"");//\"++ \"");
            }else{
                httpDelete.setHeader("X-RequestHeader", "request ProductId=\""+ productId +"\",ServiceId=\""+smsServiceId+"\",FA=\""+"tel:"+phoneNo+"\"");
            }

            CloseableHttpResponse response = client.execute(httpDelete);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity);

            if (200 == response.getStatusLine().getStatusCode()) {
                LoggerUtils.info(LOGGER, "远程调用成功.msg={" + result + "}");
            }
        } catch (Exception e) {
            LOGGER.error("远程调用失败,errorMsg={}", e.getMessage());
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 描述:获取 post 请求的 byte[] 数组
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if(contentLength<0){
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength;) {

            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }

    /**
     * 描述:获取 post 请求内容
     * <pre>
     * 举例：
     * </pre>
     * @param request
     * @return
     * @throws IOException
     */
    public static String getRequestPostStr(HttpServletRequest request)
            throws IOException {
        byte buffer[] = getRequestPostBytes(request);
        String charEncoding = request.getCharacterEncoding();
        if (charEncoding == null) {
            charEncoding = "UTF-8";
        }
        return new String(buffer, charEncoding);
    }
}
