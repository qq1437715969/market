package com.market.user.thread;

import org.springframework.web.multipart.MultipartFile;
import com.market.user.service.UserImgSer;


public class UserHeadImgThread implements Runnable {

	private UserImgSer fileService;
	
	private MultipartFile file;
	
	private String filePath;
	
	private String fileName;
	
	public UserHeadImgThread(UserImgSer fileService, MultipartFile file, String filePath, String fileName) {
		super();
		this.fileService = fileService;
		this.file = file;
		this.filePath = filePath;
		this.fileName = fileName;
	}


	@Override
	public void run() {
		fileService.upLoadImg(file, filePath, fileName);
	}

}
