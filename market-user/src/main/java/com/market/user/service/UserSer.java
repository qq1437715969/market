package com.market.user.service;

import com.market.bean.UserBean;
import com.market.domain.CommonRsp;
import com.market.domain.UserInfo;
import com.market.domain.UserRegistBean;
import com.market.domain.UserRegistDomain;

public interface UserSer {

	public CommonRsp<UserBean> queryById(String userid);
	
	boolean checkNameExist(String userName);

	public void createUser(UserRegistDomain register, UserRegistBean user);
	
}
