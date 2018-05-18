package com.market.user.thread;

import com.market.domain.MultiUserFileDomain;
import com.market.user.service.UserImgSer;

public class UserMultiFileThread implements Runnable {

	private UserImgSer fileService;
	
	private MultiUserFileDomain domain;
	
	private String filePath;
	
	private String fileName;

	public UserMultiFileThread(UserImgSer fileService, MultiUserFileDomain domain, String filePath, String fileName) {
		super();
		this.fileService = fileService;
		this.domain = domain;
		this.filePath = filePath;
		this.fileName = fileName;
	}

	public UserImgSer getFileService() {
		return fileService;
	}

	public void setFileService(UserImgSer fileService) {
		this.fileService = fileService;
	}

	public MultiUserFileDomain getDomain() {
		return domain;
	}

	public void setDomain(MultiUserFileDomain domain) {
		this.domain = domain;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void run() {
		fileService.write2Temp(domain, filePath, fileName);
	}
	
}
