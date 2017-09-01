package com.kidinfor.capture.utils;

/**
 * 抓包工具类
 */
public class StockUtil {
	/**
	 * 字符串转Double
	 * @param str
	 * @return
	 */
	public static Double string2Double(String str){
		if("--".equals(str)) return 0.0;
		try{
			if(!isNull(str)){
				int index;
				if ((index = str.indexOf("万亿")) != -1) {
					return Double.parseDouble(str.substring(0, index)) * 1E12;
				} else if ((index = str.indexOf("亿")) != -1) {
					return Double.parseDouble(str.substring(0, index)) * 1E8;
				} else if ((index = str.indexOf("万")) != -1) {
					return Double.parseDouble(str.substring(0, index)) * 1E4;
				}else if(str.indexOf("%")!=-1){
					return Double.parseDouble(str.replace("%", ""));
				}else {
					return Double.parseDouble(str);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0.0;
	}
	/**
	 * 字符串转Long
	 * @param str
	 * @return
	 */
	public static Long string2Long(String str){
		if("--".equals(str)) return 0L;
		try {
			if(!isNull(str)){
				if(str.indexOf(",")!=-1){
					return Long.parseLong(str.replaceAll(",", ""));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0L;
	}
	
	/**
	 * 获取编号
	 * @param str
	 * @return
	 */
	public static String getCode(String str){
		return str.substring(str.length()-13, str.length()-5);
	}
	
	/**
	 * 是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str){
		if(str==null || str.trim().equals("")) return true;
		return false;
	}
}
