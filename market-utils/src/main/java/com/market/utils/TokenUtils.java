package com.market.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
import java.util.zip.GZIPInputStream;

public class TokenUtils {
	
	private static final String ADMIN_TOKEN_PRE = "ADMIN_TOKEN";
	
	private static final String DEFAULT_IPADDR = "localhost";
	
	private static final String WEBSITE = "http://www.market.com";
	
	public static String createAppId(String adminId,Date lastLoginTime,String ipAddr) {
		String uuid = UUID.randomUUID().toString().replaceAll("-","").trim();
		StringBuffer sb = new StringBuffer(ADMIN_TOKEN_PRE);
		sb.append(uuid);
		sb.append(WEBSITE);
		if(CheckUtil.isBlank(ipAddr)) {
			ipAddr = DEFAULT_IPADDR;
		}
		String str = sb.append(ipAddr).toString();
		byte[] bytes = null;
		try {
			str = URLEncoder.encode(str,"UTF-8");
			
//			bytes = str.getBytes("GBK");
//			bytes = new String(bytes,"GBK").getBytes("UTF-8");
//			str = new String(bytes,"ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static void main(String[] args) {
		String appid = createAppId("admin",new Date(), null);
		System.out.println(appid);
	}
}
