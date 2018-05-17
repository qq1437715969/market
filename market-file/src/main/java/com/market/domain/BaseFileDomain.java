package com.market.domain;

import org.springframework.web.multipart.MultipartFile;

public class BaseFileDomain {
	
	private MultipartFile file;
	
	private Long size;

	private String filePathName;
	
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}
	
}
