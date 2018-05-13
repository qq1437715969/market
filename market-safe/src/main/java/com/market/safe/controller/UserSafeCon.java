package com.market.safe.controller;


import java.io.UnsupportedEncodingException;

import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.market.bean.UserRegistSafeBean;
import com.market.domain.UserAccessInfo;
import com.market.exception.UserException;
import com.market.safe.annotion.CheckHeaders;
import com.market.safe.annotion.GetAccessInfo;
import com.market.safe.config.CacheClient;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;
import com.market.utils.MyShaUtil;
import com.market.utils.SaltUtils;

@RestController
@RequestMapping("/user")
public class UserSafeCon {

	private Logger log = LoggerFactory.getLogger("userSafe");
	
	@Autowired
	private CacheClient client;
	
	@Autowired
	private HttpServletRequest req;
	
	@GetMapping("/registPre.do")
	@CheckHeaders
	@GetAccessInfo
	public void registPre(UserRegistSafeBean safe) throws UnsupportedEncodingException {
		UserAccessInfo access = (UserAccessInfo)safe.getData();
		String salt = SaltUtils.createSalt(access.toJson());
		String lastSalt = SaltUtils.createSecSaltNum(access.toJson(),salt,3);
		log.info(lastSalt);
	}
	
	
	
	private String decode(String str,int num) {
		if(num==0||num==1) {
			byte[] bytes = Base64.decodeBase64(str);
			return new String(bytes);
		}
		num -= 1;
		return decode(str, num);
	}
	
	
}
