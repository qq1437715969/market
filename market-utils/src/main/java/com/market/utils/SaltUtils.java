package com.market.utils;

import java.util.UUID;

public class SaltUtils {

	public static final String createSecSalt(String str,String salt) {
	 	return Md5Utils.encryptString(str, salt);
	}
	
	public static final String createSecSaltNum(String str,String salt,int num) {
		if(num==0||num==1) {
			return Md5Utils.encryptString(str, salt);
		}
		num -= 1;
		return createSecSaltNum(str,salt,num);
	}
	
	public static final String createSalt(String key) {
		return Md5Utils.hash(UUID.randomUUID().toString().replaceAll("-","")+key).trim();
	}
	
}
