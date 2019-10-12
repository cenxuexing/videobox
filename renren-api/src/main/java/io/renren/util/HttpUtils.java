package io.renren.util;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


public class HttpUtils {

	public static final String Cellcard_PrivateKey = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwiZXhwIjoiMTYwNjc1MjA2MCIsIm5iZiI6IjE1NTU0OTI2OTIiLCJpYXQiOjE1NTU0OTI2OTJ9.UUObzAb7KtJ6aWn8wdoMP5-k3LgL-Pfol79b-ZTANdhrOhDvi1OmGTAcfx8qKFriUKPJild-MlsF6l6j1EWQ7e6OLAOPetISQlauNGWrQ0t9VV2H41AWnosipEH3GAuNgkIKIam7QpxYjMBfmqY1p_9bjiwFF8T9LvUlDKUrCAGvmD2956ZgfTQ2d65cJ4yR2c10Cbt5VhN2gUKboa4ZlRp1lwYr2KpIJRxY95RWiZvewdzXmkhUBxfADRIBcio2ozOCF8UPvp28YgTj1w7Od4ApNeHWrQ2RBIPo3YoqDnsj1IzOCozzLZo-zlF8TjQIbfB2IxdyNmCGDtQtLkJHp-3CKIUOeFyDv2uvHWmntEcs15KQoCUltTvFYmo2WpptxtIROvrp3A0o4OzJgaJksdM4Q967D6UWYe5YhGQ2xnmJyXq0WtpmeLkilxjeVzn1qbJfaukx67Q_wmuFS0WoSYzUk4FhoQOeXP7vU1VdndgrKFgMAZVLC-wVLC0w9m_IROVAXczKT-DhdbWYT-tSmWcQubHF7it_6s0ND-nSMVXxpX-Gl7x43tj8zxWIU6ySGgnNoNqWzZKVCmYjDheEHRdbTHbY_6eoUpWmtQOZcvJMnrg2Sf7ZDe-CvYXAk4YaVwWtXF8Xnr4HUt5zajPYC-p94MIQht0vjp4Szjmzdfk";

	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	/**
	 * 发送POST请求
	 *
	 * @param url
	 * @param requestData
	 * @return
	 */
	public static String doPost(String url, String requestData) {
		CloseableHttpClient httpClient = null;
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			httpClient = httpClientBuilder.build();
			HttpPost httppost = new HttpPost(url);
			HttpEntity re = new StringEntity(requestData, "UTF-8");
			httppost.setHeader("Content-Type", "application/json; charset=utf-8");
			httppost.setHeader("Authorization", "Bearer " + Cellcard_PrivateKey);
			httppost.setEntity(re);
			//调用接口
			HttpResponse response = httpClient.execute(httppost);
			//调用状态
			if (response.getStatusLine().getStatusCode() == 200) {
				String resp = EntityUtils.toString(response.getEntity());
				logger.info("HttpClient post request return result：" + resp);
				return resp;
			} else {
				logger.info("HttpClient请求返回的调用状态：" + response.getStatusLine());
				return null;
			}
		} catch (Exception e) {
			logger.info("HttpClient请求返回结果异常", e);
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
					logger.info("request url: {}, response: {}, {}", url, httpResponse.getStatusLine(), EntityUtils.toString(entiy));
				} else {
					logger.info("request url: {}, response: {}", url, httpResponse.getStatusLine());
				}
			}
		} catch (Exception e) {
			strResult = "error";
			logger.error("http doGet error.", e);
		}
		return strResult;
	}


	/**
	 * 把数据post到服务器，获取返回的结果
	 *
	 * @param uriAPI ： API 所在的路径
	 * @param params ： 传递的数据
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
			logger.info("返回码：ResponseCode= {} ", httpResponse.getStatusLine().getStatusCode());
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
	 * 发送httpput请求
	 *
	 * @param url
	 * @param stringJson
	 * @return
	 */
	public static String httpPutRaw(String url, String stringJson) {

		CloseableHttpClient httpClient = null;
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			httpClient = httpClientBuilder.build();
			HttpPut httpPut = new HttpPut(url);
			HttpEntity re = new StringEntity(stringJson, "UTF-8");
			httpPut.setHeader("Content-Type", "application/json; charset=utf-8");
			httpPut.setHeader("Authorization", "Bearer " + Cellcard_PrivateKey);
			httpPut.setEntity(re);
			//调用接口
			HttpResponse response = httpClient.execute(httpPut);

			logger.info("HttpClient put HttpResponse parameters：" + JSON.toJSONString(response));
			//调用状态
			if (response.getStatusLine().getStatusCode() == 200) {
				String resp = EntityUtils.toString(response.getEntity());
				logger.info("HttpClient put request return result：" + resp);
				return resp;
			} else {
				logger.info("HttpClient put 请求返回的调用状态：" + response.getStatusLine());
				return null;
			}
		} catch (Exception e) {
			logger.info("HttpClient put 请求返回结果异常", e);
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

	private static String ENCODING = "UTF-8";

	private static CloseableHttpClient httpClient = HttpClients.createDefault();

	public static String doPostLDS(String url, String paramJson) {
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeader("Content-type", "application/json");
			StringEntity reqEntity = new StringEntity(paramJson, Charset.forName(ENCODING));
			reqEntity.setContentEncoding("UTF-8");
			reqEntity.setContentType("application/json");
			httpPost.setEntity(reqEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			String result = EntityUtils.toString(entity);
			logger.info("url:{},param:{}response:{}", url, paramJson, result);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				logger.error("httpPost接口调用失败url:{},params:{},response{},code:{}", url, paramJson, statusCode, result, statusCode);
			}
			return result;
		} catch (Exception e) {
			logger.error("httpPost接口调用异常url{},params:{}", url, paramJson, e);
			return "error";
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					logger.error("关闭response失败", e);
				}
			}
		}
	}
}
