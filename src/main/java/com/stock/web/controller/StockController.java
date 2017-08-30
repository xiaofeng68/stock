package com.stock.web.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.jackjson.JackJson;
import com.authority.common.utils.DateUtil;
import com.authority.common.utils.RegexUtils;
import com.authority.common.utils.StringUtil;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExceptionReturn;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.authority.pojo.ExtReturn;
import com.authority.pojo.Tree;
import com.stock.pojo.Trackcycle;
import com.stock.runnable.UpdateDataRunnable;
import com.stock.service.StockStatisticsService;

/**
 * 个股统计
 * 
 * @author yu_qhai
 * 
 */
@Controller
@RequestMapping("/stock")
public class StockController {
	@Autowired
	private StockStatisticsService stockStatisticsService;
	private Map<String,Object> cacheMap;
	/**
	 * index
	 */
	@RequestMapping(value = "year", method = RequestMethod.GET)
	public String year() {
		return "stock/year";
	}

	/**
	 * 年报
	 */
	@RequestMapping(value = "/year", method = RequestMethod.POST)
	@ResponseBody
	public Object stockYearStatistics(ExtPager pager, HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}
		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockStatistics(criteria);
		int total = this.stockStatisticsService.countStockStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "month", method = RequestMethod.GET)
	public String month() {// 返回响应的页面
		return "stock/month";
	}

	/**
	 * 月报
	 */
	@RequestMapping(value = "/month", method = RequestMethod.POST)
	@ResponseBody
	public Object stockMonthStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockMonthStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockMonthStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "warn", method = RequestMethod.GET)
	public String warn() {// 返回响应的页面
		return "stock/warn";
	}

	/**
	 * 量能预警
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/warn", method = RequestMethod.POST)
	@ResponseBody
	public Object stockWarnStatistics(ExtPager pager, HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" day_d  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockWarnStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockWarnStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	/**
	 * 量能预警统计
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/warnwindow", method = RequestMethod.POST)
	@ResponseBody
	public Object stockWarnWindowStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" bus,num  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockWarnWindowStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockWarnWindowStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "track", method = RequestMethod.GET)
	public String track() {// 返回响应的页面
		return "stock/track";
	}

	/**
	 * 轨道预警
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/track", method = RequestMethod.POST)
	@ResponseBody
	public Object stockTrackStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" days  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockTrackStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockTrackStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "trackcycle", method = RequestMethod.GET)
	public String trackcycle() {// 返回响应的页面
		return "stock/trackcycle";
	}

	/**
	 * 轨道趋势
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/trackcycle", method = RequestMethod.POST)
	@ResponseBody
	public Object stockTrackcycleStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockTrackcycleStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockTrackcycleStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "updown", method = RequestMethod.GET)
	public String updown() {// 返回响应的页面
		return "stock/updown";
	}

	/**
	 * 涨跌幅
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/updown", method = RequestMethod.POST)
	@ResponseBody
	public Object stockUpdownStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" day_d  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockUpdownStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockUpdownStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "czb", method = RequestMethod.GET)
	public String czb() {// 返回响应的页面
		return "stock/czb";
	}

	/**
	 * 财政营业部
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/czb", method = RequestMethod.POST)
	@ResponseBody
	public Object stockCZBStatistics(ExtPager pager, HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" day_d  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockCZBStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockCZBStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	/**
	 * 十大流通股东类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "/holdertype", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getHoldertype() {
		List<Map<String, Object>> list = this.stockStatisticsService
				.getHoldertype();
		return list;
	}

	@RequestMapping(value = "holder", method = RequestMethod.GET)
	public String holder() {// 返回响应的页面
		return "stock/holder";
	}

	/**
	 * 十大流通股东
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/holder", method = RequestMethod.POST)
	@ResponseBody
	public Object stockHolderStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" ss.stockcount  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockHolderStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockHolderStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "incoming", method = RequestMethod.GET)
	public String incoming() {// 返回响应的页面
		return "stock/incoming";
	}

	/**
	 * 十大流通股东
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/incoming", method = RequestMethod.POST)
	@ResponseBody
	public Object stockIncomintStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		} else {
			criteria.setOrderByClause(" si.year  desc ");
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockIncomingStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockIncomingStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	/**
	 * 限售股解禁
	 * 
	 * @return
	 */
	@RequestMapping(value = "/restrictedtype", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> getRestrictedtype() {
		List<Map<String, Object>> list = this.stockStatisticsService
				.getRestrictedtype();
		return list;
	}

	@RequestMapping(value = "restricted", method = RequestMethod.GET)
	public String restricted() {// 返回响应的页面
		return "stock/restricted";
	}

	/**
	 * 限售股解禁
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/restricted", method = RequestMethod.POST)
	@ResponseBody
	public Object stockRestrictedStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockRestrictedStatistics(criteria);
		int total = this.stockStatisticsService
				.countStockRestrictedStatistics(criteria);
		return new ExtGridReturn(total, list);
	}

	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public String oldname() {// 返回响应的页面
		return "stock/detail";
	}

	/**
	 * 股名历史
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	@ResponseBody
	public Object stockDetailStatistics(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockDetailStatistics(criteria);
		int total = this.stockStatisticsService
				.countDetailOldnameStatistics(criteria);
		return new ExtGridReturn(total, list);
	}
	/**
	 * 个股信息
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/msgs", method = RequestMethod.POST)
	@ResponseBody
	public Object stocksMsgs(ExtPager pager,
			HttpServletRequest request) {

		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStocksMsg();
		return list;
	}

	@RequestMapping(value = "coretree", method = RequestMethod.GET)
	public String coretree() {// 返回响应的页面
		return "stock/coretree";
	}

	/**
	 * 核心题材
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/coretree", method = RequestMethod.POST)
	@ResponseBody
	public Object stockCoretreeStatistics(ExtPager pager,
			HttpServletRequest request) {
		try {
			Criteria criteria = getQueryCondition(request);
			Tree tree = this.stockStatisticsService
					.selectStockCoretreeStatistics(criteria);
			return tree.getChildren();
		} catch (Exception e) {
			return new ExceptionReturn(e);
		}
	}
	
	@RequestMapping(value = "urls", method = RequestMethod.GET)
	public String stockUrls() {// 返回响应的页面
		return "stock/urls";
	}

	/**
	 * 财经网站
	 * 
	 * @param pager
	 * @return
	 */
	@RequestMapping(value = "/urls", method = RequestMethod.POST)
	@ResponseBody
	public Object stockUrls(ExtPager pager,
			HttpServletRequest request) {
		Criteria criteria = new Criteria();
		criteria.setOrderByClause(" times  desc ");
		List<Map<String, Object>> list = this.stockStatisticsService
				.selectStockUrls(criteria);
		return new ExtGridReturn(list.size(), list);
	}
	@RequestMapping(value = "market", method = RequestMethod.GET)
	public String stockMarkets() {// 返回响应的页面
		return "stock/market";
	}
	/**
	 * 删除该个股对应的轨道趋势信息
	 */
	@RequestMapping("/deltrack/{code}")
	@ResponseBody
	public Object delete(@PathVariable String code) {
		try {
			if (StringUtils.isBlank(code)) {
				return new ExtReturn(false, "个股编号不能为空！");
			}
			Criteria criteria = new Criteria();
			criteria.put("code", code);
			String result = this.stockStatisticsService
					.deleteTrackcycleByCode(criteria);
			if ("01".equals(result)) {
				return new ExtReturn(true, "删除成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "删除失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 保存轨道趋势周期
	 */
	@RequestMapping("/savetrack")
	@ResponseBody
	public Object saveTrackcycle(Trackcycle trackcycle) {
		try {
			String result = this.stockStatisticsService
					.saveTrackcycle(trackcycle);
			if ("01".equals(result)) {
				return new ExtReturn(true, "保存成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "保存失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			return new ExceptionReturn(e);
		}
	}

	/**
	 * 保存个股类型
	 */
	@RequestMapping("/savestate")
	@ResponseBody
	public Object saveStockState(String statestr, String code) {
		try {
			String result = this.stockStatisticsService.saveTrackstate(
					statestr, code);
			if ("01".equals(result)) {
				return new ExtReturn(true, "保存成功！");
			} else if ("00".equals(result)) {
				return new ExtReturn(false, "保存失败！");
			} else {
				return new ExtReturn(false, result);
			}
		} catch (Exception e) {
			return new ExceptionReturn(e);
		}
	}

	@RequestMapping("/updatedata")
	@ResponseBody
	public Object updateStockdata(HttpServletRequest request,HttpSession session) {
		String isClear = request.getParameter("isclear");
		// 如果应用级别缓存中是否允许更新？开启线程对数据进行解压并对数据文件统计：出现等待状态
		if (cacheMap != null && "false".equals(isClear)) {// 如果更新过中,直接返回,并切不需要重新更新
			return cacheMap;
		} else {// 如果是第一次请求,或再次点击查询时
			cacheMap = new HashMap<String, Object>();
			ServletContext context = session.getServletContext();
			String savePath = context.getRealPath("/upload");
			cacheMap.put("path", savePath);
			cacheMap.put("updatedate",DateUtil.dateToStr(new Date()));
		}
		new Thread(new UpdateDataRunnable(stockStatisticsService, cacheMap)).start();
		return cacheMap;
	}
	/**
	 * 清空更新状态缓存
	 * @param session
	 * @return
	 */
	@RequestMapping("/clearDataCache")
	@ResponseBody
	public Object clearDataCache(){
		cacheMap.clear();
		cacheMap = null;
		return "true";
	}
	/**
	 * 根据请求组装查询条件
	 * 
	 * @param request
	 */
	private Criteria getQueryCondition(HttpServletRequest request) {
		Criteria criteria = new Criteria();
		String state = request.getParameter("state");
		if (StringUtil.isNotNull(state))
			criteria.put("state", 1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date[] dates = null;
		String type = request.getParameter("type");

		if ("year".equals(type)) {// 年报
			String year = request.getParameter("year");
			if (StringUtil.isNotNull(year)) {
				cal.set(Calendar.YEAR, Integer.parseInt(year));
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "year");
				criteria.put("yeard", year);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "year");
				criteria.put("yeard", cal.get(Calendar.YEAR));
			}
		} else if ("month".equals(type)) {// 月报
			String month = request.getParameter("month");
			if (StringUtil.isNotNull(month)) {
				cal.set(Calendar.MONTH, Integer.parseInt(month) - 1);
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "month");
				criteria.put("month", month);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "month");
				criteria.put("month", cal.get(Calendar.MONTH) + 1);
			}
			criteria.put("year", cal.get(Calendar.YEAR));
		} else if ("warn".equals(type)) {// 量能预警
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "day");
			}
			criteria.put("warn", "warn");
			criteria.put("yeard", cal.get(Calendar.YEAR));
		} else if ("warnwindow".equals(type)) {// 量能预警
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "week");
			}
			criteria.put("warnwindow", "warnwindow");
			criteria.put("warndays", request.getParameter("warndays"));
		} else if ("track".equals(type)) {// 轨道
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "day");
			}
			String stocktrack = request.getParameter("stocktrack");
			if (StringUtil.isNotNull(stocktrack)) {
				criteria.put("stocktrack", stocktrack);
			}
			String stocktrackdes = request.getParameter("stocktrackdes");
			if (StringUtil.isNotNull(stocktrackdes)) {
				criteria.put("stocktrackdes", stocktrackdes);
			}
			criteria.put("track", "track");
		} else if ("trackcycle".equals(type)) {// 轨道趋势
			String stocktrack = request.getParameter("stocktrack");
			if (StringUtil.isNotNull(stocktrack)) {
				criteria.put("stocktrack", stocktrack);
			}
		} else if ("updown".equals(type)) {// 个股涨跌幅
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "day");
			}
			criteria.put("updown", type);
			String upsStr = request.getParameter("ups");
			if (StringUtil.isNotNull(upsStr)) {
				criteria.put(upsStr, upsStr);
			}
		} else if ("czb".equals(type)) {// 财政营业部
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = DateUtil.getBegainAndEndDate(cal.getTime(), "day");
			}
			criteria.put("czb", type);
			String typeCode = request.getParameter("stockczb");
			if (StringUtil.isNotNull(typeCode)) {
				criteria.put("stockczb", typeCode);
			}
		} else if ("holder".equals(type)) {// 十大流通股东
			String ios = request.getParameter("ios");
			if (StringUtil.isNotNull(ios)) {
				criteria.put("ios", ios);
			}
			String holdertype = request.getParameter("holdertype");
			if (StringUtil.isNotNull(holdertype)) {
				criteria.put("holdertype", holdertype);
			}
			String stockholder = request.getParameter("stockholder");
			if (StringUtil.isNotNull(stockholder)) {
				criteria.put("stockholder", stockholder);
			}
		} else if ("restricted".equals(type)) {// 限售股解禁
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			if (StringUtil.isNotNull(startDate)
					&& StringUtil.isNotNull(endDate)) {
				dates = new Date[2];
				dates[0] = DateUtil.strToDate(startDate);
				dates[1] = DateUtil.strToDate(endDate);
			} else {
				dates = new Date[2];
				dates[0] = new Date();
				dates[1] = DateUtil.getNextMonth(dates[0], 3);
			}
			criteria.put("restricted", type);
			String stockrestrictedtype = request
					.getParameter("stockrestrictedtype");
			if (StringUtil.isNotNull(stockrestrictedtype)) {
				criteria.put("stockrestrictedtype", stockrestrictedtype);
			}
		}
		if (dates != null && dates.length == 2) {// 起止时间
			criteria.put("startDate", DateUtil.dateToStr(dates[0]));
			criteria.put("endDate", DateUtil.dateToStr(dates[1]));
		}
		String code = request.getParameter("code");
		if (StringUtil.isNotNull(code)) {// 根据编号查询
			if (RegexUtils.checkStockCode(code)) {
				criteria.put("code", code);
			} else {
				criteria.put("name", code);
			}
		}
		String marketDate = request.getParameter("market_date");
		if (StringUtil.isNotNull(marketDate)) {// 上市年份
			criteria.put("market_date", marketDate);
		}
		String keys = request.getParameter("keys");
		if (StringUtil.isNotNull(keys)) {// 核心题材关键字
			List<String> list = JackJson.fromJsonToObject(keys,
					new TypeReference<List<String>>() {
					});
			criteria.put("corekeys", list);
		}
		String typeIds = request.getParameter("typeIds");
		if (StringUtil.isNotNull(typeIds)) {// 自定义类型
			List<String> list = JackJson.fromJsonToObject(typeIds,
					new TypeReference<List<String>>() {
					});
			criteria.put("types", list);
		}
		return criteria;
	}
}
