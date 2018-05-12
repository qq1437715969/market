package com.market.domain;

public class UserAccessInfo extends BaseBean {

	private SystemDomain sys;
	
	private IpInfoDomain ip;

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
