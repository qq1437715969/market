package com.market.domain;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MenuTree {

	private String menuId;

	private String parentId;
	
	private String menuName;
	
	private String menuType;
	
	private String isParent;
	
	private List<MenuTree> children;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public List<MenuTree> getChildren() {
		if("O".equals(this.menuType)) {
			return null;
		}else {
			return children;
		}
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}

	public String getIsParent() {
		if("M".equals(this.menuType)) {
			return "isParent";
		}else {
			return "";
		}
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}
	
}
