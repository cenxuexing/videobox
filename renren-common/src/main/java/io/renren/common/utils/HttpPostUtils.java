package io.renren.common.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpPostUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpPostUtils.class);

	static String encode = "UTF-8";

	public static String doHttpPost(String url, String reqJson) {
		logger.info("do http post json - url : [{}] - reqJson :  [{}]", url, reqJson);
		StringBuffer respJson = new StringBuffer("");
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(reqJson);
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			while ((lines = reader.readLine()) != null) {
				//				lines = URLDecoder.decode(lines, encode);
				respJson.append(lines);
			}
			reader.close();
			// 断开连接
			connection.disconnect();
			logger.info("do http post json - respJson :  {}", respJson.toString());
		} catch (MalformedURLException e) {
			logger.error("url {} MalformedURLException ", url, e);
		} catch (UnsupportedEncodingException e) {
			logger.error("url {} UnsupportedEncodingException ", url, e);
		} catch (IOException e) {
			logger.error("url {} IOException ", url, e);
		} catch (Exception e) {
			logger.error("url {} Exception ", url, e);
		}
		return respJson.toString();
	}

}
