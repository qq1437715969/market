package com.market.utils;

import org.apache.commons.lang3.StringUtils;

public class CheckUtil {

	private final static String NULL_STR = "null";
	
	public static boolean isBlank(String string) {
		boolean blank = StringUtils.isBlank(string);
		if(blank) {
			return true;
		}
		if(string.trim().equals(NULL_STR)) {
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlank(String string) {
		return !isBlank(string);
	}
	
}
