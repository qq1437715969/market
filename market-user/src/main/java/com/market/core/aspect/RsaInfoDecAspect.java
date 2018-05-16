package com.market.core.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.market.bean.KeysBean;
import com.market.constant.CommonConstant;
import com.market.constant.UserConstant;
import com.market.core.config.CacheClient;
import com.market.domain.BaseUserBean;
import com.market.exception.UserException;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;
import com.market.utils.RSASecurityTool;

@Component
@Aspect
@Order(value =1)
public class RsaInfoDecAspect {
	
	@Autowired
	private CacheClient client;
	
	@Before("@annotation(com.market.core.annotion.RsaInfoDec) && args(user)")
	public void before(JoinPoint joinPoint, BaseUserBean user) {
	    // 接收到请求，记录请求内容
	    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	    String info = user.getInfo();
		String sign = user.getSign();
		if(CheckUtil.isBlank(info)||CheckUtil.isBlank(sign)) {
			throw new UserException("用户信息缺失");
		}
		if(info.indexOf(CommonConstant.DOT_STR)==-1) {
			throw new UserException("用户信息缺失");
		}
		String[] infoArr = info.split("\\"+CommonConstant.DOT_STR);
		if(infoArr.length!=UserConstant.USER_REGIST_INFO_DOT_NUM) {
			throw new UserException("用户信息缺失");
		}
		String salt = infoArr[2];
		String random = infoArr[1];
		Integer nums = Integer.parseInt(Base64Util.decode(random));
		nums = nums%6;
		String baseInfo = info.substring(0, info.lastIndexOf(CommonConstant.DOT_STR));
		String backSign = Md5Utils.encryptString(baseInfo, salt, nums);
		if(!backSign.equals(sign)) {
			throw new UserException("用户信息被篡改");
		}
		user.setRandom(nums);
		String jsonStr = infoArr[0];
		try {
			KeysBean keysBean = (KeysBean)client.get(UserConstant.USER_KEY_PRE+(nums));
			String privateKey = keysBean.getPrivateKey();
			info = RSASecurityTool.decryptByPrivateKey(jsonStr, privateKey);
			user.setInfo(info);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
