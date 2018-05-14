package com.market.safe.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.market.bean.KeysBean;

@Mapper
public interface UserKeysMapper {

	Integer modifyAllRand(Integer rand);
	
	Integer modifyAllLast(Integer last);
	
	List<KeysBean> userViewKeys(Integer num);
	
	Integer addNewKeys(@Param("keys")List<KeysBean> keys);
}
