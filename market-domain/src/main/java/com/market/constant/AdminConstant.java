package com.market.constant;

import java.util.HashSet;
import java.util.Set;

public class AdminConstant {

	public final static String ADMIN_DEFAULT_PASS = "888888";

	public final static Integer ADMIN_DEFAULT_ROLEID = 2;
	
	public static Set<String> CAN_NOT_REMOVE_ADMINIDS = new HashSet<String>(); 
	
	static {
		CAN_NOT_REMOVE_ADMINIDS.add("admin");
//		CAN_NOT_REMOVE_ADMINIDS.add("subAdmin");
//		CAN_NOT_REMOVE_ADMINIDS.add("subadmin");
//		CAN_NOT_REMOVE_ADMINIDS.add("adminId");
	}
}
