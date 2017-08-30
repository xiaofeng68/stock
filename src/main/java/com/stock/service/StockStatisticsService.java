package com.stock.service;

import java.util.List;
import java.util.Map;

import com.authority.pojo.Criteria;
import com.authority.pojo.Tree;
import com.stock.pojo.StockPriceModel;
import com.stock.pojo.Trackcycle;

/**
 * 个股统计
 * @author yu_qhai
 *
 */
public interface StockStatisticsService {
	/**
	 * 年报查询
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockStatistics(Criteria criteria);
	/**
	 * 年报统计数量
	 * @param criteria
	 * @return
	 */
	int countStockStatistics(Criteria criteria);
	
	/**
	 * 月度查询
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockMonthStatistics(Criteria criteria);
	/**
	 * 月底统计数量
	 * @param criteria
	 * @return
	 */
	int countStockMonthStatistics(Criteria criteria);
	/**
	 * 量能预警
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockWarnStatistics(Criteria criteria);
	/**
	 * 量能预警统计数量
	 * @param criteria
	 * @return
	 */
	int countStockWarnStatistics(Criteria criteria);
	/**
	 * 量能预警统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockWarnWindowStatistics(Criteria criteria);
	/**
	 * 量能预警统计统计数量
	 * @param criteria
	 * @return
	 */
	int countStockWarnWindowStatistics(Criteria criteria);
	/**
	 * 轨道预警
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockTrackStatistics(Criteria criteria);
	/**
	 * 轨道预警统计数量
	 * @param criteria
	 * @return
	 */
	int countStockTrackStatistics(Criteria criteria);
	/**
	 * 轨道趋势
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockTrackcycleStatistics(Criteria criteria);
	/**
	 * 轨道趋势统计数量
	 * @param criteria
	 * @return
	 */
	int countStockTrackcycleStatistics(Criteria criteria);
	/**
	 * 涨跌幅天数统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockUpdownStatistics(Criteria criteria);
	/**
	 * 涨跌幅天数统计数量
	 * @param criteria
	 * @return
	 */
	int countStockUpdownStatistics(Criteria criteria);
	/**
	 * 财政营业部统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockCZBStatistics(Criteria criteria);
	/**
	 * 财政营业部统计数量
	 * @param criteria
	 * @return
	 */
	int countStockCZBStatistics(Criteria criteria);
	/**
	 * 查询十大流通股东类型
	 * @return
	 */
	List<Map<String,Object>> getHoldertype();
	/**
	 * 十大流通股东统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockHolderStatistics(Criteria criteria);
	/**
	 * 十大流通股东统计数量
	 * @param criteria
	 * @return
	 */
	int countStockHolderStatistics(Criteria criteria);
	/**
	 * 个股收益统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockIncomingStatistics(Criteria criteria);
	/**
	 * 个股收益统计数量
	 * @param criteria
	 * @return
	 */
	int countStockIncomingStatistics(Criteria criteria);
	
	/**
	 * 个股限售类型
	 * @return
	 */
	List<Map<String,Object>> getRestrictedtype();
	/**
	 * 限售股解禁统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockRestrictedStatistics(Criteria criteria);
	/**
	 * 限售股解禁统计数量
	 * @param criteria
	 * @return
	 */
	int countStockRestrictedStatistics(Criteria criteria);
	/**
	 * 股名历史统计
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockDetailStatistics(Criteria criteria);
	/**
	 * 股名历史统计数量
	 * @param criteria
	 * @return
	 */
	int countDetailOldnameStatistics(Criteria criteria);
	/**
	 * 返回核心题材树
	 * @param criteria
	 * @return
	 */
	Tree selectStockCoretreeStatistics(Criteria criteria);
	/**
	 * 删除轨道趋势周期信息
	 * @param criteria
	 *  @return 00：失败，01：成功 ,其他情况
	 */
	String deleteTrackcycleByCode(Criteria criteria);
	/**
	 * 保存轨道趋势周期
	 * @param trackcycle
	 * @return
	 */
	String saveTrackcycle(Trackcycle trackcycle);
	/**
	 * 查询所有个股编号
	 * @return
	 */
	List<String> selectStocks();
	List<Map<String,Object>> selectStocksMsg();
	/**
	 * 查询个股轨道信息
	 * @return
	 */
	Map<String, Map<String,Object>> getTrackMap();
	/**
	 * 查询个股涨跌天数
	 * @return
	 */
	Map<String, Map<String,Object>> getDaysMap();
	/**
	 * 更新个股的类型
	 * @param statestr
	 * @param code
	 * @return
	 */
	String saveTrackstate(String statestr,String code);
	
	/**
	 * 根据导出的股票除权文件，分析后保存到对应的统计表中
	 * @param path
	 * @param codeList
	 * @param capital
	 */
	List<String> readStockExrightHis(String path,String code, double capital);
	
	/**
	 * 查询个股流通股本
	 * @return
	 */
	Map<String, Map<String,Object>> getStocksCapital();
	/**
	 * 查询某个用户的网页
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectStockUrls(Criteria criteria);
	/**
	 * 根据编号获取历史数据
	 * @param code
	 * @return
	 */
	public List<StockPriceModel> getStockDayData(String code,String path);
}
