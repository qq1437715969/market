package com.market.bean;

import com.market.domain.BaseBean;

public class UserSafeBean<T> extends BaseBean {
	
	private String imei;
	
	private int mobileType; // 0 web 1安卓 2apple
	
	private String foot;
	
	private String info;
	
	private String sign;
	
	private Long accessTime;
	
	private Long lastSafeTime;
	
	private T data; 
	
	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public int getMobileType() {
		return mobileType;
	}

	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}

	public String getFoot() {
		return foot;
	}

	public void setFoot(String foot) {
		this.foot = foot;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
	public Long getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Long accessTime) {
		this.accessTime = accessTime;
	}

	public Long getLastSafeTime() {
		return lastSafeTime;
	}

	public void setLastSafeTime(Long lastSafeTime) {
		this.lastSafeTime = lastSafeTime;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
