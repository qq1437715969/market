package com.market.domain;

public enum CodeDict {
	SUCCESS("0","成功"),
	FAILED("1","失败"),
	EXCEPT("2","异常"),
	
	
	LOGIN_NEED("9","未登录"),
	NOAUTH("10","无权限"),
	NOT_PERMISSION_REQUEST("-1","不允许的操作");
	
	private String code;
	
	private String desc;
	
	private CodeDict(String code, String desc){
		this.code = code;
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
