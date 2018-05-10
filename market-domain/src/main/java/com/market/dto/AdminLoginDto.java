package com.market.dto;

import java.util.Date;

public class AdminLoginDto {
	
	private String adminId;
	
	private String adminName;
	
	private Date loginTime;
	
	private Date lastLoginTime;
	
	private String appId;
	
	private String accessToken;

	private String appIdBak;
	
	private String accessTokenBak;
	
	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAppIdBak() {
		return appIdBak;
	}

	public void setAppIdBak(String appIdBak) {
		this.appIdBak = appIdBak;
	}

	public String getAccessTokenBak() {
		return accessTokenBak;
	}

	public void setAccessTokenBak(String accessTokenBak) {
		this.accessTokenBak = accessTokenBak;
	}
	
}
