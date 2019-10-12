package io.renren.util;

public interface Request {
		public String sendPost(String url, String param) throws Exception;
		public String sendGet(String url, String param) throws Exception;
}
