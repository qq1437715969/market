package com.market.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.market.bean.UserBean;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.dto.UserLoginDto;
import com.market.user.mapper.UserMapper;
import com.market.user.service.UserLoginSer;
import com.market.utils.CheckUtil;
import com.market.utils.Md5Utils;

@Service
public class UserLoginService implements UserLoginSer {

	@Autowired
	private UserMapper userMapper;

	@Override
	public CommonRsp<UserLoginDto> login(UserBean bean) {
		CommonRsp<UserLoginDto> resp = new CommonRsp<UserLoginDto>();
		resp.setCode(CodeDict.FAILED.getCode());
		String loginType = bean.getLoginType();
		if(CheckUtil.isBlank(loginType)) {
			String userName = bean.getUserName();
			String checkName = userMapper.checkName(userName);
			if(CheckUtil.isBlank(checkName)) {
				resp.setMsg("用户名不存在请注册");
				return resp;
			}
			UserBean userBean = userMapper.queryById(checkName);
			
		}
		
		
//		String phone = bean.getPhone();
//		UserBean userBean = userMapper.queryByPhone(phone);
//		resp.setCode(CodeDict.FAILED.getCode());
//		if(null==userBean) {
//			resp.setMsg("当前手机号未注册");
//			return resp;
//		}
//		String password = userBean.getPassword();
//		String pass = DigestUtils.md5Hex(password+SecretConfig.USER_SALT);
//		if(pass.equals(userBean.getPassword())) {
//			//TODO 生成token
//			resp.setCode(CodeDict.SUCCESS.getCode());
//			resp.setMsg("登陆成功");
//			//TODO 塞数据
//			UserLoginDto udto = new UserLoginDto();
//			resp.setData(udto);
//			return resp;
//		}
		resp.setMsg("用户名或密码错误");
		return resp;
	}

	@Override
	public CommonRsp loginByName(UserBean bean) {
		CommonRsp resp = new CommonRsp<>();
		resp.setCode(CodeDict.FAILED.getCode());
		String userName = bean.getUserName();
		UserBean dbBean = userMapper.queryByName(userName);
		if(null==dbBean) {
			resp.setMsg("当前用户不存在,请先注册");
			return resp;
		}
		String salt = dbBean.getSalt();
		Integer random = dbBean.getRandom();
		String password = bean.getPassword();
		String pwd = Md5Utils.encryptString(password, salt, random);
		if(!pwd.equals(dbBean.getPassword())) {
			resp.setMsg("用户名或密码错误");
			return resp;
		}
		resp.setCode(CodeDict.SUCCESS.getCode());
		resp.setMsg("登陆成功");
		return resp;
	}

	@Override
	public CommonRsp loginByPhone(UserBean bean) {
		CommonRsp resp = new CommonRsp<>();
		resp.setCode(CodeDict.FAILED.getCode());
		String phone = bean.getPhone();
		UserBean dbBean = userMapper.queryByPhone(phone);
		if(null==dbBean) {
			resp.setMsg("当前用户不存在,请先注册");
			return resp;
		}
		String salt = dbBean.getSalt();
		Integer random = dbBean.getRandom();
		String password = bean.getPassword();
		String pwd = Md5Utils.encryptString(password, salt, random);
		if(!pwd.equals(dbBean.getPassword())) {
			resp.setMsg("用户名或密码错误");
			return resp;
		}
		return null;
	}
	
}
