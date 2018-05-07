package com.market.user.service;

import com.market.domain.CommonRsp;
import com.market.domain.UserInfo;

public interface UserSer {

	public CommonRsp<UserInfo> queryById(String userid);
	
}
