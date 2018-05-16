package com.market.user.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.market.bean.UserAccessInfoBean;

@Mapper
public interface UserRegSysMapper {
	
	Integer addNew(@Param("info")UserAccessInfoBean info);
	
}
