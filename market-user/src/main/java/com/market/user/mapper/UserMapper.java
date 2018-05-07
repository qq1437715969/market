package com.market.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.market.bean.UserBean;
import com.market.domain.UserInfo;

@Mapper
public interface UserMapper {

	UserBean queryByPhone(String phone);
	
	UserInfo queryById(String userid);
}
