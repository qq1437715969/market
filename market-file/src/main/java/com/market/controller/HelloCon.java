package com.market.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.market.domain.Response;

@RestController
@RequestMapping("/file")
public class HelloCon {

	@RequestMapping("/hello.do")
	public Response fileHello() {
		return new Response();
	}
}
