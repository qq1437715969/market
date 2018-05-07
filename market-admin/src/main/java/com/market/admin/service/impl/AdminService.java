package com.market.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.admin.controller.AdminCon;
import com.market.admin.mapper.AdminMapper;
import com.market.admin.mapper.AdminRoleRelMapper;
import com.market.admin.service.AdminSer;
import com.market.bean.Admin;
import com.market.bean.AdminRoleRel;
import com.market.bean.Menu;
import com.market.constant.AdminConstant;
import com.market.constant.AdminStatusConstant;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.MenuTree;
import com.market.dto.AdminLoginDto;
import com.market.dto.SubAdminRegistDto;
import com.market.exception.AdminException;
import com.market.utils.CheckUtil;


@Service("adminService")
public class AdminService implements AdminSer {

	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private AdminRoleRelMapper adminRoleRelMapper;
	
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

	
	@Override
	public List<MenuTree> getAllMenus() {
		List<Menu> menus = adminMapper.getAllMenus();
		if(null==menus||menus.size()==0) {
			return null;
		}
		return generateMenus(menus);
	}
	

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CommonRsp<AdminLoginDto> login(String adminId, String pass) {
		Admin admin = adminMapper.selectById(adminId);
		CommonRsp<AdminLoginDto> rsp = new CommonRsp<AdminLoginDto>();
		rsp.setCode(CodeDict.FAILED.getCode());
		if(null==admin) {
			rsp.setMsg("该用户不存在");
			return rsp;
		}
		if(admin.getStatus()!=AdminStatusConstant.NORMAL_STATUS) {
			rsp.setMsg("账户被冻结,联系管理员");
			return rsp;
		}
		if(!pass.equals(admin.getPass())) {
			rsp.setMsg("用户名或密码错误");
			return rsp;
		}
		AdminLoginDto loginDto = new AdminLoginDto();
		loginDto.setAdminId(adminId);
		loginDto.setAdminName(admin.getAdminName());
		loginDto.setLastLoginTime(admin.getLastLoginTime());
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setMsg("登陆成功");
		rsp.setData(loginDto);
		Integer update = adminMapper.updateLoginTime(adminId);
		if(update!=1) {
			throw new AdminException("登陆失败，请联系管理员");
		}
		return rsp;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public CommonRsp<SubAdminRegistDto> createSubAdmin(String subAdmin, String pass) {
		CommonRsp<SubAdminRegistDto> rsp = new CommonRsp<SubAdminRegistDto>();
		rsp.setCode(CodeDict.FAILED.getCode());
		Admin dbAdmin = adminMapper.selectById(subAdmin);
		if(null!=dbAdmin) {
			rsp.setMsg("用户已经存在,无法为你创建同名用户");
			return rsp;
		}
		if(CheckUtil.isBlank(pass)) {
			pass = AdminConstant.ADMIN_DEFAULT_PASS;
		}
		Admin admin = new Admin();
		admin.setAdminId(subAdmin);
		admin.setAdminName(subAdmin);
		admin.setPass(pass);
		Integer create = adminMapper.insert(admin);
		if(create!=1) {
			throw new AdminException("创建失败,新建用户失败");
		}
		AdminRoleRel adminRoleRel = new AdminRoleRel();
		adminRoleRel.setAdminId(subAdmin);
		adminRoleRel.setRoleId(AdminConstant.ADMIN_DEFAULT_ROLEID);
		Integer roleRel = adminRoleRelMapper.insert(adminRoleRel);
		if(roleRel!=1) {
			throw new AdminException("创建失败,授权失败");
		}
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setMsg("创建用户成功,请妥善保管密码");
		SubAdminRegistDto rtnDto = new SubAdminRegistDto();
		rtnDto.setAdminId(subAdmin);
		rtnDto.setAdminName(subAdmin);
		rtnDto.setCreateTime(new Date());
		rtnDto.setRoleId(AdminConstant.ADMIN_DEFAULT_ROLEID);
		rsp.setData(rtnDto);
		return rsp;
	}

	/**
	 * 组织树形结构
	 * @param menus
	 * @return
	 */
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
