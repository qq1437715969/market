package com.market.utils;

import org.apache.commons.codec.binary.Base64;

public class Base64Util {

	public static String decode(String str) {
		if(CheckUtil.isBlank(str)) {
			return "";
		}
		return new String(Base64.decodeBase64(str));
	} 
	
	
	
}
