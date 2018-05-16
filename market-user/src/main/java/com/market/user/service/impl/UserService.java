package com.market.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.bean.UserAccessInfoBean;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.IpInfoDomain;
import com.market.domain.SystemDomain;
import com.market.domain.UserInfo;
import com.market.domain.UserRegistBean;
import com.market.domain.UserRegistDomain;
import com.market.exception.UserException;
import com.market.user.mapper.UserMapper;
import com.market.user.mapper.UserRegSysMapper;
import com.market.user.service.UserSer;
import com.market.utils.Base64Util;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;
import com.market.utils.MyJsonUtil;
import com.market.utils.SaltUtils;
import com.market.utils.UserIdMaker;

@Service
public class UserService implements UserSer {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserRegSysMapper regSysMapper;
	
	@Override
	public CommonRsp<UserInfo> queryById(String userid) {
		CommonRsp<UserInfo> resp = new CommonRsp<UserInfo>();
		UserInfo userInfo = userMapper.queryById(userid);
		resp.setCode(CodeDict.FAILED.getCode());
		if(null==userInfo) {
			resp.setMsg("查询无果");
			return resp;
		}
		resp.setCode(CodeDict.SUCCESS.getCode());
		resp.setMsg(CodeDict.SUCCESS.getDesc());
		resp.setData(userInfo);
		return resp;
	}

	@Override
	public boolean checkNameExist(String userName) {
		if(CheckUtil.isBlank(userName)) {
			throw new UserException("参数为空");
		}
		String name = userMapper.checkName(userName);
		if(CheckUtil.isBlank(name)) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void createUser(UserRegistDomain register,UserRegistBean user) {
		String userName = register.getUserName();
		String pass = register.getPass();
		Integer random = register.getRandom();
		if(CheckUtil.isBlank(userName)||CheckUtil.isBlank(pass)) {
			throw new UserException("用户名或密码为空");
		}
		if(checkNameExist(userName)) {
			throw new UserException("用户名已存在,无法注册");
		}
		String salt = SaltUtils.createSecSalt(userName, pass);
		pass = Md5Utils.encryptString(pass, salt, random);
		register.setSalt(salt);
		register.setPass(pass);
		register.setUserId(UserIdMaker.newUserId(userName));
		Integer userAdd = userMapper.addNewUser(register);
		if(userAdd!=1) {
			throw new UserException("用户注册失败1");
		}
		String ipInfo = user.getIpInfo();
		ipInfo = Base64Util.decode(ipInfo);
		String sysInfo = user.getSysInfo();
		sysInfo = Base64Util.decode(sysInfo);
		SystemDomain sys = MyJsonUtil.parseObject(sysInfo, SystemDomain.class, null);
		IpInfoDomain ip = MyJsonUtil.parseObject(ipInfo, IpInfoDomain.class,null);
		UserAccessInfoBean userInfo = new UserAccessInfoBean();
		userInfo.setUserId(register.getUserId());
		userInfo.setReqIp(user.getIpAddr());
		com.market.utils.MyBeanUtils.copyPropertiesIgnoreNull(sys, userInfo);
		com.market.utils.MyBeanUtils.copyPropertiesIgnoreNull(ip, userInfo);
		Integer regAdd = regSysMapper.addNew(userInfo);
		if(regAdd!=1) {
			throw new UserException("用户注册失败2");
		}
	}
	
	
}
