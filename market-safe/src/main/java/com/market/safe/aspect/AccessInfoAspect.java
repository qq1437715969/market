package com.market.safe.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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
import com.market.domain.UserAccessInfo;
import com.market.exception.UserException;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.IPUtils;
import com.market.utils.Md5Utils;


@Component
@Aspect
@Order(value = 1)
public class AccessInfoAspect {

	  private Logger log = LoggerFactory.getLogger("userSafe");
	
	  @Before("@annotation(com.market.safe.annotion.GetAccessInfo) && args(safe)")
	  public void before(JoinPoint joinPoint, UserSafeBean safe) {
	        // 接收到请求，记录请求内容
	        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        HttpServletRequest request = attributes.getRequest();
	        String ip = IPUtils.getIpAddr(request);
	        String sign = safe.getSign();
			String info = safe.getInfo();
			if(CheckUtil.isBlank(info)||CheckUtil.isBlank(sign)) {
				throw new UserException("参数不足,无法服务");
			}
			if(info.indexOf(".")==-1) {
				throw new UserException("参数错误,无法服务");
			}
			if(sign.indexOf(".")==-1) {
				throw new UserException("参数错误,无法服务");
			}
			Long accessTime = safe.getAccessTime();
			Date time = new Date(accessTime);
			Date now = new Date();
			if(time.after(now)) {
				throw new UserException("时间错误,无法服务");
			}
			UserAccessInfo reqUserInfo = parseSafeBean(safe, UserAccessInfo.class);
			log.info(reqUserInfo.toJson());
			safe.setLastSafeTime(System.currentTimeMillis());
			safe.setData(reqUserInfo);
			safe.setInfo(null);
			safe.setSign(null);
	  }

	private <T> T parseSafeBean(UserSafeBean safe,Class<T> clazz) {
		String info = safe.getInfo();
		String sign = safe.getSign();
		String[] infoArr = info.split("\\.");
		String[] signArr = sign.split("\\.");
		log.info(infoArr.length+"");
		log.info(signArr.length+"");
		if(infoArr.length!=2||signArr.length!=2) {
			throw new UserException("参数错误,无法服务");
		}
		String secret = signArr[1];
		String newSign = Md5Utils.encryptString(info, secret, 5);
		if(!newSign.equals(signArr[0])) {
			throw new UserException("user安全校验失败,签名失效");
		}
		String string = Base64Util.decode(infoArr[0]);
//		log.info(string);
		String comInfo = Base64Util.decode(infoArr[1]);
//		log.info(comInfo);
		JSONObject json = JSONObject.parseObject(comInfo);
		String ipAddrStr = json.getString("ipAddr");	
		String ipAddr = Base64Util.decode(ipAddrStr);
//		log.info(ipAddr);
		return JSON.parseObject(ipAddr,clazz);
	}
	
	
}
