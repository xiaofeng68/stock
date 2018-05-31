package com.kidinfor.capture.core.service;

import java.util.Date;
import java.util.List;

import com.kidinfor.capture.core.entity.StockCode;

/**
 * 
 *
 */
public interface StockService {
    void truncateCodes();
	void truncatePrice();
	void truncateHolders();
	void truncateJijinBD();
	void truncateLongtou(Date date);
	/**
	 * 获取基金持仓的个股
	 * @return
	 */
	List<String> findJJCode();
	/**
	 * 获取板块编号
	 * @return
	 */
	List<String> findBKCode(Date date);
	/**
	 * 获取个股基金变动
	 */
	void updateJijinBD(String code) throws Exception;
	/**
	 * 基金持仓
	 */
	void updateJijinCC() throws Exception;
	/**
	 * 概念更新（主体猎手）
	 * @throws Exception
	 */
	void updateGainian() throws Exception;
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
	 * 获取所属板块
	 */
	void updateType(String code,String scode) throws Exception;
	
	/**
	 * 更新板块龙头
	 */
	void updateLongtou(String code)throws Exception;
	/**
	 * 获取所有编码
	 * @return
	 */
	public List<StockCode> getCodes();
}
