package com.stock.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockCodeStatisticsMapper;
import com.stock.service.StockCodeStatisticsService;

@Service(value = "stockCodeStatisticsService")
public class StockCodeStatisticsServiceImpl implements StockCodeStatisticsService{
//	private Logger log = LoggerFactory.getLogger(StockCodeStatisticsServiceImpl.class);
	@Autowired
	StockCodeStatisticsMapper stockCodeStatisticsMapper;
	
	@Override
	public Map<String, Object> getStatistics(String code) {
		return stockCodeStatisticsMapper.getStockContent(code).get(code);
	}

	@Override
	public List<Map<String, Object>> getYearStatistics(String code) {
		return stockCodeStatisticsMapper.getYearStatistics(code);
	}

	@Override
	public List<Map<String, Object>> getMonthStatistics(String code) {
		return stockCodeStatisticsMapper.getMonthStatistics(code);
	}

	@Override
	public Map<String, Object> getStockSelfMsg(String code) {
		return stockCodeStatisticsMapper.getStockSelfMsg(code).get(code);
	}

}
