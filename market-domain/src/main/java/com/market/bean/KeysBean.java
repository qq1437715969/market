package com.market.bean;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class KeysBean {

	private String keysId;
	
	private String privateKey;
	
	private String publicKey;
	
	private Date createTime;
	
	private Integer random;

	private Date endTime;
	
	private String operator;
	
	private Integer last;

	public String getKeysId() {
		return keysId;
	}

	public void setKeysId(String keysId) {
		this.keysId = keysId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRandom() {
		return random;
	}

	public void setRandom(Integer random) {
		this.random = random;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

}
