package com.market.domain;

import java.util.List;

public class Page<T> {

	private Integer page;// 当前页
	private Integer totalPage;// 总页数
	private Integer totalRecords;// 总记录数
	private Integer rows;// 每页要显示的数据条目
	private List<T> result;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}



	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalPage() {
		totalPage = totalRecords % rows == 0 ? totalRecords / rows : totalRecords / rows + 1;
		return totalPage;
	}

}
