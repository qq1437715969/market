package com.market.domain;

import org.springframework.web.multipart.MultipartFile;

public class BaseFileDomain {
	
	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
}
