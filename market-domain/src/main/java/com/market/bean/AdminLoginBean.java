package com.market.bean;

import com.market.dto.AdminLoginDto;

/**
 * 存redis中的数据
 * @author wolf
 *
 */
public class AdminLoginBean extends AdminLoginDto{

	private String random;

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
		this.random = random;
	}
	
}
