package com.market.safe.aspect;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.market.bean.UserSafeBean;
import com.market.exception.UserException;
import com.market.exception.UtilsException;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;


@Component
@Aspect
@Order(value = 1)
public class HeadersAspect {
	 private Logger log = LoggerFactory.getLogger("userSafe");
	
	 private final static String user_url1 = "http://localhost:16888/Market/";
	 
	 private final static String user_url2 = "http://127.0.0.1:16888/Market/";
	 
	 private final static String user_url3 = "http://192.168.0.111/Market/";
	 
	 private final static String user_url4 = "http://www.market.com/";

	 private static Set<String> userUrls = new HashSet<String>();
	 
	 static {
		 userUrls.add(user_url1);
		 userUrls.add(user_url2);
		 userUrls.add(user_url3);
		 userUrls.add(user_url4);
	 }
	 
	  @Before("@annotation(com.market.safe.annotion.CheckHeaders) && args(safe)")
	  public void before(JoinPoint joinPoint, UserSafeBean safe) {
	        // 接收到请求，记录请求内容
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        String foot = safe.getFoot();
	        if(CheckUtil.isBlank(foot)) {
	        	throw new UserException("头文件信息缺失");
	        }
	        foot = Base64Util.decode(foot);
	        log.info(foot);
	        JSONObject json = JSON.parseObject(foot);
	        String refer = json.getString("refer");
	        int mobile = safe.getMobileType();
	        switch (mobile) {
			case 0:
				checkWebRefer(refer);
				break;
			case 1:
				checkAnZhuoRefer(refer);
			default:
				break;
			}
	        
	  }

	private void checkAnZhuoRefer(String refer) {
		
	}

	private void checkWebRefer(String refer) {
		if(CheckUtil.isBlank(refer)) {
			throw new UserException("来源不明确");
		}
		refer = refer.length()>21?refer.substring(0,21):refer.substring(0, refer.length());
		int allowCount = 0;
		for(String allow:userUrls) {
			if(allow.contains(refer)) {
				break;
			}
			allowCount += 1;
		}
		if(allowCount==userUrls.size()) {
			throw new UserException("来源错误");
		}
	}
}
