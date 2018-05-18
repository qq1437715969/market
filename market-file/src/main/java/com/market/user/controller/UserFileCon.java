package com.market.user.controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.market.constant.FileConstant;
import com.market.core.config.CacheClient;
import com.market.core.constant.TimeConstant;
import com.market.domain.BaseFileDomain;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.MultiUserFileDomain;
import com.market.domain.UserFileDomain;
import com.market.user.config.FilePathConfig;
import com.market.user.service.UserImgSer;
import com.market.user.thread.UserHeadImgThread;
import com.market.user.thread.UserMultiFileThread;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/userFile")
public class UserFileCon {
	
	private Logger log = LoggerFactory.getLogger(UserFileCon.class);
	
	private  ExecutorService executors = Executors.newSingleThreadExecutor();
	
	@Autowired
	private CacheClient client;
	
	@Autowired
	private UserImgSer userImgService;
	
	@PostMapping("/upLoadHeadImg.do")
	public CommonRsp<String> upLoadHeadImg(BaseFileDomain domain) {
		MultipartFile file = domain.getFile();
		String name = file.getName();
		String contentType = file.getContentType();
		long size = file.getSize();
		String originalFilename = file.getOriginalFilename();
		System.out.println(name+" -- "+contentType+" -- "+size+" -- "+originalFilename);
		String filePath = FilePathConfig.getHeadImgUploadPath();
		String fileName = "test.jpg";
		UserHeadImgThread thread = new UserHeadImgThread(userImgService,file,filePath,fileName);
		executors.execute(thread);
		CommonRsp<String> resp = new CommonRsp<String>();
		resp.setCode(CodeDict.SUCCESS.getCode());
		resp.setMsg("上传中,请等待");
		resp.setData(filePath+fileName);
		UserFileDomain ufd = new UserFileDomain();
		ufd.setFilePathName(filePath+fileName);
		ufd.setSize(size);
		String random = UUID.randomUUID().toString().replaceAll("-","").trim();
		client.set("USER_IMG_"+random.substring(8),ufd);
		return resp;
	}
	
	@PostMapping("/upLoadMultiFiles.do")
	public CommonRsp<String> upLoadHeadImg(MultiUserFileDomain domain,String lastModifiedDate,String name){
//		userImgService.upLoadMultiParts(domain, "/data/market/file/temp/", name);
//		userImgService.write2Temp(domain,"/data/market/file/temp/", name);
		String chunks = domain.getChunks();
		String chunk = domain.getChunk();
		if(CheckUtil.isBlank(chunks)||CheckUtil.isBlank(chunk)) {
			return null;
		}
		String path = "/data/market/file/temp/";
		append(domain,path,name);
		return null;
	}
	
	
	private void append(MultiUserFileDomain domain, String pathStr, String name) {
		String chunk = domain.getChunk();
		String chunks = domain.getChunks();
		InputStream in = null;
		byte[] bytes = new byte[1024];
		String[] nameArr = name.split("\\.");
		String fileName = pathStr+nameArr[0]+".temp";
		File file = new File(fileName);
		if(chunk.equals("0")) {
			File path = new File(pathStr);
			if(!path.exists()) {
				path.mkdirs();
			}
			FileOutputStream fos = null;
			try {
				in = domain.getFile().getInputStream();
				int len = 0;
				fos = new FileOutputStream(file, true);
				while((len=in.read(bytes))!=-1) {
					fos.write(bytes, 0, len);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					fos.flush();
					fos.close();
					if(null!=in) {
						in.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}else {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(file, true);
				in = domain.getFile().getInputStream();
				int len = 0;
				while((len=in.read(bytes))!=-1) {
					fos.write(bytes, 0, len);
				}
				fos.flush();
				fos.close();
				if((Integer.parseInt(chunk)+1)==(Integer.parseInt(chunks))) {
					File lastFile = new File(pathStr+name);
					FileUtils.copyFile(file, lastFile);
					file.delete();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping("/checkUpload.do")
	public CommonRsp<String> checkImgUpLoad(String random){
		CommonRsp<String> resp = new CommonRsp<String>();
		resp.setCode(CodeDict.FAILED.getCode());
		if(CheckUtil.isBlank(random)) {
			resp.setMsg("必要参数缺失,请求异常");
			return resp;
		}
		UserFileDomain domain = (UserFileDomain)client.get("USER_IMG_"+random);
		if(null==domain) {
			resp.setMsg("暂无该图片相关信息");
			return resp;
		}
		resp.setCode(CodeDict.SUCCESS.getCode());
		if(domain.getState()==FileConstant.UPLOAD_FILE_SUCCESS_STATE) {
			resp.setMsg("上传完成");
			return resp;
		}
		Long size = domain.getSize();
		String filePathName = domain.getFilePathName();
		File file = new File(filePathName);
		if(file.exists()) {
			long total = file.getTotalSpace();
			if(total<size) {
				resp.setData("上传中");
				float percent = total/size;
				resp.setData(percent+"%");
				return resp;
			}else {
				domain.setState(FileConstant.UPLOAD_FILE_SUCCESS_STATE);
				resp.setMsg("上传完成");
				client.set("USER_IMG_"+random, domain,TimeConstant.MINUTE_5);
				return resp;
			}
		}else {
			resp.setData("上传中,请耐心等待");
			return resp;
		}
	}
	
	
}
