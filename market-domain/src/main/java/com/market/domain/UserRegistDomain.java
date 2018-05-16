package com.market.domain;

public class UserRegistDomain {
	
	private String userId;
	
	private String phone;
	
	private String userName;
	
	private String pass;
	
	private String salt;
	
	private Integer random;
	
	private UserAccessInfo accessInfo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getRandom() {
		return random;
	}

	public void setRandom(Integer random) {
		this.random = random;
	}

	public UserAccessInfo getAccessInfo() {
		return accessInfo;
	}

	public void setAccessInfo(UserAccessInfo accessInfo) {
		this.accessInfo = accessInfo;
	}
	
}
