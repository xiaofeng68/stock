package com.stock.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

/**
 * 编号查询统计
 * @author yu_qhai
 *
 */
public interface StockCodeStatisticsMapper {
	/**
	 * 查询{}基本信息
	 * @param code
	 * @return
	 */
	@MapKey("code")
	Map<String, Map<String,Object>> getStockContent(String code);
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
	@MapKey("code")
	Map<String, Map<String,Object>> getStockSelfMsg(String code);
}
