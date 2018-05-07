package com.market.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.domain.CodeDict;
import com.market.domain.Response;

@RestController
@RequestMapping("/user")
public class HelloCon {

	@PostMapping("/alive.do")
	public Response response() {
		 Response response = new Response();
		 response.setCode(CodeDict.SUCCESS.getCode());
		 response.setMsg(CodeDict.SUCCESS.getDesc());
		 return response;
	}
	
}
