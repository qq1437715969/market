package com.market.user.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.market.bean.Product;

@Mapper
public interface ProductMapper {
	
	Product queryById(String pid);
	
}
