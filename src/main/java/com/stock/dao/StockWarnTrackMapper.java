package com.stock.dao;

import java.util.List;
import java.util.Map;

import com.authority.pojo.Criteria;
import com.stock.pojo.StockCzb;
import com.stock.pojo.StockIncoming;
import com.stock.pojo.StockRestricted;

/**
 * 轨道量能Mapper
 * @author yu_qhai
 *
 */
public interface StockWarnTrackMapper {
	/**
	 * 获取某营业部更新的最后时间
	 * @param type
	 * @return
	 */
	Map<String,Object> selectMaxStockCzbData(String type);
	/**
	 * 批量插入财政部
	 * @param list
	 * @return
	 */
	void saveBatchStockCzb(List<StockCzb> list);
	/**
	 * 更新个股基本信息
	 * @param criteria
	 */
	void updateStockContent(Criteria criteria);
	/**
	 * 更新个股十大流通股东状态
	 * @param criteria
	 * @return
	 */
	int updateHolderState(Criteria criteria);
	/**
	 * 获取最后更新年年份
	 * @return
	 */
	Integer getLastYear();
	/**
	 * 插入个股收益
	 * @param stock
	 * @return
	 */
	int saveIncoming(StockIncoming stock);
	/**
	 * 更新个股限售时间信息
	 * @param model
	 */
	void updateStockRestricted(StockRestricted model);
}
