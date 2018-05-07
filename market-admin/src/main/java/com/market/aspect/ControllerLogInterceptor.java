package com.market.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.catalina.connector.RequestFacade;
import org.apache.catalina.connector.ResponseFacade;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
@Order(1)
public class ControllerLogInterceptor {
	private static final Logger log = LoggerFactory.getLogger(ControllerLogInterceptor.class);
	@Pointcut("execution(* com..*.controller..*(..))")
	private void controllerAspect() {
	}// 请求method前打印内容

	@Before(value = "controllerAspect()")
	public void methodBefore(JoinPoint joinPoint) {
		// 打印请求内容
		try {
			Object[] objs = joinPoint.getArgs();
			List<Object> list = new ArrayList<>();
			for(int i=0; i<objs.length; i++){
				if(!(objs[i] instanceof ExtendedServletRequestDataBinder)  && !(objs[i] instanceof RequestFacade)&& !(objs[i] instanceof ResponseFacade)){
					list.add(objs[i]);
				}
			}
			if(list.size() > 0){
				log.info("\n方法:{}\n参数:{}", joinPoint.getSignature(), JSONObject.toJSONString(list));	
			}
		} catch (Exception e) {
			log.error("AOP methodBefore:", e);
		}
	}

	@AfterReturning(returning = "o", pointcut = "controllerAspect()")
	public void methodAfterReturing(Object o) {
		try {
			if(o != null) log.info("Response内容:" + JSONObject.toJSON(o));
		} catch (Exception e) {
			log.error("AOP methodAfterReturing:", e);
		}
	}
}
				