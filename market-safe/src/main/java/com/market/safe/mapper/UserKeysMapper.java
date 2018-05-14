package com.market.safe.mapper;

import java.util.List;

import com.market.bean.KeysBean;

public interface UserKeysMapper {

	Integer modifyAllRand(Integer rand);
	
	Integer modifyAllLast(Integer last);
	
	Integer addNewKeys(List<KeysBean> keys);
}
