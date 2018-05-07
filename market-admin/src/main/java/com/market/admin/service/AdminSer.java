package com.market.admin.service;

import java.util.List;

import com.market.bean.Admin;
import com.market.bean.Menu;
import com.market.domain.MenuTree;

public interface AdminSer {
	
	Admin queryById(String adminId);
	
	List<Menu> getMenuByRoleId(String roleId);
	
	String getRoleIdsById(String adminId);

	List<Menu> getMenusById(String adminId);
	
	MenuTree getMenuTreeById(String adminId);
	
}
