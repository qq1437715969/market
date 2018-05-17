package com.market.user.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.market.bean.KeysBean;
import com.market.bean.UserBean;
import com.market.constant.CommonConstant;
import com.market.constant.UserConstant;
import com.market.core.annotion.RsaInfoDec;
import com.market.core.annotion.UserRealIP;
import com.market.core.config.CacheClient;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.Response;
import com.market.domain.UserInfo;
import com.market.domain.UserRegistBean;
import com.market.domain.UserRegistDomain;
import com.market.dto.UserLoginDto;
import com.market.exception.UserException;
import com.market.user.service.UserLoginSer;
import com.market.user.service.UserSer;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;
import com.market.utils.MyJsonUtil;
import com.market.utils.RSASecurityTool;
import com.market.utils.SaltUtils;
import com.market.utils.UserIdMaker;

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
	
	@Autowired
	private UserSer userService;
	
	@PostMapping("/login.do")
	@RsaInfoDec
	public CommonRsp<UserLoginDto> login(UserBean user){
		String info = user.getInfo();
		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+(user.getRandom()));
		String privateKey = keysBean.getPrivateKey();
		try {
			info = RSASecurityTool.decryptByPrivateKey(info, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(info);
		user.setInfo(info);
		JSONObject json = JSON.parseObject(info);
		user.setUserName(json.getString("userName"));
		user.setPassword(json.getString("pass"));
		String loginType = user.getLoginType();
		if(CheckUtil.isBlank(loginType)) {
			return userLoginService.loginByName(user);
		}
		
//		String phone = bean.getPhone();
//		String password = bean.getPassword();
//		CommonRsp<UserLoginDto> resp = new CommonRsp<UserLoginDto>();
//		resp.setCode(CodeDict.FAILED.getCode());
//		if(CheckUtil.isBlank(phone)||CheckUtil.isBlank(password)) {
//			resp.setMsg("参数校验失败");
//			return resp;
//		}
		return userLoginService.login(user);
	}
	
	@PostMapping("/regist.do")
	@UserRealIP
	public CommonRsp<UserLoginDto> regist(UserRegistBean user){
		CommonRsp<UserLoginDto> resp = new CommonRsp<UserLoginDto>();
		resp.setCode(CodeDict.FAILED.getCode());
		String imgYzm = user.getImgYzm();
		if(CheckUtil.isBlank(imgYzm)) {
			resp.setMsg("验证码为空");
			return resp;
		}
		if(!checkRegistParams(user)) {
			resp.setMsg("注册参数校验失败");
			return resp;
		}
		try {
			UserRegistDomain register = parseRegistInfo(user);
			userService.createUser(register,user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("解析注册参数异常,请校验您的参数");
		}
		return null;
	}


	private UserRegistDomain parseRegistInfo(UserRegistBean user) throws Exception {
		String info = user.getInfo();
		String[] infoArr = info.split("\\"+CommonConstant.DOT_STR);
		String jsonStr = infoArr[0];
		Integer random = user.getRandom();
		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+(random));
		String privateKey = keysBean.getPrivateKey();
		info = RSASecurityTool.decryptByPrivateKey(jsonStr, privateKey);
		UserRegistDomain domain = MyJsonUtil.parseObject(info,UserRegistDomain.class, null);
		domain.setRandom(user.getRandom());
		return domain;
	}

	private boolean checkRegistParams(UserRegistBean user) {
		if(null==user) {
			return false;
		}
		String info = user.getInfo();
		String sign = user.getSign();
		if(CheckUtil.isBlank(info)||CheckUtil.isBlank(sign)) {
			throw new UserException("用户信息缺失");
		}
		if(info.indexOf(CommonConstant.DOT_STR)==-1) {
			throw new UserException("用户信息缺失");
		}
		String[] infoArr = info.split("\\"+CommonConstant.DOT_STR);
		if(infoArr.length!=UserConstant.USER_REGIST_INFO_DOT_NUM) {
			throw new UserException("用户信息缺失");
		}
		String salt = infoArr[2];
		String random = infoArr[1];
		Integer nums = Integer.parseInt(Base64Util.decode(random));
		String baseInfo = info.substring(0, info.lastIndexOf(CommonConstant.DOT_STR));
		String backSign = Md5Utils.encryptString(baseInfo, salt, nums);
		if(!backSign.equals(sign)) {
			throw new UserException("用户信息被篡改");
		}
		user.setRandom(nums%6);
		return true;
	}
	
	@PostMapping("/checkExist.do")
	public Response checkExist(String userName) {
		Response resp = new Response();
		resp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(userName)) {
			resp.setMsg("参数缺失");
			return resp;
		}
		userName = Base64Util.decode(userName);
		boolean exist = userService.checkNameExist(userName);
		if(exist) {
			resp.setMsg("用户名已存在");
			return resp;
		}
		resp.setCode(CodeDict.SUCCESS.getCode());
		resp.setMsg("可以使用的用户名");
		return resp;
	}
	
}
