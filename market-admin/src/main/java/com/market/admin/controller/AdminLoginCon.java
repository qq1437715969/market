package com.market.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.admin.service.AdminSer;
import com.market.core.annotion.RealIP;
import com.market.domain.BaseAdminBean;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.dto.AdminLoginDto;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/admin")
public class AdminLoginCon {
	
	@Autowired
	private AdminSer adminService;
	
	@RequestMapping("/login.do")
	@RealIP
	public CommonRsp<AdminLoginDto> login(BaseAdminBean admin) {
		CommonRsp<AdminLoginDto> rsp = new CommonRsp<AdminLoginDto>();
		rsp.setCode(CodeDict.FAILED.getCode());
		String adminId = admin.getAdminId();
		String pass = admin.getPass();
		if(CheckUtil.isBlank(adminId)||CheckUtil.isBlank(pass)) {
			rsp.setMsg("登陆信息校验失败");
			return rsp;
		}
		return adminService.login(adminId,pass,admin.getIpAddr());
	}
	
}
