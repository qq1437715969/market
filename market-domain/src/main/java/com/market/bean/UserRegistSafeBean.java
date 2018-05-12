package com.market.bean;

public class UserRegistSafeBean extends UserSafeBean {

	private String method;
	
	private String jsSalt;
	
	private String salt;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getJsSalt() {
		return jsSalt;
	}

	public void setJsSalt(String jsSalt) {
		this.jsSalt = jsSalt;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}
