package com.market.user.service;


import org.springframework.web.multipart.MultipartFile;

public interface UserImgSer extends FileService {

	void upLoadImg(MultipartFile file,String filePath,String fileName);
	
}
