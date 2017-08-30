package com.stock.runnable;

public class StockUtils {
	/**
	 * 指数不做监控处理
	 * 
	 * @param code
	 * @return
	 */
	public static boolean isStock(String code) {
		if ("399001".equals(code) || "399300".equals(code)
				|| "999999".equals(code) || "399006".equals(code)) {
			return false;
		}
		return true;
	}
}
