package com.market.safe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.market.bean.KeysBean;
import com.market.constant.UserConstant;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.safe.mapper.UserKeysMapper;
import com.market.safe.service.UserSecretSer;
import com.market.utils.KeysIdUtils;
import com.market.utils.RSAUtil;

@Service
public class UserSecretService implements UserSecretSer {

		@Autowired
		private UserKeysMapper userKeysMapper;
	
		private final static Integer KEYS_NUM = 6;

		private final static Integer FROZEN_RAND = 0;
		
		private final static Integer OLD_VERSION = -1;
		
		
		
		@Override
		public List<KeysBean> createNewKeyPairs(String operator){
			 List<KeysBean> list = new ArrayList<KeysBean>();
			 StringBuilder sb = new StringBuilder();
			 try {
				for(int i=1;i<=KEYS_NUM;i++) {
					Map<String, Object> keys = RSAUtil.initKey();
					String keysId = KeysIdUtils.createKeyId(UserConstant.USER_KEY_PRE);
					String privateKey = RSAUtil.getPrivateKey(keys);
					String publicKey = RSAUtil.getPublicKey(keys);
					KeysBean keysBean = new KeysBean();
					keysBean.setKeysId(keysId);
					keysBean.setPrivateKey(privateKey);
					keysBean.setPublicKey(publicKey);
					keysBean.setOperator(operator);
					keysBean.setRandom(i);
					if(sb.indexOf(keysId)==-1) {
						list.add(keysBean);
						sb.append(keysId);
					}else {
						i--;
					}
				}
			 } catch (Exception e) {
				e.printStackTrace();
			}
			 return list;
		 }

		@Transactional(rollbackFor=Exception.class)
		@Override
		public CommonRsp<List<KeysBean>> updateKeys(List<KeysBean> list) {
			CommonRsp<List<KeysBean>> resp = new CommonRsp<List<KeysBean>>();
			resp.setCode(CodeDict.FAILED.getCode());
			if(null==list||list.size()==0) {
				resp.setMsg("没有新的密钥对,更新失败");
				return resp;
			}
			userKeysMapper.modifyAllRand(FROZEN_RAND);
			userKeysMapper.modifyAllLast(OLD_VERSION);
			userKeysMapper.addNewKeys(list);
			
			return null;
		}

}
