package com.market.admin.service;

import java.util.List;

import com.market.bean.Admin;
import com.market.bean.Menu;
import com.market.domain.CommonRsp;
import com.market.domain.MenuTree;
import com.market.domain.Response;
import com.market.dto.AdminLoginDto;
import com.market.dto.SubAdminRegistDto;

public interface AdminSer {
	
	Admin queryById(String adminId);
	
	List<Menu> getMenuByRoleId(String roleId);
	
	String getRoleIdsById(String adminId);

	List<Menu> getMenusById(String adminId);
	
	MenuTree getMenuTreeById(String adminId);

	List<MenuTree> getAllMenus();

	CommonRsp<AdminLoginDto> login(String adminId, String pass,String ipAddr);

	CommonRsp<SubAdminRegistDto> createSubAdmin(String subAdmin, String pass);
	
	Response remove(String adminId);
	
	CommonRsp<List<Admin>> querySubAdmin();
}
