package com.market.domain;

import java.util.Date;

public class BaseUserBean {

	private String userId;
	
	private String password;
	
	private String pass;
	
	private String appid;
	
	private String ipAddr;
	
	private String accessToken;
	
	private Date loginTime;
	
	private Long longTime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
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
