package com.stock.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authority.common.utils.DateUtil;
import com.authority.common.utils.StringUtil;
import com.authority.pojo.Criteria;
import com.authority.pojo.Tree;
import com.stock.dao.StockStatisticsMapper;
import com.stock.pojo.StockPriceModel;
import com.stock.pojo.StockStatisticsModel;
import com.stock.pojo.StockStatisticsMonthModel;
import com.stock.pojo.StockVolModel;
import com.stock.pojo.Trackcycle;
import com.stock.pojo.TreeCoreKeys;
import com.stock.runnable.StockUtils;
import com.stock.service.StockStatisticsService;

@Service(value = "stockStatisticsService")
public class StockStatisticsServiceImpl implements StockStatisticsService {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	StockStatisticsMapper stockStatisticsMapper;
	@Resource
	private Cache ehCache;
	private Date beginDate;

	@Override
	public List<Map<String, Object>> selectStockStatistics(Criteria criteria) {
		return stockStatisticsMapper.selectStockStatistics(criteria);
	}

	@Override
	public int countStockStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockMonthStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockMonthStatistics(criteria);
	}

	@Override
	public int countStockMonthStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockMonthStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockWarnStatistics(Criteria criteria) {
		return stockStatisticsMapper.selectStockWarnStatistics(criteria);
	}

	@Override
	public int countStockWarnStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockWarnStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockWarnWindowStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockWarnWindowStatistics(criteria);
	}

	@Override
	public int countStockWarnWindowStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockWarnWindowStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockTrackStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockTrackStatistics(criteria);
	}

	@Override
	public int countStockTrackStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockTrackStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockUpdownStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockUpdownStatistics(criteria);
	}

	@Override
	public int countStockUpdownStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockUpdownStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockCZBStatistics(Criteria criteria) {
		return stockStatisticsMapper.selectStockCZBStatistics(criteria);
	}

	@Override
	public int countStockCZBStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockCZBStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> getHoldertype() {
		return stockStatisticsMapper.selectHoldertype();
	}

	@Override
	public List<Map<String, Object>> selectStockHolderStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockHolderStatistics(criteria);
	}

	@Override
	public int countStockHolderStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockHolderStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockIncomingStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockIncomingStatistics(criteria);
	}

	@Override
	public int countStockIncomingStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockIncomingStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> getRestrictedtype() {
		return stockStatisticsMapper.selectRestrictedtype();
	}

	@Override
	public List<Map<String, Object>> selectStockRestrictedStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockRestrictedStatistics(criteria);
	}

	@Override
	public int countStockRestrictedStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockRestrictedStatistics(criteria);
	}

	@Override
	public List<Map<String, Object>> selectStockDetailStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockDetailStatistics(criteria);
	}

	@Override
	public int countDetailOldnameStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockDetailStatistics(criteria);
	}

	@Override
	public Tree selectStockCoretreeStatistics(Criteria criteria) {
		List<Map<String, Object>> list = stockStatisticsMapper
				.selectStockCorecontentStatistics(criteria);
		TreeCoreKeys menu = new TreeCoreKeys(list);
		return menu.getCoreKeysNode();
	}

	@Override
	public List<Map<String, Object>> selectStockTrackcycleStatistics(
			Criteria criteria) {
		return stockStatisticsMapper.selectStockTrackcycleStatistics(criteria);
	}

	@Override
	public int countStockTrackcycleStatistics(Criteria criteria) {
		return stockStatisticsMapper.countStockTrackcycleStatistics(criteria);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String deleteTrackcycleByCode(Criteria criteria) {
		int result = 0;
		String code = criteria.getAsString("code");
		result = this.stockStatisticsMapper.deleteTrackcycleByCode(code);
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveTrackcycle(Trackcycle trackcycle) {
		int count = this.stockStatisticsMapper.countTrackcycle(trackcycle
				.getCode());
		int result = 0;
		if (count == 0) {
			result = this.stockStatisticsMapper.insertTrackcycle(trackcycle);
		} else {
			result = this.stockStatisticsMapper.updateTrackcycle(trackcycle);
		}
		return result > 0 ? "01" : "00";
	}

	@Override
	public List<String> selectStocks() {
		return this.stockStatisticsMapper.selectStocks();
	}

	@Override
	public List<Map<String, Object>> selectStocksMsg() {
		return this.stockStatisticsMapper.selectStocksMsg();
	}

	@Override
	public Map<String, Map<String, Object>> getTrackMap() {
		return this.stockStatisticsMapper.getTrackMap();
	}

	@Override
	public Map<String, Map<String, Object>> getDaysMap() {
		return this.stockStatisticsMapper.getDaysMap();
	}

	@Override
	public String saveTrackstate(String statestr, String code) {
		int result = 0;
		Criteria criteria = new Criteria();
		criteria.put("code", code);
		criteria.put("statestr", statestr);
		result = this.stockStatisticsMapper.saveTrackstate(criteria);
		return result > 0 ? "01" : "00";
	}

	@Override
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<StockPriceModel> getStockDayData(String code, String filePath) {
		Element element = ehCache.get(code);
		if (element != null)
			return (List<StockPriceModel>) element.getValue();
		else {// 如果没有去找对应的文件分析完毕放入缓存
			File file = new File(filePath + File.separator + "stock");
			if (!file.exists())
				return new ArrayList<StockPriceModel>();
			String[] arr = getPathByCode(code);
			List<StockPriceModel> list;
			try {
				String path = file.getPath() + "/" + arr[1];
				if ("true".equals(arr[0])) {
					list = getMarketData(code, path);
				} else {
					list = getStockData(code, path);
				}
				// 把数据放入缓存，方便数据展示
				element = new Element(code, list);
				ehCache.put(element);
				/* 强行输出内存数据到硬盘 */
				ehCache.flush();
			} catch (Exception e) {
				log.error("解析{}时出现异常:{}", code, e);
				return new ArrayList<StockPriceModel>();
			}
			return list;
		}
	}

	@Override
	public List<String> readStockExrightHis(String filePath, String code,
			double capital) {
		synchronized (ehCache) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.set(Calendar.YEAR, 2009);
			cal.set(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_YEAR, 1);
			beginDate = cal.getTime();
			String[] arr = getPathByCode(code);
			List<StockPriceModel> list;
			try {
				String path = filePath + "/" + arr[1];
				if ("true".equals(arr[0])) {
					list = getMarketData(code, path);
				} else {
					list = getStockData(code, path);
				}
				// 把数据放入缓存，方便数据展示
				Element element = new Element(code, list);
				ehCache.put(element);
				/* 强行输出内存数据到硬盘 */
				ehCache.flush();
				List<String> sql = new ArrayList<String>();
				List<String> sqls;
				if (StockUtils.isStock(code)) {
					sqls = saveStockWarnHelp(list, code, capital);
					if (sqls != null && sqls.size() > 0) {
						sql.addAll(sqls);
					}
				}
				// 分析个股年报
				List<StockStatisticsModel> smList = analyzeStock(list);
				// 对该股票进行的统计保存
				sqls = saveStockStatistics(smList);
				if (sqls != null && sqls.size() > 0) {
					sql.addAll(sqls);
				}
				// 分析个股月报
				List<StockStatisticsMonthModel> smmList = analyzeStockMonth(list);
				// 对该股票进行的统计保存
				sqls = saveStockStatisticsMonth(smmList);
				if (sqls != null && sqls.size() > 0) {
					sql.addAll(sqls);
				}
				// 统计个股的历史量
				List<StockVolModel> svmList = analyzeStockVol(list);
				// 对该股票进行的量能统计保存
				sqls = saveStockVol(svmList);
				if (sqls != null && sqls.size() > 0) {
					sql.addAll(sqls);
				}
				return sql;
			} catch (Exception e) {
				log.error("解析{}时出现异常:{}", code, e);
			}
		}
		return null;
	}

	/**
	 * 统计出最大值于最小值等信息
	 * 
	 * @param conn
	 * @param list
	 * @return
	 */
	private List<StockStatisticsMonthModel> analyzeStockMonth(
			List<StockPriceModel> list) {
		if (list.size() == 0)
			return null;
		List<StockStatisticsMonthModel> smList = new ArrayList<StockStatisticsMonthModel>();
		StockStatisticsMonthModel model = null;
		StockPriceModel maxY = null, minY = null;
		int maxIndex = 0, minIndex = 0;
		StockPriceModel sm;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(cal.get(Calendar.YEAR) - 1, 0, 1);
		Date startDate = cal.getTime();
		int currentM = 0;
		for (int i = 0, j = list.size(); i < j; i++) {
			sm = list.get(i);
			if (startDate.before(sm.getDate())) {// 开始统计某当前的最高价于最低价
				cal.setTime(sm.getDate());
				if (model != null) {
					if (currentM != cal.get(Calendar.MONTH)) {// 修改了年份
						model.setDays(maxIndex - minIndex);
						model.setHhv_d(maxY.getDate());
						model.setHhv_p(maxY.getHhv());
						model.setLlv_d(minY.getDate());
						model.setLlv_p(minY.getLlv());
						// 在创建一个model
						currentM = cal.get(Calendar.MONTH);
						model = new StockStatisticsMonthModel();
						model.setYear(cal.get(Calendar.YEAR));
						model.setMonth(currentM + 1);
						model.setCode(sm.getCode());
						maxY = sm;
						minY = sm;
						maxIndex = i;
						minIndex = i;
						smList.add(model);
					} else {// 在同一年内，只找出最大值与最小值
						if (sm.getHhv() > maxY.getHhv()) {
							maxY = sm;
							maxIndex = i;
						}
						if (sm.getLlv() < minY.getLlv()) {
							minY = sm;
							minIndex = i;
						}
					}
				} else {// 从09年开始时
					currentM = cal.get(Calendar.MONTH);
					model = new StockStatisticsMonthModel();
					model.setYear(cal.get(Calendar.YEAR));
					model.setMonth(currentM + 1);
					model.setCode(sm.getCode());
					maxY = sm;
					minY = sm;
					maxIndex = i;
					minIndex = i;
					smList.add(model);
				}
			}
			if (i == j - 1 && model != null) {// 最后一条记录执行完后，修改最后一年的统计信息
				model.setDays(maxIndex - minIndex);
				model.setHhv_d(maxY.getDate());
				model.setHhv_p(maxY.getHhv());
				model.setLlv_d(minY.getDate());
				model.setLlv_p(minY.getLlv());
			}
		}
		return smList;
	}

	/**
	 * 获取盈亏比
	 * 
	 * @param model
	 * @return
	 */
	private String getProfitLoss(double hhv, double llv, int days) {
		Double gain = (hhv - llv) / llv * 100;
		DecimalFormat df = new DecimalFormat("######0.00");
		String profitLoss = df.format(gain);
		profitLoss = days >= 0 ? profitLoss : "-" + profitLoss;
		return profitLoss;
	}

	/**
	 * 统计出最大值于最小值等信息
	 * 
	 * @param conn
	 * @param list
	 * @return
	 */
	private List<StockVolModel> analyzeStockVol(List<StockPriceModel> list) {
		if (list.size() < 20)
			return null;
		List<StockVolModel> smList = new ArrayList<StockVolModel>();
		StockVolModel model = null;
		StockPriceModel sm;
		double max = Double.MIN_VALUE, min = Double.MAX_VALUE;
		Queue<StockVolModel> maxQueue = new LinkedList<StockVolModel>();
		Queue<StockVolModel> minQueue = new LinkedList<StockVolModel>();
		for (int i = 0, j = list.size(); i < j; i++) {
			sm = list.get(i);
			model = new StockVolModel();
			model.setCode(sm.getCode());
			model.setVol_date(sm.getDate());
			model.setVol(sm.getVol());
			if (model.getVol() >= max) {
				maxQueue.add(model);
			}
			if (model.getVol() <= min) {
				minQueue.add(model);
			}
			if (maxQueue.size() > 10) {
				maxQueue.poll();
			}
			if (minQueue.size() > 10) {
				minQueue.poll();
			}
		}
		smList.addAll(maxQueue);
		smList.addAll(minQueue);
		return smList;
	}

	/**
	 * 生成月报sql
	 * 
	 * @param smList
	 * @return
	 */
	public List<String> saveStockStatisticsMonth(
			List<StockStatisticsMonthModel> smList) {
		List<String> sqls = new ArrayList<String>();
		if (smList != null && smList.size() > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock_statistics_month");
			sql.append(" (code,year,month,hhv_d,hhv_p,llv_d,llv_p,days,profit_loss)");
			sql.append("values");
			StockStatisticsMonthModel model = null;
			for (int i = 0, j = smList.size(); i < j; i++) {
				model = smList.get(i);
				sql.append("(");
				String code = model.getCode();
				sql.append("'" + code + "',");
				int year = model.getYear();
				sql.append(year + ",");
				int month = model.getMonth();
				sql.append(month + ",");
				String hhv_d = DateUtil.dateToStr(model.getHhv_d());
				sql.append("'" + hhv_d + "',");
				double hhv_p = model.getHhv_p();
				sql.append(hhv_p + ",");
				String llv_d = DateUtil.dateToStr(model.getLlv_d());
				sql.append("'" + llv_d + "',");
				double llv_p = model.getLlv_p();
				sql.append(llv_p + ",");
				int days = model.getDays();
				sql.append(days);
				String profitLoss = getProfitLoss(model.getHhv_p(),
						model.getLlv_p(), model.getDays());
				sql.append(","+profitLoss);
				sql.append("),");
			}
			sqls.add(sql.substring(0, sql.length() - 1));
		}
		return sqls;
	}

	/**
	 * 生成量能统计
	 * 
	 * @param smList
	 * @return
	 */
	public List<String> saveStockVol(List<StockVolModel> svmList) {
		List<String> sqls = new ArrayList<String>();
		if (svmList != null && svmList.size() > 0) {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock_vol");
			sql.append(" (code,vol_date,vol)");
			sql.append("values");
			StockVolModel model = null;
			for (int i = 0, j = svmList.size(); i < j; i++) {
				model = svmList.get(i);
				sql.append("(");
				String code = model.getCode();
				sql.append("'" + code + "',");
				String vol_date = DateUtil.dateToStr(model.getVol_date());
				sql.append("'" + vol_date + "',");
				double hhv_p = model.getVol();
				sql.append(hhv_p);
				sql.append("),");
			}
			sqls.add(sql.substring(0, sql.length() - 1));
		}
		return sqls;
	}

	/**
	 * @param smList
	 * @return
	 * @throws SQLException
	 */
	private List<String> saveStockStatistics(List<StockStatisticsModel> smList) {
		List<String> sqls = new ArrayList<String>();
		if (smList != null && smList.size() > 0) {
			StockStatisticsModel model = null;
			for (int i = 0, j = smList.size(); i < j; i++) {
				model = smList.get(i);
				StringBuffer sql = new StringBuffer();
				sql.append("INSERT INTO stock_statistics");
				sql.append(" (code,year_d,hhv_d,hhv_p,llv_d,llv_p,days,profit_loss)");
				sql.append("values(");
				String code = model.getCode();
				sql.append("'" + code + "',");
				int year = model.getYear();
				sql.append(year + ",");
				String hhv_d = DateUtil.dateToStr(model.getHhv_d());
				sql.append("'" + hhv_d + "',");
				double hhv_p = model.getHhv_p();
				sql.append(hhv_p + ",");
				String llv_d = DateUtil.dateToStr(model.getLlv_d());
				sql.append("'" + llv_d + "',");
				double llv_p = model.getLlv_p();
				sql.append(llv_p + ",");
				int days = model.getDays();
				sql.append(days+",");
				String profitLoss = getProfitLoss(model.getHhv_p(),
						model.getLlv_p(), model.getDays());
				sql.append(profitLoss);
				sql.append(") ON DUPLICATE KEY UPDATE code='" + code + "',");
				sql.append("year_d=" + year + ",");
				sql.append("hhv_d='" + hhv_d + "',");
				sql.append("hhv_p=" + hhv_p + ",");
				sql.append("llv_d='" + llv_d + "',");
				sql.append("llv_p=" + llv_p + ",");
				sql.append("days=" + days + ",");
				sql.append("profit_loss=" + profitLoss);
				sqls.add(sql.toString());
			}
			String date = DateUtil.dateToStr(model.getMarker_date());
			String llvDate = DateUtil.dateToStr(model.getLlv_date());
			String hhvDate = DateUtil.dateToStr(model.getHhv_date());
			String profitLoss = getProfitLoss(model.getHhv_p(),
					model.getLlv_p(), model.getDays());
			sqls.add("update stock_content set hhv=" + model.getHhv() + ",llv="
					+ model.getLlv() + ",market_date='" + date + "',llv_date='"
					+ llvDate + "',hhv_date='" + hhvDate + "',v_days="
					+ model.getV_days() + ",profit_loss=" + profitLoss
					+ " where code='" + model.getCode() + "'");
		}
		return sqls;
	}

	/**
	 * 统计出最大值于最小值等信息
	 * 
	 * @param conn
	 * @param list
	 * @return
	 */
	private List<StockStatisticsModel> analyzeStock(List<StockPriceModel> list) {
		if (list.size() == 0)
			return null;
		List<StockStatisticsModel> smList = new ArrayList<StockStatisticsModel>();
		StockStatisticsModel model = null;
		StockPriceModel maxY = null, minY = null;
		StockPriceModel sm = list.get(0);
		int maxIndex = 0, minIndex = 0, maxI = 0, minI = 0;
		double maxP = sm.getHhv(), minP = sm.getLlv();
		Date marker_date = list.get(0).getDate(), maxDate = sm.getDate(), minDate = sm
				.getDate();

		Calendar cal = Calendar.getInstance();
		int currentY = 0;
		for (int i = 0, j = list.size(); i < j; i++) {
			sm = list.get(i);
			if (sm.getHhv() > maxP) {// 历史最高价
				maxP = sm.getHhv();
				maxI = i;
				maxDate = sm.getDate();
			}
			if (sm.getLlv() < minP) {// 历史最低价
				minP = sm.getLlv();
				minI = i;
				minDate = sm.getDate();
			}
			if (beginDate.before(sm.getDate())) {// 开始统计某当前的最高价于最低价
				cal.setTime(sm.getDate());
				if (model != null) {
					if (currentY != cal.get(Calendar.YEAR)) {// 修改了年份
						model.setDays(maxIndex - minIndex);
						model.setHhv_d(maxY.getDate());
						model.setHhv_p(maxY.getHhv());
						model.setLlv_d(minY.getDate());
						model.setLlv_p(minY.getLlv());
						model.setHhv(maxP);
						model.setLlv(minP);
						// 在创建一个model
						currentY = cal.get(Calendar.YEAR);
						model = new StockStatisticsModel();
						model.setYear(currentY);
						model.setCode(sm.getCode());
						maxY = sm;
						minY = sm;
						maxIndex = i;
						minIndex = i;
						smList.add(model);
					} else {// 在同一年内，只找出最大值与最小值
						if (sm.getHhv() > maxY.getHhv()) {
							maxY = sm;
							maxIndex = i;
						}
						if (sm.getLlv() < minY.getLlv()) {
							minY = sm;
							minIndex = i;
						}
					}
				} else {// 从09年开始时
					currentY = cal.get(Calendar.YEAR);
					model = new StockStatisticsModel();
					model.setYear(currentY);
					model.setCode(sm.getCode());
					maxY = sm;
					minY = sm;
					maxIndex = i;
					minIndex = i;
					smList.add(model);
				}
			}
			if (i == j - 1 && model != null) {// 最后一条记录执行完后，修改最后一年的统计信息
				model.setMarker_date(marker_date);
				model.setDays(maxIndex - minIndex);
				model.setHhv_d(maxY.getDate());
				model.setHhv_p(maxY.getHhv());
				model.setLlv_d(minY.getDate());
				model.setLlv_p(minY.getLlv());
				model.setHhv(maxP);
				model.setLlv(minP);
				model.setV_days(maxI - minI);// 历史最高价与最低价相差交易日
				model.setLlv_date(minDate);
				model.setHhv_date(maxDate);
			}
		}
		return smList;
	}

	/**
	 * 用于统计涨幅在5个点以上的股票历史数据
	 * 
	 * @param conn
	 * @param list
	 * @param code
	 */
	private List<String> saveStockWarnHelp(List<StockPriceModel> list,
			String code, double capital) {
		if (list == null || list.size() == 0)
			return null;
		List<String> sqls = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		DecimalFormat df = new DecimalFormat("#0.00");
		boolean hasSave = false;
		StringBuffer sql = new StringBuffer(
				"insert into stock_cache(code,day_d,open_p,close_p,udv,"
						+ "tov,market_p,buy,yclose_p) values");
		for (StockPriceModel pm : list) {
			if (pm.getDate().before(cal.getTime()) || pm.getState() == 0) {
				continue;
			}
			hasSave = true;
			sql.append("(");
			sql.append("'" + code + "'");
			sql.append(",'" + DateUtil.dateToStr(pm.getDate()) + "'");
			sql.append("," + pm.getOpen());
			sql.append("," + pm.getClose());
			double std = (pm.getClose() - pm.getYclose()) / pm.getYclose()
					* 100;// 单日涨幅
			sql.append("," + df.format(std));
			double markerPrice = pm.getYclose() * capital;
			double capitalPrice = markerPrice / 2 / 12 * 0.8;
			sql.append("," + capitalPrice);
			sql.append("," + markerPrice);
			sql.append("," + pm.getBuy());
			sql.append("," + pm.getYclose() + "),");
		}
		if (hasSave)
			sqls.add(sql.substring(0, sql.length() - 1));
		return sqls;
	}

	/**
	 * 导入除权的股票数据
	 * 
	 * @param code
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private List<StockPriceModel> getStockData(String code, String path)
			throws FileNotFoundException, IOException {
		List<StockPriceModel> list;
		list = new ArrayList<StockPriceModel>();
		StockPriceModel spm = null;
		File file = new File(path);
		if (!file.exists())
			return null;
		FileInputStream ins = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(ins));
		String noteStr;
		StockPriceModel lastModel = null;
		while ((noteStr = br.readLine()) != null) {
			try {
				String[] datas = noteStr.split(",");
				if (datas.length != 7)
					continue;
				spm = new StockPriceModel();
				spm.setCode(code);
				spm.setDate(DateUtil.strToDate(datas[0], "yyyy/MM/dd"));
				spm.setOpen(Double.parseDouble(datas[1]));
				spm.setHhv(Double.parseDouble(datas[2]));
				spm.setLlv(Double.parseDouble(datas[3]));
				spm.setClose(Double.parseDouble(datas[4]));
				spm.setVol(Double.parseDouble(datas[5]));
				spm.setBuy(Double.parseDouble(datas[6]));
				if (lastModel != null) {// 存在昨日收盘价（疑问某一年的第一个交易日未出现涨停，但是与去年的最后一个交易日计算出来的涨幅是涨停会不会存在）
					double std = (spm.getClose() - lastModel.getClose())
							/ lastModel.getClose() * 100;// 单日涨幅
					if (std > 9.8) {// 涨停
						spm.setState(1);
					} else if (std < -9.8) {// 跌停
						spm.setState(-1);
					} else if (std >= 5.0) {// 涨幅在5到9.8之间
						spm.setState(2);
					}
					spm.setColor(std >= 0 ? "#FF0000" : "#00FF00");
					spm.setYclose(lastModel.getClose());
				}
				list.add(spm);
				lastModel = spm;
			} catch (Exception e) {
			}
		}
		return list;
	}

	/**
	 * 导入扳指数据
	 * 
	 * @param code
	 * @param path
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private List<StockPriceModel> getMarketData(String code, String path)
			throws FileNotFoundException, IOException {
		List<StockPriceModel> list;
		list = new ArrayList<StockPriceModel>();
		StockPriceModel spm = null, lastModel = null;
		File file = new File(path);
		if (!file.exists())
			return null;
		FileInputStream ins = new FileInputStream(file);
		byte[] tempbytes = new byte[32];
		int byteread = 0;
		// 读入多个字节到字节数组中，byteread为一次读入的字节数
		while ((byteread = ins.read(tempbytes)) != -1) {
			// "代号"+"股票代码"+".day"命名，代号为"sh"或"sz"，股票代码6位，一个记录32个字节。
			String str = hex2bin(tempbytes);
			spm = getStock(str);
			spm.setCode(code);
			if (lastModel != null) {// 存在昨日收盘价（疑问某一年的第一个交易日未出现涨停，但是与去年的最后一个交易日计算出来的涨幅是涨停会不会存在）
				double std = (spm.getClose() - lastModel.getClose())
						/ lastModel.getClose() * 100;// 单日涨幅
				if (std > 9.8) {// 涨停
					spm.setState(1);
				} else if (std < -9.8) {// 跌停
					spm.setState(-1);
				} else if (std >= 5.0) {// 涨幅在5到9.8之间
					spm.setState(2);
				}
				spm.setColor(std >= 0 ? "#FF0000" : "#00FF00");
				spm.setYclose(lastModel.getClose());
			}
			list.add(spm);
			lastModel = spm;
		}
		return list;
	}

	private static StockPriceModel getStock(String str) {
		StockPriceModel spm = new StockPriceModel();
		String dateStr = str.substring(6, 8) + str.substring(4, 6)
				+ str.substring(2, 4) + str.substring(0, 2);
		String date = Integer.parseInt(dateStr, 16) + "";
		spm.setDate(DateUtil.strToDate(date, "yyyyMMdd"));
		String openStr = str.substring(14, 16) + str.substring(12, 14)
				+ str.substring(10, 12) + str.substring(8, 10);
		Double open = Integer.parseInt(openStr, 16) / 100.0;
		spm.setOpen(open);
		String hhStr = str.substring(22, 24) + str.substring(20, 22)
				+ str.substring(18, 20) + str.substring(16, 18);
		Double hh = Integer.parseInt(hhStr, 16) / 100.0;
		spm.setHhv(hh);
		String llStr = str.substring(30, 32) + str.substring(28, 30)
				+ str.substring(26, 28) + str.substring(24, 26);
		Double ll = Integer.parseInt(llStr, 16) / 100.0;
		spm.setLlv(ll);
		String closeStr = str.substring(38, 40) + str.substring(36, 38)
				+ str.substring(34, 36) + str.substring(32, 34);
		Double close = Integer.parseInt(closeStr, 16) / 100.0;
		spm.setClose(close);
		String vStr = str.substring(54, 56) + str.substring(52, 54)
				+ str.substring(50, 52) + str.substring(48, 50);
		int v = Integer.parseInt(vStr, 16);
		spm.setVol(v);
		String buyStr = str.substring(46, 48) + str.substring(44, 46)
				+ str.substring(42, 44) + str.substring(40, 42);
		int buy = Integer.parseInt(buyStr, 16);
		spm.setBuy(buy);
		return spm;
	}

	/**
	 * 数据包转化为16进制
	 * 
	 * @param b
	 * @return
	 */
	private static String hex2bin(byte[] b) {
		StringBuffer sb = new StringBuffer("");
		String str, next;
		boolean flag = false;
		for (int i = 0; i < b.length; i++) {
			str = Integer.toHexString(0xFF & b[i]);
			if (str.length() == 1) {
				sb.append(0);
			}
			sb.append(str.toUpperCase());
			if (flag)
				break;
			if (i <= b.length - 2) {
				next = Integer.toHexString(0xFF & b[i + 1]);
				if ("d".equals(str.toLowerCase())
						&& "a".equals(next.toLowerCase())) {
					flag = true;
					continue;
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 根据代码获取路径
	 * 
	 * @param code
	 * @return
	 */
	private String[] getPathByCode(String code) {
		String[] arr = new String[2];
		if (StringUtil.isNotNull(code)) {
			String lday = code.substring(0, 1);
			if (StringUtil.equals(lday, "6")) {
				arr[1] = "T0002/export/SH" + code + ".TXT";
			} else {
				if ("999999".equals(code)) {
					arr[1] = "vipdoc/sh/lday/sh999999.day";
					arr[0] = "true";
				} else if ("399006".equals(code) || "399300".equals(code)
						|| "399001".equals(code) || "399005".equals(code)) {
					arr[1] = "vipdoc/sz/lday/sz" + code + ".day";
					arr[0] = "true";
				} else {
					arr[1] = "T0002/export/SZ" + code + ".TXT";
				}
			}
		}
		return arr;
	}

	@Override
	public Map<String, Map<String, Object>> getStocksCapital() {
		return this.stockStatisticsMapper.getStocksCapital();
	}

	@Override
	public List<Map<String, Object>> selectStockUrls(Criteria criteria) {
		return stockStatisticsMapper.selectStockUrls(criteria);
	}
}
