package com.stock.service;

import java.util.List;

import com.authority.pojo.Criteria;
import com.stock.pojo.StockCzb;
import com.stock.pojo.StockTrackWarn;
import com.stock.pojo.StockWarn;

/**
 * 轨道量能服务
 * @author yu_qhai
 *
 */
public interface StockWarnTrackService {
	/**
	 * 保存预警信息
	 * 
	 * @param model
	 */
	public String saveWarnStock(StockWarn model);
	/**
	 * 保存轨道预警信息
	 * @param model
	 */
	public String saveWarnStock(StockTrackWarn model);
	/**
	 * 更新轨道信息
	 * @param code
	 * @param model
	 * @return
	 */
	public String updateTrace(String code,StockTrackWarn model);
	/**
	 * 更新涨跌天数
	 * @param code
	 * @param days
	 * @return
	 */
	public String saveDays(String code,int days);
	/**
	 * 获取某个营业部操作的个股
	 * @return
	 */
	public List<StockCzb> getPageCzbStocks(String yyb);
	/**
	 * 批量插入财政部操作个股信息
	 * @param list
	 * @return
	 */
	public void saveBatchStockCzb(List<StockCzb> list);
	/**
	 * 更新个股基本信息
	 * @param code
	 */
	public void updateStockCoreContent(String code);
	/**
	 * 更新十大流通股东状态为出逃
	 * @param criteria
	 */
	public void updateHolderState(Criteria criteria);
	/**
	 * 根据个股编号获取十大流通股东信息
	 * @param code
	 */
	public List<String> updateStockHolders(String code);
	/**
	 * 获取最后更新年年份
	 * @return
	 */
	public int getLastYear();
	/**
	 * 获取要更新的个股收益
	 * @param code
	 * @param year
	 * @return
	 */
	public int updateIncoming(String code,int year);
	/**
	 * 更新个股限售时间
	 * @param code
	 */
	public void updateStockRestricted(String code);
}
