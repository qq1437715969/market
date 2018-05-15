package com.market.safe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.market.bean.KeysBean;
import com.market.domain.CommonRsp;
import com.market.exception.UserException;
import com.market.safe.annotion.CheckHeaders;
import com.market.safe.service.UserSecretSer;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.SaltUtils;

@RestController
@RequestMapping("/userKeys")
public class UserKeysCon {
	
	private static Map<String, String> saltMap = new HashMap<String,String>();

	static {
		saltMap.put("A","+");
	}
	
	@Autowired
	private UserSecretSer userSecretService;
	
	@RequestMapping("/adminUpdateKeys.do")
	public CommonRsp<List<KeysBean>> adminUpdateKeys(String admin){
		if(CheckUtil.isBlank(admin)) {
			throw new UserException("操作者信息错误");
		}
		return userSecretService.adminUpdateKeys(admin);
	} 
	
	@RequestMapping("/userViewKeys.do")
	@CheckHeaders
	public String userViewKeys(){
		 CommonRsp<List<KeysBean>> userViewKeys = userSecretService.userViewKeys();
		 String json = JSON.toJSONString(userViewKeys);
		 return "+"+Base64Util.encode(json)+"+";
	}

	@RequestMapping("/flushKeysMap2Cache.do")
	public void flushKeys2Cache() {
		userSecretService.flushKeys2Cache();
	}
}
