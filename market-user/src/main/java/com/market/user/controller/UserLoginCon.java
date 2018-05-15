package com.market.user.controller;

import static org.assertj.core.api.Assertions.in;

import java.security.interfaces.RSAPrivateKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.market.bean.KeysBean;
import com.market.bean.UserBean;
import com.market.constant.UserConstant;
import com.market.core.config.CacheClient;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.UserRegistBean;
import com.market.dto.UserLoginDto;
import com.market.user.service.UserLoginSer;
import com.market.utils.CheckUtil;
import com.market.utils.RSAUtil;

/**
 * @author LL
 * 用户登陆入口
 */
@RestController
@RequestMapping("/user")
public class UserLoginCon {

	@Autowired
	private CacheClient client;
	
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
	public CommonRsp<UserLoginDto> regist(UserRegistBean user){
		System.out.println(JSON.toJSONString(user));
		Integer random = user.getRandom();
		if(null==random) {
			return null;
		}
		random = random%6;
//		String random = infoArr[1];
//		int rand = Integer.parseInt(random);
//		rand = (int) Math.ceil(rand%6);
		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+random);
		String privateKey = keysBean.getPrivateKey();
		RSAPrivateKey key = RSAUtil.loadPrivateKey(privateKey);
		try {
			byte[] decrypt = RSAUtil.decrypt(key, user.getUserName().getBytes());
			String string = new String(decrypt);
			System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
