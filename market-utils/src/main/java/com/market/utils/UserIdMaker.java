package com.market.utils;

import java.util.UUID;

public class UserIdMaker {
	
	public static String newUserId(String userName) {
		if(CheckUtil.isBlank(userName)) {
			return null;
		}
		int length = userName.length();
		if(length>5) {
			userName = userName.substring(length-4);
		}
		return "user_"+UUID.randomUUID().toString().replaceAll("-","").trim().substring(10)+userName;
	}
	
}
