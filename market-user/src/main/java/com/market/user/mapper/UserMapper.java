package com.market.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.market.bean.UserBean;
import com.market.domain.UserRegistDomain;

@Mapper
public interface UserMapper {

	UserBean queryByPhone(String phone);
//	
	UserBean queryById(String userid);
	
	UserBean queryByName(String userName);
	
	@Select("select  userId from tb_user where userName = #{userName}")
	@ResultType(java.lang.String.class)
	String checkName(@Param("userName")String userName);
	
//	UserBean queryByName(String userName);
	
	Integer addNewUser(UserRegistDomain user);
}
