package com.market.domain;

public class CommonRsp<T> extends Response {

	private static final long serialVersionUID = 460456776638649480L;

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
