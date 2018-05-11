package com.market.domain;

public class UserRegistBean extends BaseUserBean {

	private String userName;

	private String imgYzm;
	
	private Integer imgYzmPur;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImgYzm() {
		return imgYzm;
	}

	public void setImgYzm(String imgYzm) {
		this.imgYzm = imgYzm;
	}

	public Integer getImgYzmPur() {
		return imgYzmPur;
	}

	public void setImgYzmPur(Integer imgYzmPur) {
		this.imgYzmPur = imgYzmPur;
	}
	
}
