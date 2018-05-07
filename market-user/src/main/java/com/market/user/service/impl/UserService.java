package com.market.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.UserInfo;
import com.market.user.mapper.UserMapper;
import com.market.user.service.UserSer;

@Service
public class UserService implements UserSer {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public CommonRsp<UserInfo> queryById(String userid) {
		CommonRsp<UserInfo> resp = new CommonRsp<UserInfo>();
		UserInfo userInfo = userMapper.queryById(userid);
		resp.setCode(CodeDict.FAILED.getCode());
		if(null==userInfo) {
			resp.setMsg("查询无果");
			return resp;
		}
		resp.setCode(CodeDict.SUCCESS.getCode());
		resp.setMsg(CodeDict.SUCCESS.getDesc());
		resp.setData(userInfo);
		return resp;
	}
	
}
