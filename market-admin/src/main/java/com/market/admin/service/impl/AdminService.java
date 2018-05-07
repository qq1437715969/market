package com.market.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.admin.mapper.AdminMapper;
import com.market.admin.service.AdminSer;
import com.market.bean.Admin;
import com.market.bean.Menu;
import com.market.domain.MenuTree;
import com.market.utils.CheckUtil;


@Service("adminService")
public class AdminService implements AdminSer {

	@Autowired
	private AdminMapper adminMapper;
	
	@Override
	public Admin queryById(String adminId) {
		 Admin admin = adminMapper.selectById(adminId);
		 if(null==admin) {
			 return null;
		 }
		 String roleIds = adminMapper.getRolesById(adminId);
		 if(CheckUtil.isBlank(roleIds)) {
			 admin.setRoleType(0);
			 return admin;
		 }
		 if(roleIds.contains(",")) {
			 admin.setRoleIds(roleIds);
			 admin.setRoleType(2);
			 return admin;
		 }else {
			 admin.setRoleId(roleIds);
			 admin.setRoleType(1);
			 return admin;
		 }
	}
	
	@Override
	public List<Menu> getMenuByRoleId(String roleId) {
		return adminMapper.getMenuByRoleId(roleId);
	}

	
	@Override
	public String getRoleIdsById(String adminId) {
		return adminMapper.getRolesById(adminId);
	}

	@Override
	public List<Menu> getMenusById(String adminId) {
		 Admin admin = adminMapper.selectById(adminId);
		 if(null==admin) {
			 return null;
		 }
		 String roleIds = adminMapper.getRolesById(adminId);
		 if(CheckUtil.isBlank(roleIds)) {
			 return null;
		 }
		 if(roleIds.contains(",")) {
			 return adminMapper.getMenuByRoleIds(roleIds);
		 }else {
			 return adminMapper.getMenuByRoleId(roleIds);
		 }
	}
	
	@Override
	public MenuTree getMenuTreeById(String adminId){
		 Admin admin = adminMapper.selectById(adminId);
		 if(null==admin) {
			 return null;
		 }
		 String roleIds = adminMapper.getRolesById(adminId);
		 if(CheckUtil.isBlank(roleIds)) {
			 return null;
		 }
		 List<Menu> menus = null;
		 if(roleIds.contains(",")) {
			 menus = adminMapper.getMenuByRoleIds(roleIds);
		 }else {
			 menus = adminMapper.getMenuByRoleId(roleIds);
		 }
		 if(null==menus||menus.size()==0) {
			 return null;
		 }
		 List<MenuTree> trees = generateMenus(menus);
		 if(null==trees||trees.size()==0) {
			 return null;
		 }
		 MenuTree menuTree = new MenuTree();
		 menuTree.setMenuId("root");
		 menuTree.setMenuName("root");
		 menuTree.setMenuType("root");
		 menuTree.setChildren(trees);
		 return menuTree;
	}

	private List<MenuTree> generateMenus(List<Menu> menus) {
		List<MenuTree> rootMenuTrees = getRootMenuTrees(menus);
		if(null==rootMenuTrees||rootMenuTrees.size()==0) {
			return null;
		}
		getChildMenus(rootMenuTrees,menus);
		return rootMenuTrees;
	}

	private void getChildMenus(List<MenuTree> rootMenuTrees, List<Menu> menus) {
		for(MenuTree rootMenu:rootMenuTrees) {
			getChildMenus(rootMenu,menus);
		}
	}

	private void getChildMenus(MenuTree rootMenu, List<Menu> menus) {
		String menuId = rootMenu.getMenuId();
		ArrayList<MenuTree> list = new ArrayList<MenuTree>();
		for(Menu menu:menus) {
			String parentId = menu.getParentId();
			if(CheckUtil.isNotBlank(parentId)&&parentId.equals(menuId)) {
				MenuTree menuTree = new MenuTree();
				menuTree.setMenuId(menu.getMenuId());
				menuTree.setMenuName(menu.getMenuName());
				menuTree.setMenuType(menu.getMenuType());
				menuTree.setParentId(parentId);
				getChildMenus(menuTree, menus);
				list.add(menuTree);
			}
		}
		rootMenu.setChildren(list);
	}

	private List<MenuTree> getRootMenuTrees(List<Menu> menus) {
		ArrayList<MenuTree> list = new ArrayList<MenuTree>();
		for(Menu menu:menus) {
			if(CheckUtil.isBlank(menu.getParentId())) {
				MenuTree child = new MenuTree();
				child.setMenuId(menu.getMenuId());
				child.setMenuName(menu.getMenuName());
				child.setMenuType(menu.getMenuType());
				list.add(child);
			}
		}
		return list;
	}

}
