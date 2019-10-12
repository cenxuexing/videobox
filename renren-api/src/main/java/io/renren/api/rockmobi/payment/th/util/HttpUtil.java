/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.util;

import io.renren.common.utils.LoggerUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: HttpUtil, v0.1 2019年02月12日 14:39闫迎军(YanYingJun) Exp $
 */
public class HttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

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

    public static String doPostXml(String postUrl, String xml) {
        LoggerUtils.info(LOGGER, "开始进入dopost方法，postUrl:" + postUrl+"&xml:"+xml);
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        LoggerUtils.info(LOGGER, "HttpPost之前");
        HttpPost httpPost = new HttpPost(postUrl);
        LoggerUtils.info(LOGGER, "HttpPost之后");
        try {
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            StringEntity data = new StringEntity(xml,
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
            LoggerUtils.error(LOGGER, "exception in doPostXml", e);
        }
        return retStr;
    }


}
