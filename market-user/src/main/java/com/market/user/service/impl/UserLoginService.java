package com.market.user.service.impl;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.market.bean.UserBean;
import com.market.config.SecretConfig;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.dto.UserLoginDto;
import com.market.user.mapper.UserMapper;
import com.market.user.service.UserLoginSer;

@Service
public class UserLoginService implements UserLoginSer {

	@Autowired
	private UserMapper userMapper;

	@Override
	public CommonRsp<UserLoginDto> login(UserBean bean) {
		CommonRsp<UserLoginDto> resp = new CommonRsp<UserLoginDto>();
		String phone = bean.getPhone();
		UserBean userBean = userMapper.queryByPhone(phone);
		resp.setCode(CodeDict.FAILED.getCode());
		if(null==userBean) {
			resp.setMsg("当前手机号未注册");
			return resp;
		}
		String password = userBean.getPassword();
		String pass = DigestUtils.md5Hex(password+SecretConfig.USER_SALT);
		if(pass.equals(userBean.getPassword())) {
			//TODO 生成token
			resp.setCode(CodeDict.SUCCESS.getCode());
			resp.setMsg("登陆成功");
			//TODO 塞数据
			UserLoginDto udto = new UserLoginDto();
			resp.setData(udto);
			return resp;
		}
		resp.setMsg("用户名或密码错误");
		return resp;
	}
	
	
}
