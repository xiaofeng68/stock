package com.kidinfor.capture.core.service;

import java.util.List;

import com.kidinfor.capture.core.entity.StockCode;

/**
 * 
 *
 */
public interface StockService {
	void truncatePrice();
	void truncateHolders();
	/**
	 * 数据抓取
	 */
	void updateHolders(String code) throws Exception;
	/**
	 * 代码更新
	 * @return
	 */
	void updateCodes() throws Exception;
	
	/**
	 * 价格更新
	 */
	void updatePrice(String code) throws Exception;
	/**
	 * 获取所有编码
	 * @return
	 */
	public List<StockCode> getCodes();
}
