package com.market.admin.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.market.admin.controller.AdminCon;
import com.market.admin.mapper.AdminMapper;
import com.market.admin.mapper.AdminRoleRelMapper;
import com.market.admin.service.AdminSer;
import com.market.bean.Admin;
import com.market.bean.AdminLoginBean;
import com.market.bean.AdminRoleRel;
import com.market.bean.Menu;
import com.market.constant.AdminConstant;
import com.market.constant.AdminStatusConstant;
import com.market.core.config.CacheClient;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.MenuTree;
import com.market.domain.Response;
import com.market.dto.AdminLoginDto;
import com.market.dto.SubAdminRegistDto;
import com.market.exception.AdminException;
import com.market.utils.CheckUtil;
import com.market.utils.TokenUtils;


@Service("adminService")
public class AdminService implements AdminSer {
	
	@Autowired
	private AdminMapper adminMapper;
	
	@Autowired
	private CacheClient client;
	
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
	public CommonRsp<AdminLoginDto> login(String adminId, String pass,String ipAddr) {
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
		loginDto.setLoginTime(new Date());
		
		Integer update = adminMapper.updateLoginTime(loginDto);
		if(update!=1) {
			throw new AdminException("登陆失败，请联系管理员");
		}
		
		Map<String, String> map = TokenUtils.createAppId(adminId, loginDto.getLoginTime(), ipAddr);
		Map<String, String> noIpMap = TokenUtils.createAppId(adminId, loginDto.getLoginTime(), null);
		
		String appIdBak = noIpMap.get(AdminConstant.APPID);
		String randomBak = noIpMap.get(AdminConstant.RANDOM);
		loginDto.setAppIdBak(appIdBak);
		loginDto.setAccessTokenBak(TokenUtils.createToken(appIdBak, randomBak));
		
		String appId = map.get(AdminConstant.APPID);
		String random = map.get(AdminConstant.RANDOM);
		loginDto.setAppId(appId);
		AdminLoginBean loginBean = new AdminLoginBean();
		loginBean.setAdminId(adminId);
		loginBean.setAdminName(admin.getAdminName());
		loginBean.setAppId(appId);
		loginBean.setLastLoginTime(admin.getLastLoginTime());
		loginBean.setLoginTime(loginDto.getLoginTime());
		loginBean.setRandom(random);
		loginBean.setLoginIpAddr(ipAddr);
		loginBean.setRandomBak(randomBak);

		String token = TokenUtils.createToken(appId, random);
		loginBean.setAccessToken(token);
		loginDto.setAccessToken(token);
		client.set(AdminConstant.ADMIN_ONLINE_PRE+loginBean.getAdminId(),loginBean);
//		client.set(AdminConstant.ADMIN_ONLINE_BAK_PRE+loginBean.getAdminId(), obj);
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setMsg("登陆成功");
		rsp.setData(loginDto);
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
	
	@Override
	public Response remove(String adminId) {
		Response response = new Response();
		response.setCode(CodeDict.FAILED.getCode());
		Admin admin = adminMapper.selectById(adminId);
		if(null==admin) {
			response.setMsg("当前管理员不存在");
			return response;
		}
		Integer delete = adminMapper.deleteById(adminId);
		if(delete!=1) {
			throw new AdminException("数据异常");
		}
		response.setCode(CodeDict.SUCCESS.getCode());
		response.setMsg("管理员删除成功");
		return response;
	}
	
	@Override
	public CommonRsp<List<Admin>> querySubAdmin() {
		CommonRsp<List<Admin>> rsp = new CommonRsp<List<Admin>>();
		rsp.setCode(CodeDict.FAILED.getCode());
		List<Admin> list = adminMapper.selectAll();
		if(null==list||list.size()==0) {
			rsp.setMsg("未查询到任何数据");
			return rsp;
		}
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setCode("查询完成");
		rsp.setData(list);
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
