package com.market.domain;

import java.util.Date;

public class BaseAdminBean {

	private String adminId;
	
	private String appid;
	
	private String ipAddr;
	
	private String accessToken;
	
	private Date loginTime;
	
	private Long longTime;

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Long getLongTime() {
		return longTime;
	}

	public void setLongTime(Long longTime) {
		this.longTime = longTime;
	}
	
}
