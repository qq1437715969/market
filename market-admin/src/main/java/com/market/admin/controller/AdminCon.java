package com.market.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.market.admin.service.AdminSer;
import com.market.bean.Admin;
import com.market.bean.Menu;
import com.market.constant.AdminConstant;
import com.market.core.annotion.AdminCheckLogin;
import com.market.core.annotion.RealIP;
import com.market.domain.BaseAdminBean;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.MenuTree;
import com.market.domain.Response;
import com.market.dto.SubAdminRegistDto;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/admin")
public class AdminCon extends BaseCon {
	
	
	@Autowired
	private AdminSer adminService;
	
	@RequestMapping("/queryById.do")
	public CommonRsp<Admin> queryById(String adminId) {
		CommonRsp<Admin> rsp = new CommonRsp<Admin>();
		rsp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(adminId)) {
			rsp.setMsg("必要参数缺失");
			return rsp;
		}
		Admin admin = adminService.queryById(adminId);
		if(null==admin) {
			rsp.setMsg("查询无果");
			return rsp;
		}
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setMsg("查询成功");
		rsp.setData(admin);
		log.info(JSON.toJSONString(rsp));
		return rsp;
	}
	
	@RequestMapping("/getMenusById.do")
	@AdminCheckLogin
	public CommonRsp<List<MenuTree>> getMenusById(Admin admin) {
//		,String adminId
		String adminId = "";
		CommonRsp<List<MenuTree>> rsp = new CommonRsp<List<MenuTree>>();
		rsp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(adminId)) {
			rsp.setMsg("必要参数缺失");
			return rsp;
		}
		MenuTree menuTree = adminService.getMenuTreeById(adminId);
		rsp.setCode(CodeDict.SUCCESS.getCode());
		rsp.setMsg("查询成功");
		rsp.setData(menuTree.getChildren());
		return rsp;
	}
	
	@RequestMapping("/listMenuTree.do")
	public List<MenuTree> listMenuTreeById(String adminId){
		if(CheckUtil.isBlank(adminId)) {
			return null;
		}
		MenuTree menuTree = adminService.getMenuTreeById(adminId);
		if(null==menuTree||null==menuTree.getChildren()||menuTree.getChildren().size()==0) {
			return null;
		}
		return menuTree.getChildren();
	}
	
	@RequestMapping("/listAllMenus.do")
//	@RealIP
	@AdminCheckLogin
	public List<MenuTree> listAllMenus(BaseAdminBean admin){
		List<MenuTree> menus = adminService.getAllMenus();
		if(null==menus||menus.size()==0) {
			return null;
		}
		return menus;
	}
	
	@RequestMapping("/createSubAdmin.do")
	public CommonRsp<SubAdminRegistDto> createSubAdmin(String subAdmin,String pass) {
		CommonRsp<SubAdminRegistDto> rsp = new CommonRsp<SubAdminRegistDto>();
		rsp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(subAdmin)||subAdmin.length()<5||subAdmin.length()>15) {
			rsp.setMsg("管理员名称不合法");
			return rsp;
		}
		return adminService.createSubAdmin(subAdmin,pass);
	}
	
	@RequestMapping("/removeSubAdmin.do")
	public Response removeSubAdmin(String adminId) {
		Response response = new Response();
		response.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(adminId)) {
			response.setMsg("参数不合法");
		}
		if(AdminConstant.CAN_NOT_REMOVE_ADMINIDS.contains(adminId)) {
			response.setMsg("被保护的adminId,你无法删除");
			return response;
		}
		return adminService.remove(adminId);
	}
	
	@RequestMapping("/querySubAdminBaseInfo.do")
	public CommonRsp<List<Admin>> querySubAdminBaseInfo(){
		 return adminService.querySubAdmin();
	}
	
}
