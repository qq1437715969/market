package com.market.safe.service;

import java.util.List;

import com.market.bean.KeysBean;
import com.market.domain.CommonRsp;

public interface UserSecretSer {
	
	List<KeysBean> createNewKeyPairs(String operator);
	
	
	CommonRsp<List<KeysBean>> updateKeys(List<KeysBean> list);
	
}
