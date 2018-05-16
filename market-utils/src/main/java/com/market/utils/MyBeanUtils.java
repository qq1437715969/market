package com.market.utils;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class MyBeanUtils {

	/**
	 * 或略的copy属性
	 */
	private static Set<String> ignoreSet = new HashSet<String>();
	
	static {
		ignoreSet.add("yzmType");
		ignoreSet.add("source");
		ignoreSet.add("mobileType");
	}
	
	public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
        	String name = pd.getName();
            Object srcValue = src.getPropertyValue(name);
            if (srcValue == null) emptyNames.add(name);
            if(ignoreSet.contains(name)) {
            	if(srcValue.equals(0)) {
            		emptyNames.add(name);
            	}
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target){
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }
}
