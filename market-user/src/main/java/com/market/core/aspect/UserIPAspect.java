package com.market.core.aspect;

import com.market.core.config.CacheClient;
import com.market.domain.BaseAdminBean;
import com.market.domain.BaseUserBean;
import com.market.utils.IPUtils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * IP切面
 */
@Slf4j
@Component
@Aspect
@Order(value = 1)
public class UserIPAspect {

	@Autowired
	private CacheClient client;
	
    // 配置前置通知
    @Before("@annotation(com.market.core.annotion.UserRealIP) && args(user)")
    public void before(JoinPoint joinPoint, BaseUserBean user) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = IPUtils.getIpAddr(request);
        user.setIpAddr(ip);
    }
    
//    @Before("@annotation(com.market.core.annotion.RealIP) && args(req,resp)")
//    public void before(JoinPoint joinPoint, HttpServletRequest req,HttpServletResponse resp) {
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        String ip = IPUtils.getIpAddr(request);
//        req.setAttribute("ipAddr",ip);
//    }
    
    
}
