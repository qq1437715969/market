package com.market.user.service;

import com.market.bean.UserBean;
import com.market.domain.CommonRsp;
import com.market.dto.UserLoginDto;

public interface UserLoginSer {

	CommonRsp<UserLoginDto> login(UserBean bean);
	
	CommonRsp loginByName(UserBean bean);
	
	CommonRsp loginByPhone(UserBean bean);
	
}
