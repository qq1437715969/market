package com.market.user.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.market.domain.MultiUserFileDomain;
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

	@Override
	public void write2Temp(MultiUserFileDomain domain, String filePath, String fileName) {
		String[] nameArr = fileName.split("\\.");
		String tempPathStr = filePath+"/"+nameArr[0]+"/";
		File path = new File(tempPathStr);
		if(!path.exists()) {
			path.mkdirs();
		}
		String chunk = domain.getChunk();
		String chunks = domain.getChunks();
		File file = new File(tempPathStr+chunk+".temp");
		MultipartFile filePart = domain.getFile();
		writeTemp2File(filePart,file);
	}

	@Override
	public void upLoadMultiParts(MultiUserFileDomain domain, String filePath, String fileName) {
		Long size = domain.getSize();
		String chunks = domain.getChunks();
		String[] nameArr = fileName.split("\\.");
		File path = new File(filePath+"/"+nameArr[0]+"/");
		if(!path.exists()) {
			write2Temp(domain, filePath, fileName);
		}
		String[] childFiles = path.list();
		if(childFiles.length<Integer.parseInt(chunks)-1) {
			write2Temp(domain, filePath, fileName);
		}else {
			write2Temp(domain, filePath, fileName);
			File lastFilePath = new File(filePath);
			readTemp2File(path,lastFilePath,fileName);
		}
	}

	private void readTemp2File(File path, File lastFilePath, String fileName) {
		if(!lastFilePath.exists()) {
			lastFilePath.mkdirs();
		}
		File file = new File(lastFilePath+fileName);
		File[] subFiles = path.listFiles();
		for(File subFile:subFiles) {
			System.out.println(subFile.getName());
		}
	}

	private void writeTemp2File(MultipartFile filePart, File file) {
		FileOutputStream fos = null;
		InputStream in = null;
		try {
			byte[] bytes = new byte[1024];
			in = filePart.getInputStream();
			int len = 0;
			fos = new FileOutputStream(file);
			while((len=in.read(bytes))!=-1) {
				fos.write(bytes, 0, len);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null!=fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
