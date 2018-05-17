package com.market.user.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:filePath.properties")
@ConfigurationProperties("user")
public class FilePathConfig {

	private static String headImgUploadPath;
	
	@PostConstruct
	public void init() {
		List<String> paths = new ArrayList<String>();
		paths.add(headImgUploadPath);
		
		File file;
		for (int i = 0; i < paths.size(); i++) {
			file = new File(paths.get(i));
			if (!file.exists()) {
				file.mkdirs();
			}
		}
	}

	public static String getHeadImgUploadPath() {
		return headImgUploadPath;
	}

	public static void setHeadImgUploadPath(String headImgUploadPath) {
		FilePathConfig.headImgUploadPath = headImgUploadPath;
	}
	
}
