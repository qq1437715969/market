package com.market.domain;

import java.util.Date;

public class BaseAdminBean {

	private String adminId;
	
	private String appid;
	
	private String ipArrd;
	
	private String accessToken;
	
	private Date loginTime;

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

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getIpArrd() {
		return ipArrd;
	}

	public void setIpArrd(String ipArrd) {
		this.ipArrd = ipArrd;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}
	
}
