package com.market.bean;

import com.market.dto.AdminLoginDto;

/**
 * 存redis中的数据
 * @author wolf
 *
 */
public class AdminLoginBean extends AdminLoginDto{

	private String random;
	
	private String randomBak;
	
	private String loginIpAddr;

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}

	public String getRandomBak() {
		return randomBak;
	}

	public void setRandomBak(String randomBak) {
		this.randomBak = randomBak;
	}

	public String getLoginIpAddr() {
		return loginIpAddr;
	}

	public void setLoginIpAddr(String loginIpAddr) {
		this.loginIpAddr = loginIpAddr;
	}
	
}
