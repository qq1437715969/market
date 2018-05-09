package com.market.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtils {
	
	private static final String ADMIN_TOKEN_PRE = "ADMIN_TOKEN";
	
	private static final String DEFAULT_IPADDR = "localhost";
	
	private static final String WEBSITE = "http://www.market.com";
	
	private static final String DEFAULT_SECRET_METHOD = "MD5";
	
	private static final String DEFAULT_SALT = "marketAdmin";
	
	private static final Integer DEFAULT_SECRET_TIMES = 5;
	
	private static final Integer DEFAULT_TOKEN_TIMES = 2;
	
	public static final String RANDOM = "random";
	
	public static final String APPID = "appid";
	
	/**
	 * 
	 * @param adminId
	 * @param lastLoginTime
	 * @param ipAddr
	 * @return 
	 * Map
	 * random , 生成appId时的随机数
	 * appid , 生成的appid
	 */
	public static Map<String, String> createAppId(String adminId,Date lastLoginTime,String ipAddr) {
		String uuid = UUID.randomUUID().toString().replaceAll("-","").trim();
		StringBuffer sb = new StringBuffer(ADMIN_TOKEN_PRE);
		sb.append(uuid);
		sb.append(WEBSITE);
		if(CheckUtil.isBlank(ipAddr)) {
			ipAddr = DEFAULT_IPADDR;
		}
		Map<String,String> map = new HashMap<String,String>();
		map.put(RANDOM,uuid);
		map.put(APPID,Md5Utils.encryptString(sb.append(ipAddr).toString(),DEFAULT_SALT));
		return map;
	}
	
	public final static String getAppId(String adminId,Date lastLoginTime,String ipAddr,String random) {
		StringBuffer sb = new StringBuffer(ADMIN_TOKEN_PRE);
		sb.append(random);
		sb.append(WEBSITE);
		if(CheckUtil.isBlank(ipAddr)) {
			ipAddr = DEFAULT_IPADDR;
		}
		return Md5Utils.encryptString(sb.append(ipAddr).toString(),DEFAULT_SALT);
	}
	
	public static String createToken(String appId,String random) {
		return Md5Utils.encryptString(appId,random,DEFAULT_TOKEN_TIMES);
	}
	
	public static boolean checkToken(String appId,String random,String token) {
		String newToken = createToken(appId, random);
		return token.equals(newToken)?true:false;
	}
	
}
