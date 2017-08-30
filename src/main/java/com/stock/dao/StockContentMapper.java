package com.stock.dao;

import java.util.Map;


/**
 * 个股基本信息维护
 * @author yu_qhai
 *
 */
public interface StockContentMapper {
	
	/**   
	 * @Title: synStockContent   
	 * @Description: 添加或更新个股的基本信息 
	 * @param stockContent
	 * @return
	 * @author  author
	 */
	 
	int synStockContent(Map<String,Object> stockContent);
}
