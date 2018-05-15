//package com.market.safe.aspect;
//
//
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSON;
//
//
//
//@Component
//@Aspect
//@Order(value = 4)
//public class Base64RspAspect {
//
//	  private Logger log = LoggerFactory.getLogger("userSafe");
//	
//	  @After("@annotation(com.market.safe.annotion.GetAccessInfo) && args(obj)")
//	  public void before(JoinPoint joinPoint, Object obj) {
//	      Object target = joinPoint.getTarget(); 
//		  System.out.println(JSON.toJSONString(target));
//	  }
//	
//}
