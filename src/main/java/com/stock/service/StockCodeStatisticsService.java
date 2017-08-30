package com.stock.service;

import java.util.List;
import java.util.Map;

/**
 * 根据编号查询所有的信息统计
 * @author yu_qhai
 *
 */
public interface StockCodeStatisticsService {
	/**
	 * 查询统计集合
	 * @param code
	 * @return
	 */
	Map<String,Object> getStatistics(String code);
	/**
	 * 年报统计
	 * @param code
	 * @return
	 */
	List<Map<String,Object>> getYearStatistics(String code);
	/**
	 * 月报统计
	 * @param code
	 * @return
	 */
	List<Map<String,Object>> getMonthStatistics(String code);
	/**
	 * 获取个股统计信息
	 * @param code
	 * @return
	 */
	Map<String,Object> getStockSelfMsg(String code);
}
