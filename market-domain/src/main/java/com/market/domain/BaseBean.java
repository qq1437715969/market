package com.market.domain;

import com.alibaba.fastjson.JSON;

public class BaseBean {

	public String toJson() {
		return JSON.toJSONString(this);
	}
	
}
