package com.stock.runnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.htmlparser.Parser;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authority.common.db.Mysql;
import com.authority.common.utils.DateUtil;
import com.authority.common.utils.StringUtil;
import com.stock.pojo.StockTrackWarn;
import com.stock.pojo.StockWarn;
import com.stock.service.StockWarnTrackService;

/**
 * 预警抓去线程
 * 
 * @author yu_qhai
 * 
 */
public class MonitorRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(MonitorRunnable.class);
	private StockWarnTrackService stockWarnTrackService;
	private String code;
	private Map<String, Object> track;
	private int days;// 连续涨跌天数

	public MonitorRunnable(StockWarnTrackService stockWarnTrackService,
			String code, Map<String, Object> track, Map<String, Object> days) {
		this.stockWarnTrackService = stockWarnTrackService;
		this.code = code;
		this.track = track;
		this.days = (Integer) days.get("uddays");
	}

	@Override
	public void run() {
		/* 避免多次获取股票信息 */
		String[] datas = getStockDatas(code);
		if (datas == null) {
			return;
		}
		List<String> sqls = new ArrayList<String>();
		/* 成交量预警处理 */
		StockWarn model = nodeFilterTagName(datas);
		if (model != null && model.getTov() <= model.getSumprice()) {
			String sql = stockWarnTrackService.saveWarnStock(model);
			if (StringUtil.isNotNull(sql))
				sqls.add(sql);
		}
		/* 抓取数据，并提示警告，以托盘闪烁呈现，双击时弹出窗口 */
		if (track != null) {
			StockTrackWarn sw = nodeFilterTagName(track, datas);
			if (sw != null) {
				String sql = stockWarnTrackService.saveWarnStock(sw), sql1;
				if (StringUtil.isNotNull(sql))
					sqls.add(sql);
				sql1 = stockWarnTrackService.updateTrace(code, sw);
				if (StringUtil.isNotNull(sql1))
					sqls.add(sql1);
			}
		}
		/** 统计连阴连阳交易日统计 */
		String str = stockWarnTrackService.saveDays(code, getDays(days, datas));
		if (StringUtil.isNotNull(str))
			sqls.add(str);
		if (sqls.size() > 0) {
			Connection conn = null;
			Statement st = null;
			try {
				conn = Mysql.getConnection();
				st = conn.createStatement();
				for (String sql : sqls) {
					st.addBatch(sql);
				}
				st.executeBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("批量保存{}预警时失败,异常信息{}", code, e);
			} finally {
				if (st != null)
					try {
						st.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
	}

	/**
	 * 抓取某只个股对象,进行轨道预警判断
	 * 
	 * @param code
	 * @return
	 */
	public StockTrackWarn nodeFilterTagName(Map<String, Object> track,
			String[] datas) {
		try {
			String code = (String) track.get("code");
			// 如果当前价格上传当前轨道提示
			String trackStr = (String) track.get("track"), trackCache, targetTrack;// 当前所处轨道
			double np = Double.parseDouble(datas[5]);// 最新价
			trackCache = getTrack(np, track, trackStr);// 预警轨道描述
			targetTrack = getTargetTrack(np, track);
			if (trackCache == null && targetTrack == null)
				return null;
			if (Double.parseDouble(datas[5]) <= 0)
				return null;// 停盘的不做预警
			trackCache = trackCache != null ? trackCache + targetTrack == null ? ""
					: targetTrack
					: targetTrack == null ? "" : targetTrack;
			Date today = DateUtil.strToDateTime(datas[datas.length - 5]);// 时间
			StockTrackWarn warn = new StockTrackWarn();
			warn.setCode(code);
			warn.setTrack(trackStr);
			warn.setTrack_ud(trackCache);// 设置轨道趋势描述
			warn.setStart(today);
			warn.setNprice(Double.parseDouble(datas[5]));// 最新价
			warn.setZhang(Double.parseDouble(datas[11].substring(0,
					datas[11].length() - 1)));// 涨幅
			return warn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 抓取某只个股对象
	 * 
	 * @param code
	 * @return
	 */
	public StockWarn nodeFilterTagName(String[] datas) {
		try {
			StockWarn model = new StockWarn();
			model.setCode(datas[1]);// 编号
			model.setName(datas[2]);
			model.setYclose(Double.parseDouble(datas[3]));// 昨日收盘价
			model.setNprice(Double.parseDouble(datas[5]));// 最新价
			model.setDate(DateUtil.strToDateTime(datas[datas.length - 5]));// 时间
			model.setCapital(Long.parseLong(datas[datas.length - 3]));// 流通股
			double capitalPrice = model.getYclose() * model.getCapital() / 2
					/ 12 * 0.8;
			model.setTov(capitalPrice);
			model.setSumprice(Long.parseLong(datas[8]) * 1E4);
			model.setZhang(Double.parseDouble(datas[11].substring(0,
					datas[11].length() - 1)));// 涨幅
			model.setMinPrice(Double.parseDouble(datas[7]));
			return model;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 根据编号获取股票信息
	 * 
	 * @param code
	 * @return
	 */
	public String[] getStockDatas(String code) {
		int num = 0;
		do {
			try {
				Parser parser = new Parser();
				parser.setURL("http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=Z&ids="
						+ code + (code.charAt(0) == '6' ? "1" : "2"));
				parser.setEncoding("UTF-8");
				TextExtractingVisitor visitor = new TextExtractingVisitor();
				parser.visitAllNodesWith(visitor);
				String str = visitor.getExtractedText();
				String msg = str.substring(str.indexOf("[") + 2,
						str.indexOf("]") - 1);
				String[] datas = msg.split(",");
				if (datas.length < 2)
					return null;
				return datas;
			} catch (Exception e) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (num == 4) {
					log.error("个股编号{}获取信息失败{}", code, e);
				}
				num++;
			}
		} while (num >= 5);
		return null;
	}

	/**
	 * 获取最新轨道
	 * 
	 * @param np
	 * @param track
	 * @return
	 */
	private String getTrack(double np, Map<String, Object> track,
			String trackStr) {
		try {
			double tt = (Double) track.get("top_track_t"), td = (Double) track
					.get("top_track_d"), ct = (Double) track.get("cen_track_t"), cd = (Double) track
					.get("cen_track_d"), dt = (Double) track.get("dwn_track_t"), dd = (Double) track
					.get("dwn_track_d"), st = (Double) track.get("slf_track_t"), sd = (Double) track
					.get("slf_track_d");
			if ("上轨".equals(trackStr) && np > tt) {
				return "突破上轨";
			} else if ("中轨".equals(trackStr) && np > ct) {
				return "突破中轨";
			} else if ("下轨".equals(trackStr) && np > dt) {
				return "突破下轨";
			} else if ("上轨".equals(trackStr) && np < td) {
				return "击穿上轨";
			} else if ("中轨".equals(trackStr) && np < cd) {
				return "击穿中轨";
			} else if ("下轨".equals(trackStr) && np < dd) {
				return "击穿下轨";
			} else if ("自定".equals(trackStr) && np > st) {
				return "突破自定";
			} else if ("自定".equals(trackStr) && np < sd) {
				return "击穿自定";
			}
		} catch (Exception e) {
			log.debug("{}为设置全中轨、上轨、下轨、自定区间等信息", track.get("code"));
		}
		return null;
	}

	/**
	 * 根据最新价预警目标价、周线、月线、深水区
	 * 
	 * @param np
	 * @param track
	 * @return
	 */
	private String getTargetTrack(double np, Map<String, Object> track) {
		try {
			double target = (Double) track.get("targetprice"), week = (Double) track
					.get("weekprice"), month = (Double) track.get("monthprice"), deep = (Double) track
					.get("deepprice");
			if (np == target) {
				return "->目标价";
			} else if (np == week) {
				return "->周线";
			} else if (np == month) {
				return "->月线";
			} else if (np == deep) {
				return "->深水区";
			}
		} catch (Exception e) {
			log.debug("{}为设置周线、月线、深水区、目标价等信息", track.get("code"));
		}
		return null;
	}

	/**
	 * 获取连续涨跌天数
	 * 
	 * @param days
	 * @param datas
	 * @return
	 */
	private int getDays(int days, String[] datas) {
		String str = datas[11].replaceAll("%", "");
		double ud = Double.parseDouble(str);
		if (ud >= 1 && days >= 0) {
			return ++days;
		} else if (ud <= -1 && days <= 0) {
			return --days;
		} else {
			return 0;
		}
	}

}
