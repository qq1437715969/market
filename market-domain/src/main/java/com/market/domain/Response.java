package com.market.domain;

import java.io.Serializable;



public class Response implements Serializable {

	private static final long serialVersionUID = -1412088307616236078L;

	private String code = CodeDict.SUCCESS.getCode();

	private String msg = CodeDict.SUCCESS.getDesc();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
