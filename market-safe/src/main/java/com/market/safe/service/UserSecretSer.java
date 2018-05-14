package com.market.safe.service;

import java.util.List;

import com.market.bean.KeysBean;
import com.market.domain.CommonRsp;

public interface UserSecretSer {
	
//	List<KeysBean> createNewKeyPairs(String operator);
	
	/**
	 * 操作
	 * @param list
	 * @return
	 */
//	CommonRsp<List<KeysBean>> updateKeys(List<KeysBean> list);

	CommonRsp<List<KeysBean>> adminUpdateKeys(String admin);
	
	CommonRsp<List<KeysBean>> userViewKeys();	
}
