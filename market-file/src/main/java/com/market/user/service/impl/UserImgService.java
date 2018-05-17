package com.market.user.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.market.user.service.UserImgSer;

@Service
public class UserImgService implements UserImgSer {

	@Override
	public void upLoadImg(MultipartFile file, String filePath, String fileName) {
		File saveFile = new File(filePath+fileName);
		InputStream fis = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(saveFile);
			byte[] bytes = new byte[2048];
			fis = file.getInputStream();
			int len = 0;
			while((len=fis.read(bytes))!=-1) {
				fos.write(bytes, 0, len);
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fos!=null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=fis) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
