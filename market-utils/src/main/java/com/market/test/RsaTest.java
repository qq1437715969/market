package com.market.test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.Test;

import com.market.utils.RSAUtil;

public class RsaTest {

	private static String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO7+ptbUsKR4UYNvAnrEi0BpjpaDwWJTtmYmWmSWDPnk9p6apF/TcUMO3Mp2VwNAgoIIF7ODG1Y9W2l9dvBppvWeKERf1om81q51v805Qtk7XyQ92zSrGXgNwlEpke2xOnItq7KT3OdtyI9yHmt0G0BzlsXW60n7pmQKeSU10BEwIDAQAB";
	
	private static String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI7v6m1tSwpHhRg28CesSLQGmOloPBYlO2ZiZaZJYM+eT2npqkX9NxQw7cynZXA0CCgggXs4MbVj1baX128Gmm9Z4oRF/WibzWrnW/zTlC2TtfJD3bNKsZeA3CUSmR7bE6ci2rspPc523Ij3Iea3QbQHOWxdbrSfumZAp5JTXQETAgMBAAECgYBn1FMgBgI6h1VhD5dH2lg9nYU6F2N+WrFZYCSitC7E4iShtQDhMlzuJ9S1kuuWo9VBAMvNrDY/ozO9HXN6zPskxi45yyQnjYZoxgWY+QVro3CJ4wvUuT2kVvmv896FaMvVCzmH0DxXxE96sPxGnDplqqsNshyOchJfDD4FhthxkQJBAPID5ZlfJMh9F0J7Lso7R6OYZWDRDXy9Mx7NyH7mH3MBBuAl+NxvucPHm0siWEat0RbuhqeFAc8qtXEClP4kWYUCQQCXMl5lN2ahUzJa7NWSiQXlo6AIb2PcM/eEpNQ/56Wjf3eJYm9esIw5/C/Hr+LMgWu5nw2E5rdkloZY53TWUue3AkA43J2HaYlU6b8bSaH2tfLhBx9angOTOUjXhfYDisH83VLmO0W7VSzKSQ29m3YuNQyZXLWaim+gYyJ6SOcExzmZAkAtQnSjU9wy+paeRTsBbGcJgNuM6ts3tY6odDbdElixAp0j0QWJkMvJJ8advfbkRPGcVRJU8EOBptV8k3yUE6ktAkEAu60QXCTQV/gbPhi7C0OV/UFaVmIEzsrRN8DDyhOOpQikHLSx5tUhXM4T1mQ3Wfd+39JULNL2vIIqT6Q8jrHgvA==";
	
	@Test
	public void fun1() {
		try {
			RSAPublicKey publicKey = RSAUtil.loadPublicKeyByStr(publicKeyStr);
			RSAPrivateKey privateKey = RSAUtil.loadPrivateKey(privateKeyStr);
			byte[] encrypt = RSAUtil.encrypt(publicKey,"HELLO,WORLD".getBytes("utf-8"));
			System.out.println(new String(encrypt,"utf-8"));
			byte[] decrypt = RSAUtil.decrypt(privateKey, encrypt);
			String string = new String(decrypt);
			System.out.println(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
