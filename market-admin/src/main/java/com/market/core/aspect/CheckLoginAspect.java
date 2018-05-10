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

import lombok.extern.slf4j.Slf4j;

/*
 * @author Cliff.ZhouYang
 * @date 2018/3/27 14:54
 */
@Slf4j
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
        String appid = request.getHeader(AdminConstant.APPID);
        String token = request.getHeader(AdminConstant.ACCESSTOKEN);
        Date time = admin.getLoginTime();
        if(CheckUtil.isBlank(appid)||CheckUtil.isBlank(token)) {
        	throw new AdminException("请求参数异常,无法为您服务");
        }
        AdminLoginBean loginBean = (AdminLoginBean)client.get(AdminConstant.ADMIN_ONLINE_PRE+admin.getAdminId());
        if(null==loginBean) {
        	throw new AdminException(admin.getAdminId()+"未登录");
        }
        String random = loginBean.getRandom();
        Date dbLoginTime = loginBean.getLoginTime();
        int compare = time.compareTo(dbLoginTime);
        if(compare!=0) {
        	throw new AdminException(admin.getAdminId()+"登录信息过期");
        }
        String dbAppId = TokenUtils.getAppId(admin.getAdminId(),dbLoginTime, null, random);
        if(!dbAppId.equals(appid)) {
        	throw new AdminException("appId登录失效");
        }
        boolean check = TokenUtils.checkToken(dbAppId, random, token);
        if(!check) {
        	throw new AdminException("登录信息无效");
        }
//        HttpSession session = request.getSession();
//
//        if (bean.getLoginType() == 1) {
//            String appid = request.getHeader("appid");
//            String accessToken = request.getHeader("accessToken");
//            bean.setAppid(appid);
//            bean.setAccessToken(accessToken);
//            if (!tokenAuth(bean)) {
//                throw new UserException(ResultEnum.USERTOKEN_CHECK_ERROR.getCode(), "用户未登录");
//            }
//        } else {
//            String userid = (String) session.getAttribute(PublicConstant.USERID_KEY);
//            String password = (String) session.getAttribute(PublicConstant.PASSWORD_KEY);
//            if (StringUtil.isEmpty(userid) || StringUtil.isEmpty(password)) {
//                throw new UserException(ResultEnum.USER_NOTLOGIN_ERROR.getCode(), "用户未登录");
//            } else {
//                bean.setUid(userid);
//                bean.setCustomid(userid);
//                bean.setPassword(password);
//            }
//        }
        return pjp.proceed();
    }

}
