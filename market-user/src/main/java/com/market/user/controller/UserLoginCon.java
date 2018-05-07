package com.market.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.market.bean.UserBean;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.dto.UserLoginDto;
import com.market.user.service.UserLoginSer;
import com.market.utils.CheckUtil;

/**
 * @author LL
 * 用户登陆入口
 */
@RestController("/user")
public class UserLoginCon {

	@Autowired
	private UserLoginSer userLoginService;
	
	@PostMapping("/login.do")
	public CommonRsp<UserLoginDto> login(UserBean bean){
		String phone = bean.getPhone();
		String password = bean.getPassword();
		CommonRsp<UserLoginDto> resp = new CommonRsp<UserLoginDto>();
		resp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(phone)||CheckUtil.isBlank(password)) {
			resp.setMsg("参数校验失败");
			return resp;
		}
		return userLoginService.login(bean);
	}
	
	@PostMapping("/regist.do")
	public CommonRsp<UserLoginDto> regist(){
		
		
		return null;
	}
	
	
}
