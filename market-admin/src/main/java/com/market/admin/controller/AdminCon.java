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
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.MenuTree;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/admin")
public class AdminCon extends BaseCon {
	
	
	@Autowired
	private AdminSer adminService;
	
	@RequestMapping("/{adminId}")
	public CommonRsp<Admin> queryById(@PathVariable("adminId")String adminId) {
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
	public CommonRsp<List<MenuTree>> getMenusById(String adminId) {
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
	
}
