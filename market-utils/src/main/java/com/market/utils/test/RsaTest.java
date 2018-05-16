package com.market.utils.test;

import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import com.market.utils.RSASecurityTool;

public class RsaTest {
	
	@Test
	public void fun1() {
		try {
			Map<String, Object> keyMap = RSASecurityTool.initKey();
//			keyMap.get(arg0);
			String pubKey = RSASecurityTool.getPublicKey(keyMap);
			String priKey = RSASecurityTool.getPrivateKey(keyMap);
			System.out.println(pubKey);
			System.out.println(priKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
