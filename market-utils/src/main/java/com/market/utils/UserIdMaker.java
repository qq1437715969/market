package com.market.utils;

import java.util.UUID;

public class UserIdMaker {
	
	public static String newUserId(String userName) {
		if(CheckUtil.isBlank(userName)) {
			return null;
		}
		int length = userName.length();
		if(length>13) {
			userName = userName.substring(12);
		}
		if(length>9) {
			userName = userName.substring(8);
		}
		if(length>5) {
			userName = userName.substring(4);
		}
		if(length>3) {
			userName = userName.substring(2);
		}
		return "user_"+UUID.randomUUID().toString().replaceAll("-","").trim().substring(10)+userName;
	}
	
}
