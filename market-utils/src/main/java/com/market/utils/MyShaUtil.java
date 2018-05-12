package com.market.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class MyShaUtil {

	private static	ScriptEngineManager manager = new ScriptEngineManager();
	private static  ScriptEngine engine = manager.getEngineByName("javascript");
	private static String jsPath = "/opt/js/Sha.js";
	
	static {
		try {
			FileReader fr = new FileReader(jsPath);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder sb = new StringBuilder();
			String temp = null;
			while((temp=br.readLine())!=null) {
				sb.append(temp);
			}
			temp = sb.toString();
			engine.eval(temp);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public static String HMACSHA256(String key,String data) throws ScriptException {
		 return (String)engine.eval("hex_hmac_sha256("+key+","+data+")");
	}
	
}
