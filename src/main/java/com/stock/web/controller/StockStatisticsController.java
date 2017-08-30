package com.stock.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.service.StockCodeStatisticsService;
import com.stock.service.StockStatisticsService;

@Controller
@RequestMapping("/stockstatistics")
public class StockStatisticsController {
	@Autowired
	private StockCodeStatisticsService stockCodeStatisticsService;
	@Autowired
	private StockStatisticsService stockStatisticsService;
	
	
	/**
	 * index
	 */
	@RequestMapping(value = "code", method = RequestMethod.GET)
	public String year() {
		return "stock/stockstatistics";
	}

	/**
	 * 编号统计
	 */
	@RequestMapping("/code/{code}")
	@ResponseBody
	public Object stockMsgStatistics(@PathVariable String code){
		return stockCodeStatisticsService.getStatistics(code);
	}
	/**
	 * 年报统计
	 */
	@RequestMapping("/year/{code}")
	@ResponseBody
	public Object stockYearStatistics(@PathVariable String code){
		List<Map<String,Object>> list = stockCodeStatisticsService.getYearStatistics(code);
		for(Map<String,Object> map : list){
			map.put("year",map.get("year")+"-01-01 00:00:00");
		}
		return list;
	}
	/**
	 * 月报统计
	 */
	@RequestMapping("/month/{code}")
	@ResponseBody
	public Object stockMonthStatistics(@PathVariable String code){
		List<Map<String,Object>> list = stockCodeStatisticsService.getMonthStatistics(code);
		String month;
		for(Map<String,Object> map : list){
			month = map.get("month")+"";
			month = month.length()==1?"0"+month:month;
			map.put("year",map.get("year")+"-"+month+"-01 00:00:00");
		}
		return list;
	}
	/**
	 * 个股趋势统计
	 */
	@RequestMapping("/close/{code}")
	@ResponseBody
	public Object stockDayStatistics(@PathVariable String code,HttpSession session){
		ServletContext context = session.getServletContext();
		String savePath = context.getRealPath("/upload");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("data",stockStatisticsService.getStockDayData(code,savePath));
		map.put("msg",stockCodeStatisticsService.getStockSelfMsg(code));
		return map;
	}
	
}
