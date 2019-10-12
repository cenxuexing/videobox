package io.renren.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

public class HttpRequestUtil {
	private final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	@Value("${appKey}")
	private static String appKey;

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "";
			System.out.println("发送GET请求出现异常！" + e);
			logger.error(e.getMessage(), e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public String sendPost(String url, String param) throws Exception {

		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			//2017年8月20日maamin添加
			conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			/*in = new BufferedReader(
			        new InputStreamReader(conn.getInputStream()));*/
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "";
			logger.error(e.getMessage(), e);
			throw new Exception("请求失败");

		}
		//使用finally块来关闭输出流、输入流
		finally {

			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}

		}
		return result;
	}

	public static String sendPostHeaders(String url, String param, String time, String sign, String appKey) throws Exception {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("FZM-Ca-Timestamp", time);
			conn.setRequestProperty("FZM-Ca-AppKey", appKey);
			//            conn.setRequestProperty("FZM-Ca-AppIp","127.0.0.1");
			conn.setRequestProperty("FZM-Ca-OS", "web");
			conn.setRequestProperty("FZM-Ca-Signature", sign);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(20000);
			conn.setReadTimeout(20000);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "";
			logger.error(e.getMessage(), e);
			throw new Exception("请求失败");

		}
		//使用finally块来关闭输出流、输入流
		finally {

			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}

		}
		return result;
	}

	/**
	 * 传输文件方法
	 * @throws Exception 
	 */
	public static String sendFile(String url, Map<String, String> map, MultipartFile file) throws Exception {

		String resultmessage = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String twoHyphens = "--";
		final String NEWLINE = "\r\n"; // 换行，或者说是回车
		DataOutputStream dos = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try {
			URL realUrl = new URL(url);
			URLConnection conn1 = realUrl.openConnection();
			HttpURLConnection conn = (HttpURLConnection) conn1;
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY);
			conn.setConnectTimeout(600000);
			conn.setReadTimeout(600000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			dos = new DataOutputStream(conn.getOutputStream());
			// 获取表单中上传控件之外的控件数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
			if (map != null && !map.isEmpty()) {
				for (Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String, String> entry = iterator.next();
					dos.writeBytes(twoHyphens + BOUNDARY); // 像请求体中写分割线，分界线+换行
					dos.writeBytes(NEWLINE + "Content-Disposition: form-data; " + " name=\"" + entry.getKey() + "\"" + NEWLINE); // 拼接参数名，格式就是Content-Disposition:
					// form-data;
					// name="key"
					// 其中key就是当前循环的键值对的键，别忘了最后的换行
					dos.writeBytes(NEWLINE); // 空行，一定不能少，键和值之间有一个固定的空行
					dos.write(entry.getValue().getBytes("UTF-8")); // 将值写入
					// 或者写成：dos.write(value.toString().getBytes(charset));
					dos.writeBytes(NEWLINE); // 换行
				}
			}
			if (null != file) {
				// 获取表单中上传附件的数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
				dos.writeBytes(twoHyphens + BOUNDARY + NEWLINE);// 像请求体中写分割线，就是分界线+换行
				dos.write(("Content-Disposition: form-data; " + " name=\"" + "file" + "\"" + "; filename=\"" + file.getOriginalFilename() + "\"" + NEWLINE).getBytes());
				dos.writeBytes("Content-Type:" + file.getContentType() + NEWLINE);
				// 换行，重要！！不要忘了
				dos.writeBytes(NEWLINE);
				dos.write(file.getBytes()); // 上传文件的内容
				dos.writeBytes(NEWLINE); // 最后换行
			}
			dos.writeBytes(twoHyphens + BOUNDARY + twoHyphens + NEWLINE); // 最后的分割线，与前面的有点不一样是前缀+分界线+前缀+换行，最后多了一个前缀
			dos.flush();

			input = conn.getInputStream();

			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int num = input.read(buffer); num != -1; num = input.read(buffer)) {
				baos.write(buffer, 0, num);
				//				System.out.println(baos.toString());
			}
			baos.flush();
			resultmessage = baos.toString("UTF-8");
			System.out.println("----------------------------------------------" + resultmessage);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception();
		} finally {
			if (baos != null) {
				baos.close();
			}

			if (dos != null) {
				dos.close();
			}

			if (input != null) {
				input.close();
			}

		}
		return resultmessage;
	}

	/**
	 * 传输文件方法
	 * @throws Exception 
	 */
	public String sendFile(String url, Map<String, String> map, MultipartFile[] files) throws Exception {
		BufferedReader in = null;
		String resultmessage = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String twoHyphens = "--";
		final String NEWLINE = "\r\n"; // 换行，或者说是回车
		DataOutputStream dos = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		try {
			URL realUrl = new URL(url);
			URLConnection conn1 = realUrl.openConnection();
			HttpURLConnection conn = (HttpURLConnection) conn1;
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + BOUNDARY + ";charset=UTF-8");
			conn.setConnectTimeout(600000);
			conn.setReadTimeout(600000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			dos = new DataOutputStream(conn.getOutputStream());
			// 获取表单中上传控件之外的控件数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					dos.writeBytes(twoHyphens + BOUNDARY); // 像请求体中写分割线，分界线+换行
					dos.writeBytes(NEWLINE + "Content-Disposition: form-data; " + " name=\"" + entry.getKey() + "\"" + NEWLINE); // 拼接参数名，格式就是Content-Disposition:
																																	// form-data;
																																	// name="key"
																																	// 其中key就是当前循环的键值对的键，别忘了最后的换行
					dos.writeBytes(NEWLINE); // 空行，一定不能少，键和值之间有一个固定的空行
					dos.write(entry.getValue().getBytes("UTF-8")); // 将值写入
					// 或者写成：dos.write(value.toString().getBytes(charset));
					dos.writeBytes(NEWLINE); // 换行
				} // 所有循环完毕，就把所有的键值对都写入了
			}
			// 获取表单中上传附件的数据，写入到输出流对象（根据上面分析的抓包的内容格式拼凑字符串）；
			for (MultipartFile file : files) {
				dos.writeBytes(twoHyphens + BOUNDARY + NEWLINE);// 像请求体中写分割线，就是分界线+换行
				dos.write(("Content-Disposition: form-data; " + " name=\"" + "files" + "\"" + "; filename=\"" + file.getOriginalFilename() + "\"" + "; filename*=utf-8" + NEWLINE).toString().getBytes());
				dos.writeBytes("Content-Type:" + file.getContentType() + NEWLINE);
				// 换行，重要！！不要忘了
				dos.writeBytes(NEWLINE);
				dos.write(file.getBytes()); // 上传文件的内容
				dos.writeBytes(NEWLINE); // 最后换行
			}
			dos.writeBytes(twoHyphens + BOUNDARY + twoHyphens + NEWLINE); // 最后的分割线，与前面的有点不一样是前缀+分界线+前缀+换行，最后多了一个前缀
			dos.flush();

			input = conn.getInputStream();
			//			baos = new ByteArrayOutputStream();
			//			byte[] buffer = new byte[1024];
			//			for (int num = input.read(buffer); num != -1; num = input.read(buffer)) {
			//				baos.write(buffer, 0, num );
			//				System.out.println(baos.toString());
			//			}
			//			baos.flush();
			//			String result = new String( baos.toByteArray());
			//			byte[] lens = baos.toByteArray(); 

			//			resultmessage = new String(lens,"utf-8");//
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				resultmessage += line;
			}
			System.out.println("----------------------------------------------" + resultmessage);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception();
		} finally {
			if (baos != null) {
				baos.close();
			}
			if (dos != null) {
				dos.close();
			}
			if (input != null) {
				input.close();
			}

		}
		return resultmessage;
	}

	/**
	 * 邓群Action方法中分离出来的
	 */
	public String sendPost(String url, String contentType, String param) throws IOException {
		String resultmessage = null;
		PrintWriter out = null;
		InputStream input = null;
		ByteArrayOutputStream baos = null;
		System.out.println("post请求的url:" + url);
		try {
			resultmessage = "";
			URL realUrl = new URL(url);
			URLConnection conn1 = realUrl.openConnection();
			HttpURLConnection conn = (HttpURLConnection) conn1;
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			if (contentType != null && !"".equals(contentType)) {
				conn.setRequestProperty("Content-Type", contentType);
			}

			conn.setRequestProperty("Accept-Charset", "utf-8");

			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(60000);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);

			out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();

			input = conn.getInputStream();
			//			String line;
			baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			for (int num = input.read(buffer); num != -1; num = input.read(buffer)) {
				baos.write(buffer, 0, num);
				// 						System.out.println(baos.toString());
			}
			resultmessage = baos.toString("UTF-8");
			baos.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		} finally {
			if (out != null) {
				out.close();
			}
			if (input != null) {
				input.close();
			}

		}

		System.out.println(url + "----" + resultmessage/*new String(resultmessage.getBytes("UTF-8"))*/);
		// 		         return new String(resultmessage.getBytes("UTF-8"));
		return resultmessage;

	}

	/**
	 * 邓群Action方法中分离出来的
	 */
	public String sendGet(String url, String contentType, String param) throws Exception {
		String result = "";
		BufferedReader in = null;

		System.out.println("get请求的url:" + url);
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type", contentType);
			connection.connect();
			Map<String, List<String>> map = connection.getHeaderFields();
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			result = "";
			System.out.println("exception message:___>" + e);
			throw e;

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				throw e2;
			}
		}

		return result;
	}

	public static byte[] strToByteArray(String str) {
		if (str == null) {
			return null;
		}
		byte[] byteArray = str.getBytes();
		return byteArray;
	}

}