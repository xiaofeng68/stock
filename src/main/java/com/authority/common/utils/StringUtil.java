package com.authority.common.utils;

/**
 * 字符串帮助类
 * @author yu_qhai
 *
 */
public class StringUtil {
	/**
	 * 是否为空
	 * @param msg
	 * @return
	 */
	public static boolean isNull(String msg){
		if(msg==null) return true;
		if("".equals(msg.trim())) return true;
		if("null".equals(msg.trim().toLowerCase())) return true;
		return false;
	}
	public static boolean isNotNull(String msg){
		if(msg==null) return false;
		if("".equals(msg.trim())) return false;
		if("null".equals(msg.trim().toLowerCase())) return false;
		return true;
	}
	public static boolean equals(String str1,String str2){
		if(str1 == str2) return true;
		if(str1!=null){
			return str1.equals(str2);
		}
		return false;
	}
}
