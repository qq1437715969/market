package com.market.user.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.bean.KeysBean;
import com.market.bean.UserBean;
import com.market.constant.CommonConstant;
import com.market.constant.UserConstant;
import com.market.core.config.CacheClient;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.UserRegistBean;
import com.market.dto.UserLoginDto;
import com.market.exception.UserException;
import com.market.user.service.UserLoginSer;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;
import com.market.utils.RSASecurityTool;

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
			parseRegistInfo(user);
		} catch (Exception e) {
			throw new UserException("解析注册参数异常,请校验您的参数");
		}
		
//		System.out.println(JSON.toJSONString(user));
//		Integer random = user.getRandom();
//		if(null==random) {
//			return null;
//		}
//		random = random%6;
//		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+random);
//		String privateKey = keysBean.getPrivateKey();
//		String info = user.getInfo();
//		System.out.println(info);
		
//		try {
//			info = RSASecurityTool.decryptByPrivateKey(info, privateKey);
//			System.out.println(info);
//			JSONObject jsonObj = JSON.parseObject(info);
//			String userName = jsonObj.getString("userName");
//			String pass = jsonObj.getString("pass");
//			String salt = SaltUtils.createSalt(userName);
//			pass = Md5Utils.encryptString(pass, salt, random);
//			System.out.println(userName+":"+pass);
//			System.out.println(salt);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return null;
	}

	private void parseRegistInfo(UserRegistBean user) throws Exception {
		String info = user.getInfo();
		String[] infoArr = info.split("\\"+CommonConstant.DOT_STR);
		String jsonStr = infoArr[0];
		Integer random = user.getRandom();
		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+(random));
		String privateKey = keysBean.getPrivateKey();
		info = RSASecurityTool.decryptByPrivateKey(jsonStr, privateKey);
		
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
	
	
}
