package com.market.utils;

import java.util.UUID;

public class KeysIdUtils {
	
	public final static String createKeyId(String keyPre) {
		return keyPre+UUID.randomUUID().toString().replaceAll("_","").trim();
	}
	
}
