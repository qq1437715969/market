package com.market.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.market.exception.UserException;



public class MyJsonUtil {
	
	private static Logger log = LoggerFactory.getLogger(MyJsonUtil.class);
	
	private final static String JSON_FILE_DEFAULT_ENCODING = "UTF-8";
	
	public static void write(String filePath,String fileName,String jsonStr) {
		if(CheckUtil.isBlank(filePath)) {
			log.error("传递json生成路径为空,无法继续生成");
			return;
		}
		if(CheckUtil.isBlank(jsonStr)) {
			log.error("json字符串为空,无法生成json");
			return;
		}
		
		File file = new File(filePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		file = new File(filePath+"/"+fileName);
		
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos,JSON_FILE_DEFAULT_ENCODING);
			osw.write(jsonStr);
			fos.flush();
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("json文件生成失败",e);
		}finally {
			if(null!=osw) {
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(null!=fos) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 处理json解析异常
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T>  T parseObject(String jsonStr,Class<T> clazz,Logger logger) {
		T obj = null;
		
		try {
			obj = JSON.parseObject(jsonStr, clazz);
		} catch (Exception e) {
			if(null==logger) {
				log.info("解析json格式异常,jsonStr:{}",jsonStr);
			}else{
				logger.info("解析json格式异常,jsonStr:{}",jsonStr);
			}
			throw new UserException("json解析异常");
		}
		if(null==obj) {
			return null;
		}
		return obj;
	}
	
	public static <T> List<T>  parseArr(String jsonArrStr,Class<T> clazz,Logger logger){
		List<T> list = null;
		try {
			list = JSON.parseArray(jsonArrStr, clazz);
		} catch (Exception e) {
			if(null==logger) {
				log.info("解析json数组格式异常,jsonArrStr:{}",jsonArrStr);
			}else{
				logger.info("解析json数组格式异常,jsonArrStr:{}",jsonArrStr);
			}
			throw new UserException("json数组解析异常");
		}
		if(null==list) {
			return null;
		}
		return list;
	} 
	
}
