package com.market.safe.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.bean.KeysBean;
import com.market.domain.CommonRsp;
import com.market.exception.UserException;
import com.market.safe.service.UserSecretSer;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/userKeys")
public class UserKeysCon {
	
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
	public CommonRsp<List<KeysBean>> userViewKeys(){
		return userSecretService.userViewKeys();
	}

}
