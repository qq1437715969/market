package com.market.utils;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 非对称加密算法RSA算法组件
 * 非对称算法一般是用来传送对称加密算法的密钥来使用的，相对于DH算法，RSA算法只需要一方构造密钥，不需要
 * 大费周章的构造各自本地的密钥对了。DH算法只能算法非对称算法的底层实现。而RSA算法算法实现起来较为简单
 */
public class RSASecurityTool {
    private final static String publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBALQ9/N2C8C4xHfZCQDuETCxWw7LqVL7SOdLIPdcBfp84qF22Se46up25j8EfTCTy+d1Yi2h2QHy4vGTDMpZ06U0CAwEAAQ==";
    private final static String privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAtD383YLwLjEd9kJAO4RMLFbDsupUvtI50sg91wF+nzioXbZJ7jq6nbmPwR9MJPL53ViLaHZAfLi8ZMMylnTpTQIDAQABAkAxutCjxVbDR+X+ZfUW9r+VlDLWkHTlWVd+4qS9R7pJ736TxWEUmIUmE2afnb8vnBdgj9UYtVSQzcrAAXtyj7cBAiEA8gQ+6krg265jIsh+Ka4JlDOyGl7IsuPj2smlD/hl7y0CIQC+qAOUY9+bu+Fx07qp70p6EaK8sGSt7mySzpBIVT42oQIgI9xrMi5cOVES0YGh6C0osDQkzPGEjbVk+vNsKPymphUCIQCON1nkONO1oWI7vEpDNKIDiGAyDAoty+nlYHwz2JI9wQIgcedQHdqcUafeayLqHhcXUVIb5/x14v91r0qYGOqcrcE=";

    /**非对称密钥算法
     * 加密方式，标准jdk的
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    //公钥
    private static final String PUBLIC_KEY = "RSAPublicKey";

    //私钥
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {
        //实例化密钥生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器
        keyPairGenerator.initialize(KEY_SIZE);
        //生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        //甲方私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;

    }


    /**
     * 私钥加密
     *
     * @param data 待加密数据
     * @return byte[] 加密数据
     */
    public static String encryptByPrivateKey(byte[] data) throws Exception {

        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        System.out.println("------------------------:");
        return Base64.encodeBase64String(cipher.doFinal(data));
//        return Base64.encode(cipher.doFinal(data),"UTF-8");
    }
    
    /**
     * 私钥加密
     * @param data
     * @param privateKeyStr
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(byte[] data,String privateKeyStr) throws Exception {
    	if(CheckUtil.isBlank(privateKeyStr)) {
    		return encryptByPrivateKey(data);
    	}
    	PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        System.out.println("------------------------:");
        return Base64.encodeBase64String(cipher.doFinal(data));
    }

    /**
     * 公钥加密
     *
     * @param data 待加密数据
     * @return byte[] 加密数据
     */
    public static String encryptByPublicKey(String data) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);

        //数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }
    /**
     * 
     * @param data
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(String data,String publicKeyStr) throws Exception {
    	
    	if(CheckUtil.isBlank(publicKeyStr)) {
    		return encryptByPublicKey(data);
    	}
    	//实例化密钥工厂
    	KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    	//初始化公钥
    	//密钥材料转换
    	X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr.getBytes()));
    	//产生公钥
    	PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
    	//数据加密
    	Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    	cipher.init(Cipher.ENCRYPT_MODE, pubKey);
    	return Base64.encodeBase64String(cipher.doFinal(data.getBytes()));
    }

    /**
     * 私钥解密
     *
     * @param data 待解密数据
     * @return byte[] 解密数据
     */
    public static String decryptByPrivateKey(String data) throws Exception {
        //替换掉空格
        String param = data.replaceAll(" ", "+");
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey.getBytes()));
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(param.getBytes())));
    }

    /**
     * 私钥解密
     * @param data
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data,String privateKeyStr) throws Exception {
    	//替换掉空格
    	String param = data.replaceAll(" ", "+");
    	if(CheckUtil.isBlank(privateKeyStr)) {
    		return decryptByPrivateKey(data);
    	}
    	//取得私钥
    	PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr.getBytes()));
    	KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
    	//生成私钥
    	PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
    	//数据解密
    	Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    	cipher.init(Cipher.DECRYPT_MODE, privateKey);
    	return new String(cipher.doFinal(Base64.decodeBase64(param.getBytes())));
    }

    /**
     * 公钥解密
     *
     * @param data 待解密数据
     * @return byte[] 解密数据
     */
    public static String decryptByPublicKey(String data) throws Exception {

        //实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey.getBytes()));
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return new String(cipher.doFinal(Base64.decodeBase64(data.getBytes())));
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //初始化密钥
        //生成密钥对
        /*Map<String, Object> keyMap = RSACoder.initKey();
        //公钥
        byte[] publicKey = RSACoder.getPublicKey(keyMap);

        //私钥
        byte[] privateKey = RSACoder.getPrivateKey(keyMap);
        System.out.println("公钥：" + Base64.encode(publicKey,"UTF-8"));
        System.out.println("私钥：" + Base64.encode(privateKey,"UTF-8"));*/

        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "RSA密码交换算法-Send-Test";
        System.out.println("===========甲方向乙方发送加密数据>>>>>>>>>>>>>>>>>");
        System.out.println("原文:" + str);
        //甲方进行数据的加密
        String code1 = RSASecurityTool.encryptByPrivateKey(str.getBytes());
        //System.out.println("加密后的数据：" + Base64.encode(code1,"UTF-8"));
        System.out.println("加密后的数据：" + code1);
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        //乙方进行数据的解密
        String decode1 = RSASecurityTool.decryptByPublicKey(code1);
        System.out.println("乙方解密后的数据：" + new String(decode1));

        System.out.println("<<<<<<<<<<<<<<<<反向进行操作，乙方向甲方发送数据==============");

        str = "{'userName':'lilei','password':'Ll951014'}";

        System.out.println("原文:" + str);

        //乙方使用公钥对数据进行加密
        String code2 = RSASecurityTool.encryptByPublicKey(str);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据：" + code2);

        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");

        //甲方使用私钥对数据进行解密
        String decode2 = RSASecurityTool.decryptByPrivateKey(code2);

        System.out.println("甲方解密后的数据：" + new String(decode2));
    }
}