package com.market.user.controller;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.bean.KeysBean;
import com.market.constant.UserConstant;
import com.market.core.config.CacheClient;
import com.market.utils.RSAUtil;

@RestController
@RequestMapping("/rsa")
public class RsaEnsecret {

	@Autowired
	private CacheClient client;
	
	@RequestMapping("/test.do")
	public void test() throws Exception {
		KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+1);
		RSAPrivateKey priKey = RSAUtil.loadPrivateKey(keysBean.getPrivateKey());
		RSAPublicKey pubKey = RSAUtil.loadPublicKeyByStr(keysBean.getPublicKey());
		
	}
	
}
