package com.market.admin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.domain.CodeDict;
import com.market.domain.Response;

@RestController
@RequestMapping("/admin")
public class HelloCon {
	
	@RequestMapping("/alive.do")
	public Response response(String from) {
		 Response response = new Response();
		 response.setCode(CodeDict.SUCCESS.getCode());
		 response.setMsg(CodeDict.SUCCESS.getDesc());
		 return response;
	}
}
