package com.market.safe.aspect;

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
	
	 private String from = "http://127.0.0.1:8020/Market/";
	 
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
	        if(refer.indexOf("?")!=-1) {
	        	if(refer.indexOf(from)==-1) {
	        		throw new UserException("请求来源有误无法服务");
	        	}
	        }else {
	        	throw new UserException("参数缺失无法服务");
	        }
	  }
}
