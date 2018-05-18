package com.market.user.service;


import org.springframework.web.multipart.MultipartFile;

import com.market.domain.MultiUserFileDomain;

public interface UserImgSer extends FileService {

	void upLoadImg(MultipartFile file,String filePath,String fileName);
	
	void write2Temp(MultiUserFileDomain domain, String filePath, String fileName);
	
	void upLoadMultiParts(MultiUserFileDomain domain, String filePath, String fileName);
}
