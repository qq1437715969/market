package com.market.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;

import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;

import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;

import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("unused")

public class RSAUtil {

	public static final String KEY_ALGORITHM = "RSA";

	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	private static final String PUBLIC_KEY = "RSAPublicKey";

	private static final String PRIVATE_KEY = "RSAPrivateKey";

	public static void main(String[] args) {

		Map<String, Object> keyMap;

		try {

			keyMap = initKey();

			String publicKey = getPublicKey(keyMap);

			System.out.println(publicKey);

			String privateKey = getPrivateKey(keyMap);

			System.out.println(privateKey);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public static String getPublicKey(Map<String, Object> keyMap) throws Exception {

		Key key = (Key) keyMap.get(PUBLIC_KEY);

		byte[] publicKey = key.getEncoded();

		return encryptBASE64(key.getEncoded());

	}

	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {

		Key key = (Key) keyMap.get(PRIVATE_KEY);

		byte[] privateKey = key.getEncoded();

		return encryptBASE64(key.getEncoded());

	}

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decodeBase64(key);

	}

	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeBase64String(key);

	}

	public static Map<String, Object> initKey() throws Exception {

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

		keyPairGen.initialize(1024);

		KeyPair keyPair = keyPairGen.generateKeyPair();

		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		Map<String, Object> keyMap = new HashMap<String, Object>(2);

		keyMap.put(PUBLIC_KEY, publicKey);

		keyMap.put(PRIVATE_KEY, privateKey);

		return keyMap;
	}
		
	/**
	 * 获取公钥
	 * @param publicKeyStr
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr)  
            throws Exception {  
        try {  
            byte[] buffer = Base64.decodeBase64(publicKeyStr);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此算法");  
        } catch (InvalidKeySpecException e) {  
            throw new Exception("公钥非法");  
        } catch (NullPointerException e) {  
            throw new Exception("公钥数据为空");  
        }  
    }  
	
	/**
	 * 获取私钥
	 * @param privateKeyStr
	 * @return
	 */
	public static RSAPrivateKey loadPrivateKey(String privateKeyStr) {
		byte[] buffer = Base64.decodeBase64(privateKeyStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);  
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}  
		return null;
	}
	
	/**
	 * 公钥加密
	 * @param publicKey
	 * @param plainTextData
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)  
            throws Exception {  
        if (publicKey == null) {  
            throw new Exception("加密公钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
            byte[] output = cipher.doFinal(plainTextData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此加密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("加密公钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("明文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("明文数据已损坏");  
        }  
    }  
	
	public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)  
            throws Exception {  
        if (privateKey == null) {  
            throw new Exception("解密私钥为空, 请设置");  
        }  
        Cipher cipher = null;  
        try {  
            // 使用默认RSA  
            cipher = Cipher.getInstance("RSA");  
            // cipher= Cipher.getInstance("RSA", new BouncyCastleProvider());  
            cipher.init(Cipher.DECRYPT_MODE, privateKey);  
            byte[] output = cipher.doFinal(cipherData);  
            return output;  
        } catch (NoSuchAlgorithmException e) {  
            throw new Exception("无此解密算法");  
        } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
            return null;  
        } catch (InvalidKeyException e) {  
            throw new Exception("解密私钥非法,请检查");  
        } catch (IllegalBlockSizeException e) {  
            throw new Exception("密文长度非法");  
        } catch (BadPaddingException e) {  
            throw new Exception("密文数据已损坏");  
        }  
    }  
	
	
}