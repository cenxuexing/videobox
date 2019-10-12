package io.renren.sms.utils;

import io.renren.common.utils.LoggerUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtil.class);

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
//			if ("error".equals(strResult)) {
			if (httpResponse.getStatusLine().getStatusCode() != 200) {
				HttpEntity entiy = httpResponse.getEntity();
				if (entiy != null) {
					LOGGER.info("request url: {}, response: {}, {}", url, httpResponse.getStatusLine(), EntityUtils.toString(entiy));
				} else {
					LOGGER.info("request url: {}, response: {}", url, httpResponse.getStatusLine());
				}
			}
		} catch (Exception e) {
			strResult = "error";
			LOGGER.error("http doGet error.", e);
		}
		return strResult;
	}

	public static String doGetForURLConnection(String url) {
		HttpURLConnection connection = null;
		String strResult = null;
		try {
			URL urlads = new URL(url);
			connection = (HttpURLConnection) urlads.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				strResult = IOUtils.toString(connection.getInputStream(), "UTF-8");
			}
		} catch (Exception e) {
			strResult = "error";
			LOGGER.error("http doGetForURLConnection error.", e);
		}
		return strResult;
	}


	
	
	/**
	 * 把数据post到服务器，获取返回的结果
	 * 
	 * @param uriAPI
	 *            ： API 所在的路径
	 * @param params
	 *            ： 传递的数据
	 */
	public static String doHttpPost(String uriAPI, String params) {
		/* 建立HTTPost对象 */
		HttpPost httpPost = new HttpPost(uriAPI);
		try {
			/* 添加请求参数到请求对象 */
			if (params != null) {
				httpPost.setEntity(new StringEntity(params, ContentType.create("application/json", "UTF-8")));
			}
			/* 发送请求并等待响应 */
			CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			/* 若状态码为200 ok */
			LOGGER.debug("返回码：ResponseCode="+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			} else {
				return "Error Response: " + httpResponse.getStatusLine().toString();
			}
		} catch (Exception e) {
			return "post failure :caused by-->" + e.getMessage().toString();
			
		}

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

	public static String doPostForInfoBip(String url, String mtContent) {
		try {
			String result = doPostInfoBipUtil(url,mtContent);
			return result;
		} catch (Exception e) {
			try {
				Thread.sleep(500);
				return doPostInfoBipUtil(url,mtContent);
			} catch (Exception e1) {
				return "post failure :caused by-->" + e.getMessage();
			}
		}
	}
	
	public static String doPostInfoBipUtil(String url, String mtContent) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(mtContent);
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "application/json");
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			return "Error Response: " + response.getStatusLine().toString();
		}
	}

	
	public static String doPostByJson(String url, String jsonStr, String auth) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, "UTF-8");
		httpPost.setEntity(entity);
		httpPost.setHeader("Content-Type", "application/json");
		httpPost.setHeader("Authorization", "Basic " + auth);
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			return "error";
		}
	}
	
	public static String doPostByJson(String url, String jsonStr, Map<String, String> header) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		StringEntity entity = new StringEntity(jsonStr, "UTF-8");
		httpPost.setEntity(entity);
		for (String key : header.keySet()) {
			httpPost.setHeader(key, header.get(key));
		}
		HttpResponse response = httpClient.execute(httpPost);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			return "error";
		}
	}
	
	public static String doPutByJson(String url, String jsonStr, Map<String, String> header) throws Exception{
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		StringEntity entity = new StringEntity(jsonStr, "UTF-8");
		httpPut.setEntity(entity);
		for (String key : header.keySet()) {
			httpPut.setHeader(key, header.get(key));
		}
		HttpResponse response = httpClient.execute(httpPut);
		if (response.getStatusLine().getStatusCode() == 200) {
			return EntityUtils.toString(response.getEntity());
		} else {
			return "error";
		}
	}
	
	/**
	 * URL参数编码
	 * 
	 * @param str
	 * @return
	 */
	public static String urlEncoder(String str) {
		if (str == null || "".equals(str)) {
			return str;
		}
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return str;
		}
	}
	 
	/**
	 * 下载文件
	 * 
	 * @param url
	 * @param localFile
	 * @throws Exception
	 */
	public static boolean download(String url, String localFile) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				return false;
			}
			HttpEntity entity = response.getEntity();
			bis = new BufferedInputStream(entity.getContent());
			File f = new File(localFile);
			if (!f.exists())
				f.createNewFile();
			bos =  new BufferedOutputStream(new FileOutputStream(f));
			byte[] buf = new byte[1024];
			int len;
			while ((len = bis.read(buf)) > 0) {
				bos.write(buf, 0, len);
			}
			bos.flush();
				
			return true;
		} catch (Exception e) {
			LOGGER.error("download file error.", e);
			return false;
		} finally {
			try {
				if (bos != null) bos.close();
				if (bis != null) bis.close();
			} catch (IOException e) {
				LOGGER.error("downloadDrFile close stream error.", e);
			}
		}
	}
	
	/**
	 * 发送head 请求
	 * @param url
	 * @param headers
	 * @return
	 */
	public static Map<String, String> doHeadRequest(String url, Map<String, String> headers){
		Map<String, String> result = new HashMap<String, String>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpHead httpHead = new HttpHead(url);
		for (String key : headers.keySet()) {
			httpHead.addHeader(key, headers.get(key));
		}
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpHead);
			int status = response.getStatusLine().getStatusCode();
			if (status == 200) {
				result.put("status", "success");
				if (response.getFirstHeader("x-response-status") != null) {
					result.put("x-response-status", response.getFirstHeader("x-response-status").getValue());
				}
				if (response.getFirstHeader("x-response-messageid") != null) {
					result.put("x-response-messageid", response.getFirstHeader("x-response-messageid").getValue());	
				}
				if (response.getFirstHeader("x-response-sessionid") != null) {
					result.put("x-response-sessionid", response.getFirstHeader("x-response-sessionid").getValue());
				}
			}else if(status == 400){
				result.put("status", "fail");
				if (response.getFirstHeader("x-response-status") != null) {
					result.put("x-response-status", response.getFirstHeader("x-response-status").getValue());
				}
				if (response.getFirstHeader("x-response-errorcode") != null) {
					result.put("x-response-errorcode", response.getFirstHeader("x-response-errorcode").getValue());
				}
			}
		} catch (Exception e) {
			LOGGER.error("doHeadRequest occur exception!",e);
		}
		return result;
	}

	/**
	 * 把数据post到服务器，获取返回的结果
	 *
	 * @param uriAPI
	 *            ： API 所在的路径
	 * @param params
	 *            ： 传递的数据
	 */
	public static String postToServer(String uriAPI, List<NameValuePair> params) {
		/* 建立HTTPost对象 */
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpRequest = new HttpPost(uriAPI);
		try {
			/* 添加请求参数到请求对象 */
			if (params != null) {
				httpRequest.setEntity(new UrlEncodedFormEntity(params, Charset.forName("UTF-8")));
			}
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = httpClient.execute(httpRequest);
			/* 若状态码为200 ok */
			LoggerUtils.info(LOGGER, "返回码：ResponseCode="+httpResponse.getStatusLine().getStatusCode());
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				return EntityUtils.toString(httpResponse.getEntity());

			} else {
				return "Error Response: " + httpResponse.getStatusLine().toString();
			}
		} catch (Exception e) {
			return "post failure :caused by-->" + e.getMessage().toString();

		}
	}

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
	
}
