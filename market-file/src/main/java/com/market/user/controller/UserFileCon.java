package com.market.user.controller;


import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.market.constant.FileConstant;
import com.market.core.config.CacheClient;
import com.market.core.constant.TimeConstant;
import com.market.domain.BaseFileDomain;
import com.market.domain.CodeDict;
import com.market.domain.CommonRsp;
import com.market.domain.UserFileDomain;
import com.market.user.config.FilePathConfig;
import com.market.user.service.UserImgSer;
import com.market.user.thread.UserHeadImgThread;
import com.market.utils.CheckUtil;

@RestController
@RequestMapping("/userFile")
public class UserFileCon {
	
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
