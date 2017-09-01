package com.kidinfor.capture.core.service;

/**
 * 
 *
 */
public interface StockService {
	/**
	 * 数据抓取
	 */
	void updateHolders(String code) throws Exception;
	/**
	 * 代码更新
	 * @return
	 */
	void updateCodes() throws Exception;
}
