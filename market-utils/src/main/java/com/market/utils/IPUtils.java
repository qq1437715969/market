package com.market.utils;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IPUtils {
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;
    }
	
	/**
     * 获取配置文件中的IP信息
     * 
     * @param path
     * @param xmlFile
     * @return
     */
    public static Map<String, List<String>> getXmlInfo(String path, String xmlFile) {
        Map<String, List<String>> ipMap = new HashMap<String, List<String>>();
        List<String> proxyIpAddrs = new ArrayList<String>();
        List<String> ipHeaders = new ArrayList<String>();
        List<String> localareaIps = new ArrayList<String>();
        String str = "addr,header,local";
        List<Element> nodes = getTable(path, xmlFile, str);

        for (Element node : nodes) {
            proxyIpAddrs.add(node.getTextTrim());
        }

        for (Element node : nodes) {
            ipHeaders.add(node.getTextTrim());
        }

        for (Element node : nodes) {
            localareaIps.add(node.getTextTrim());
        }

        ipMap.put("proxyIpAddrs", proxyIpAddrs);
        ipMap.put("ipHeaders", ipHeaders);
        ipMap.put("localareaIps", localareaIps);
        return ipMap;
    }
    
    /**
     * 获取IP地址(匹配配置文件内容,有则匹配,无则获取当前访问IP)
     * 
     * @param request
     * @return
     */
    public static String getRealIp(HttpServletRequest request, String path, String xmlFile) {
        
        Map<String, List<String>> xmlInfo = getXmlInfo(path,xmlFile);
        List<String> proxyIpAddrs = xmlInfo.get("proxyIpAddrs");
        List<String> ipHeaders = xmlInfo.get("ipHeaders");
        List<String> localareaIps = xmlInfo.get("localareaIps");

        String ipStr = null;
        String[] ipAddrs = null;
        String clientIp = "";

        for (String ipHeader : ipHeaders) {
            ipStr = request.getHeader(ipHeader);
            if (isUnknownIp(ipStr)) {
                continue;
            }
            System.out.println(ipHeader + "=" + ipStr);
            ipAddrs = ipStr.split(",");
            for (String ipAddr : ipAddrs) {
                // 第一个ip就是代理ip则不再使用这个头信息
                if (proxyIpAddrs.contains(ipAddr)) {
                    clientIp = "";
                    break;
                }
                clientIp = ipAddr;
                // ip是局域网ip则读取同一头信息里的下一个ip
                for (String localareaIp : localareaIps) {
                    if (clientIp.startsWith(localareaIp)) {
                        clientIp = "";
                        break;
                    }
                }
                if (!isUnknownIp(clientIp)) {
                    break;
                }
            }
            if (!isUnknownIp(clientIp)) {
                break;
            }
        }
        if (isUnknownIp(clientIp)) {
            if (!proxyIpAddrs.contains(request.getRemoteAddr())) {
                clientIp = request.getRemoteAddr();
            }
        }
        return clientIp;
    }
    
    /**
     * 判断是否是未知IP
     * 
     * @param ipStr
     * @return
     */
    public static boolean isUnknownIp(String ip) {
        return CheckUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip) || "null".equalsIgnoreCase(ip);
    }
    
    /**
     * 获取配置文件中IP最大访问次数
     * @param path
     * @param xmlFile
     * @param tableKey 标签名
     * @return IP最大访问次数
     */
    public static int getMaxAccessTimes(String path, String xmlFile , String tableKey) {//tableKey:maxAccessTimes
        int maxAccessTimes = 0;
        List<Element> nodes = getTable(path, xmlFile, tableKey);
        if(nodes != null && nodes.size() > 0){
            maxAccessTimes = Integer.parseInt(nodes.get(0).getTextTrim());
        }
        return maxAccessTimes;
    }
    
    /**
     * 读取配置文件,获取配置文件中的所有结点
     * 
     * @param path 配置文件存放路径
     * @param xmlFile 配置文件名
     * @param str 关键词之间用逗号隔开
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<Element> getTable(String path, String xmlFile, String str) {
        File file = new File(path, xmlFile);
        if (file != null && file.exists()) {
            try {
                SAXReader reader = new SAXReader();
                Document document = reader.read(file);
                org.dom4j.Element root = document.getRootElement();
                String[] strs = str.split(",");
                List<Element> nodes = null;
                for (int i = 0; i < strs.length; i++) {// 同类型标签
                    nodes = root.elements(strs[i]);
                }
                return nodes;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
