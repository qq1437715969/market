package com.market.core.aspect;

import com.market.domain.BaseAdminBean;
import com.market.utils.IPUtils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * IP切面
 */
@Slf4j
@Component
@Aspect
@Order(value = 1)
public class IPAspect {

    // 配置前置通知
    @Before("@annotation(com.market.core.annotion.RealIP) && args(admin)")
    public void before(JoinPoint joinPoint, BaseAdminBean admin) {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = IPUtils.getIpAddr(request);
        admin.setIpAddr(ip);
    }
}
