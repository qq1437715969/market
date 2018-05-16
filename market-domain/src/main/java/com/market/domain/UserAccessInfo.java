package com.market.domain;

public class UserAccessInfo extends BaseBean {

	private String reqIp;
	
	private String userId;
	
	private SystemDomain sys;
	
	private IpInfoDomain ip;

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SystemDomain getSys() {
		return sys;
	}

	public void setSys(SystemDomain sys) {
		this.sys = sys;
	}

	public IpInfoDomain getIp() {
		return ip;
	}

	public void setIp(IpInfoDomain ip) {
		this.ip = ip;
	}
	
}
