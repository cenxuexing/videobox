package io.renren.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.model.IspResponse;
import com.maxmind.geoip2.record.Country;


public class CountryLookup {
	private static Logger logger =LoggerFactory.getLogger(CountryLookup.class);
	private static final String ctypath="geo/GeoIP2-Country.mmdb";
	private static final String isppath="geo/GeoIP2-ISP.mmdb";
	
	private static DatabaseReader readerCty=null;
	private static DatabaseReader readerIsp=null;
	
	private static DatabaseReader getCtyDatabaseReader(){
		if(readerCty==null){
			try {
				 InputStream inputStream = CountryLookup.class.getClassLoader().getResourceAsStream(ctypath);
				 readerCty = new DatabaseReader.Builder(inputStream).build();
			} catch (Exception e) {
				logger.error(e.toString());
			}
		}
		return readerCty;
	}
	
	private static DatabaseReader getIspDatabaseReader(){
		if(readerIsp==null){
			try {
				 InputStream inputStream = CountryLookup.class.getClassLoader().getResourceAsStream(isppath);
				readerIsp = new DatabaseReader.Builder(inputStream).build();
			} catch (Exception e) {
				logger.error(e.toString());
			}
		} 
		return readerIsp;
	}
	
	public static Country getCountry(String ip){
		try {
			DatabaseReader reader = CountryLookup.getCtyDatabaseReader();
			InetAddress ipAddress = InetAddress.getByName(ip);
			CountryResponse response = reader.country(ipAddress);
			Country country = response.getCountry();
			return country;
		} catch (Exception e) {
			logger.error(e.toString());
		} 
		return null;
	}
	
	public static String getIsp(String ip){
		try {
			 
			DatabaseReader reader = CountryLookup.getIspDatabaseReader();
			InetAddress ipAddress = InetAddress.getByName(ip);
			IspResponse response = reader.isp(ipAddress);
			return response.getIsp();
		} catch (Exception e) {
			logger.error(e.toString());
		} 
		return "";
	}
	
	public static String getOrganization(String ip){
		try {
			
			DatabaseReader reader = CountryLookup.getIspDatabaseReader();
			InetAddress ipAddress = InetAddress.getByName(ip);
			IspResponse response = reader.isp(ipAddress);
			
			return response.getOrganization();
		} catch (Exception e) {
			logger.error(e.toString());
		} 
		return "";
	}
	
 
	public static void main(String[] args) throws IOException, GeoIp2Exception {
		//119.137.53.127 [China Telecom Guangdong] [CN],[中国]
		//203.144.67.79 [Cambodian ISP, Country Wide, Wireless IAP],[KH],[柬埔寨]
		//203.144.88.35 [Cambodian ISP, Country Wide, Wireless IAP],[KH],[柬埔寨]
		//110.74.199.103 [EZECOM limited],[KH],[柬埔寨]
		System.out.println(CountryLookup.getIsp("203.144.67.79"));
		System.out.println(CountryLookup.getOrganization("203.144.67.79"));
		System.out.println(CountryLookup.getCountry("203.144.67.79").getIsoCode());
		System.out.println(CountryLookup.getCountry("203.144.67.79").getNames().get("zh-CN")); 
		System.out.println("---------------------------------------");
		System.out.println(CountryLookup.getIsp("110.74.199.103"));
		System.out.println(CountryLookup.getOrganization("110.74.199.103"));
		System.out.println(CountryLookup.getCountry("110.74.199.103").getIsoCode());
		System.out.println(CountryLookup.getCountry("110.74.199.103").getNames().get("zh-CN")); 
		
		if("Royal TeleCam Ltd. O.B.O: Telesurf ISP".equals(CountryLookup.getOrganization("203.144.67.79"))) {
			System.err.println("ok");
		}
	
	
	}
}
