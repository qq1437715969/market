package com.market.core.aspect;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.market.bean.AdminLoginBean;
import com.market.constant.AdminConstant;
import com.market.core.config.CacheClient;
import com.market.domain.BaseAdminBean;
import com.market.exception.AdminException;
import com.market.utils.CheckUtil;
import com.market.utils.TokenUtils;


/**
 * @author wolf
 * 此处默认使用
 */
@Component
@Aspect
@Order(value = 4)
public class CheckLoginAspect {
//    @Autowired
//    private TokenService tokenService;
	
    @Autowired
    private CacheClient client;

    /**
     * 环绕通知——验证用户是否登录
     *
     * @param pjp
     * @param bean
     * @return
     * @throws Throwable
     */
    
    @Around("@annotation(com.market.core.annotion.AdminCheckLogin) && args(admin)")
    public Object around(ProceedingJoinPoint pjp,BaseAdminBean admin) throws Throwable {
        //获取请求头信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long longTime = admin.getLongTime();
        String ip = admin.getIpAddr();
        if((null==longTime||longTime==0L)&&CheckUtil.isBlank(ip)) {
        	throw new AdminException(admin.getAdminId()+"登陆信息校验错误");
        }
        AdminLoginBean loginBean = (AdminLoginBean)client.get(AdminConstant.ADMIN_ONLINE_PRE+admin.getAdminId());
        if(null==loginBean) {
        	throw new AdminException(admin.getAdminId()+"未登录");
        }
        String appid = request.getHeader(AdminConstant.APPID);
        String appIdBak = request.getHeader(AdminConstant.APPID_BAK);
        String token = request.getHeader(AdminConstant.ACCESSTOKEN);
        String tokenBak = request.getHeader(AdminConstant.ACCESSTOKEN_BAK);
        if(CheckUtil.isBlank(appid)||CheckUtil.isBlank(token)) {
        	throw new AdminException("请求参数异常,无法为您服务");
        }
        Date time = new Date(longTime);
        Date loginTime = loginBean.getLoginTime();
        if(time.compareTo(loginTime)!=0) {
        	throw new AdminException(admin.getAdminId()+"登录信息过期");
        }
        if(CheckUtil.isBlank(ip)) {
        	String randomBak = loginBean.getRandomBak();
        	String app = TokenUtils.getAppId(admin.getAdminId(),loginTime, null, randomBak);
        	String createToken = TokenUtils.createToken(app, randomBak);
        	if(!app.equals(appIdBak)||!createToken.equals(tokenBak)) {
            	throw new AdminException("appId登录失效");
        	}
        }else {
//        	String random = loginBean.getRandom();
//        	String app = TokenUtils.getAppId(admin.getAppid(),loginTime,ip,random);
        	String accessToken = loginBean.getAccessToken();
//        	String createToken = TokenUtils.createToken(app, random);
        	if(!appid.equals(loginBean.getAppId())||!token.equals(accessToken)) {
            	throw new AdminException("appId登录失效");
        	}
        }
	    return pjp.proceed();
    }

}
