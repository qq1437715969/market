package com.market.constant;

import java.util.HashSet;
import java.util.Set;

public class AdminConstant {

	public final static String ADMIN_DEFAULT_PASS = "888888";

	public final static Integer ADMIN_DEFAULT_ROLEID = 2;
	
	public static Set<String> CAN_NOT_REMOVE_ADMINIDS = new HashSet<String>(); 
	
	public static final String RANDOM = "random";
	
	public static final String APPID = "appid";
	
	public static final String APPID_BAK = "appidBak";
	
	public static final String ADMIN_ONLINE_PRE = "OnLineAdmin_";
	
	public static final String ADMIN_ONLINE_BAK_PRE = "OnLineAdmin_BAK";
	
	public static final String ONLINE_APPID_PRE = "OnLineAppId_";
	
	public static final String ACCESSTOKEN  = "accessToken";
	
	public static final String ACCESSTOKEN_BAK  = "accessTokenBak";
	
	static {
		CAN_NOT_REMOVE_ADMINIDS.add("admin");
//		CAN_NOT_REMOVE_ADMINIDS.add("subAdmin");
//		CAN_NOT_REMOVE_ADMINIDS.add("subadmin");
//		CAN_NOT_REMOVE_ADMINIDS.add("adminId");
	}
}
