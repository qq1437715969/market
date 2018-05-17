package com.market.user.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.market.domain.BaseFileDomain;

@RestController
@RequestMapping("/userFile")
public class UserFileCon {
	
	@PostMapping("/upLoadHeadImg.do")
	public void upLoadHeadImg(BaseFileDomain domain) {
		MultipartFile file = domain.getFile();
		String name = file.getName();
		String contentType = file.getContentType();
		long size = file.getSize();
		String originalFilename = file.getOriginalFilename();
		System.out.println(name+" -- "+contentType+" -- "+size+" -- "+originalFilename);
	}
	
	
}
