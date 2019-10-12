package io.renren.api.rockmobi.proxy.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * ProxyController.java
 *
 * @author Dexter 2018/11/9
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/proxy")
@Api(tags = "代理网关公共接口")
public class ProxyController {

	private static Logger logger = LoggerFactory.getLogger(ProxyController.class);

	private void printRequest(HttpServletRequest request){
		//header
		Enumeration<?> enum1 = request.getHeaderNames();
		while (enum1.hasMoreElements()) {
			String key = (String) enum1.nextElement();
			String value = request.getHeader(key);
			logger.info("headerKey:{}", key);
			logger.info("headerValue:{}", value);
			System.out.println(key + "\t" + value);
		}

		//body
		String inputLine;
		String str = "";

		try {
			BufferedReader br = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
			while ((inputLine = br.readLine()) != null) {
				str += inputLine;
			}
			br.close();
		} catch (Exception e) {
			System.out.println("IOException: " + e);
		}
		logger.info("str::{}", str);
		System.out.println("str:" + str);

	}

	@RequestMapping(value = "/header/{operator}/info", method = { RequestMethod.GET, RequestMethod.POST })
	@ApiOperation("获取header头中的所有信息，key=value")
	public R headerInfo(@PathVariable("operator") String operator, HttpServletRequest req) {
		printRequest(req);
		Map<String, String> headerinfo = new HashMap<String, String>();
		try {
			//获取所有的消息头名称
			Enumeration<String> headerNames = req.getHeaderNames();
			headerinfo.put("operator", operator);
			String nextElement;

			while (headerNames.hasMoreElements()) {
				nextElement = headerNames.nextElement();
				headerinfo.put(nextElement, req.getHeader(nextElement));
			}
			String clientIp = getIpAddr(req);
			String msisdn = getMsisdn(req);
			String imsi = getImsi(req);
			headerinfo.put("GetIP", clientIp);
			headerinfo.put("GetMsisdn", msisdn == null ? "" : msisdn);
			headerinfo.put("GetImsi", imsi);
			logger.info("Get Headerinfo :{}", headerinfo.toString());
		} catch (Exception e) {
			logger.error("error", e);
		}
		return R.ok().put("headerinfo", headerinfo);
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
			ip = request.getHeader("x-real-ip");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		//如果获取还存在多个IP,则截取第一个使用
//		if(ip.contains(",")) {
//			ip = ip.split(",")[0];
//		}
		
		return ip;
	}

	public static String getMsisdn(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()) {//判断是否还有下一个元素
			String nextElement = headerNames.nextElement();//获取headerNames集合中的请求头
			String header2 = request.getHeader(nextElement);//通过请求头得到请求内容
			logger.info("打印所有的header头信息:"+nextElement + ":" + header2);
		}
		String msisdn = request.getHeader("x-msisdn");
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("msisdn");
		}
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("x-Mobile");
		}
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("header-x_msisdn");
		}
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("x-up-calling-line-id");
		}
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("X-Msisdn");
		}
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = request.getHeader("HTTP_X_MSISDN");
		}
		return msisdn;
	}

	public static String getImsi(HttpServletRequest request) {
		String imsi = request.getHeader("x-imsi");
		if (StringUtils.isEmpty(imsi)) {
			imsi = request.getHeader("imsi");
		}
		return imsi;
	}
}
