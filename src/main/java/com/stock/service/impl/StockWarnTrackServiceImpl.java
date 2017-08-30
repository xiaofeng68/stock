package com.stock.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authority.common.utils.DateUtil;
import com.authority.pojo.Criteria;
import com.stock.dao.StockWarnTrackMapper;
import com.stock.pojo.StockCzb;
import com.stock.pojo.StockHandler;
import com.stock.pojo.StockIncoming;
import com.stock.pojo.StockRestricted;
import com.stock.pojo.StockTrackWarn;
import com.stock.pojo.StockWarn;
import com.stock.service.StockWarnTrackService;

/**
 * 量能轨道预警实现
 * 
 * @author yu_qhai
 * 
 */
@Service(value = "stockWarnTrackService")
public class StockWarnTrackServiceImpl implements StockWarnTrackService {
	private Logger log = LoggerFactory
			.getLogger(StockWarnTrackServiceImpl.class);
	private static String czburl_ = "http://data.eastmoney.com/Stock/data.aspx?style=G&traderid=";
	private static String czburl = "http://data.eastmoney.com/soft/Stock/TradeSearchHistory.aspx?id=";// 财政部龙虎榜
	private static String handlerurl = "http://f9.eastmoney.com/soft/gp51.php";// 个股十大股东
	private static String INCOMINGURL = "http://f9.eastmoney.com/soft/gp13.php?exp=0&tp=4";
	private static String URL = "http://f9.eastmoney.com/soft/gp6.php";

	@Autowired
	StockWarnTrackMapper stockWarnTrackMapper;

	/**
	 * 保存预警信息
	 * 
	 * @param model
	 */
	public String saveWarnStock(StockWarn model) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock_warn");
			sql.append(" (code,day_d,yclose,capital,tov,state,name,sumprice,zhang,minprice,times)");
			sql.append("values(");
			String code = model.getCode();
			sql.append("'" + code + "',");
			String day_d = DateUtil.dateToStr(model.getDate());
			sql.append("'" + day_d + "',");
			double yclose = model.getYclose();
			sql.append(yclose + ",");
			double capital = model.getCapital();
			sql.append(capital + ",");
			double tov = model.getTov();
			sql.append(tov + ",");
			int state = model.getState();
			sql.append(state + ",");
			String name = model.getName();
			sql.append("'" + name + "',");
			double sumprice = model.getSumprice();
			sql.append(sumprice + ",");
			double zhang = model.getZhang();
			sql.append(zhang + ",");
			double minprice = model.getMinPrice();
			sql.append(minprice + ",");
			String times = DateUtil.dateToStr(model.getDate(), "HH:mm");
			sql.append("'" + times + "'");
			sql.append(") ON DUPLICATE KEY UPDATE code='" + code + "',");
			sql.append("day_d='" + day_d + "',");
			sql.append("yclose=" + yclose + ",");
			sql.append("capital=" + capital + ",");
			sql.append("tov=" + tov + ",");
			sql.append("state=" + state + ",");
			sql.append("name='" + name + "',");
			sql.append("sumprice=" + sumprice + ",");
			sql.append("zhang=" + zhang + ",");
			sql.append("minprice=" + minprice + ",");
			sql.append("times='" + times + "'");
			return sql.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存轨道预警信息
	 * 
	 * @param model
	 */
	public String saveWarnStock(StockTrackWarn model) {
		try {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock_track_warn");
			sql.append(" (code,track,track_ud,days,nprice,zhang)");
			sql.append("values(");
			String code = model.getCode();
			sql.append("'" + code + "',");
			String track = model.getTrack();
			sql.append("'" + track + "',");
			String track_ud = model.getTrack_ud();
			sql.append("'" + track_ud + "',");
			String days = DateUtil.dateToStr(model.getStart());
			sql.append("'" + days + "',");
			double nprice = model.getNprice();
			sql.append(nprice + ",");
			double zhang = model.getZhang();
			sql.append(zhang);
			sql.append(") ON DUPLICATE KEY UPDATE code='" + code + "',");
			sql.append("track='" + track + "',");
			sql.append("track_ud='" + track_ud + "',");
			sql.append("days='" + days + "',");
			sql.append("nprice=" + nprice + ",");
			sql.append("zhang=" + zhang);
			return sql.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String updateTrace(String code, StockTrackWarn model) {
		String trackUd = model.getTrack_ud(), track;
		if ("突破中轨".equals(trackUd)) {
			track = "上轨";
		} else if ("突破下轨".equals(trackUd)) {
			track = "中轨";
		} else if ("击穿中轨".equals(trackUd)) {
			track = "下轨";
		} else if ("击穿上轨".equals(trackUd)) {
			track = "中轨";
		} else {
			return null;
		}
		return "update stock_track set track='" + track + "' where code='"
				+ code + "'";
	}

	public String saveDays(String code, int days) {
		return "update stock_content set uddays =" + days + " where code='"
				+ code + "'";
	}

	@Override
	public List<StockCzb> getPageCzbStocks(String type) {
		Map<String, Object> dateMap = stockWarnTrackMapper
				.selectMaxStockCzbData(type);
		Date date = (Date) dateMap.get("mday");
		List<StockCzb> sList = new ArrayList<StockCzb>();
		try {
			for (int i = 1, j = getPagesize(type); i <= j; i++) {
				Parser parser = new Parser();
				parser.setURL(czburl_ + type + "&page=" + i + "&pageSize=50");
				parser.setEncoding("gbk");
				TextExtractingVisitor visitor = new TextExtractingVisitor();
				parser.visitAllNodesWith(visitor);
				String jsonStr = visitor.getExtractedText();
				JSONObject obj = JSONObject.fromObject(jsonStr.substring(
						jsonStr.indexOf("{"), jsonStr.indexOf("}") + 1));
				JSONArray arr = obj.getJSONArray("data");
				String[] stockArr;
				StockCzb stock;
				for (Object oo : arr) {
					stockArr = ((String) oo).split(",");
					stock = new StockCzb();
					stock.setType_code(type);
					stock.setCode(stockArr[0]);
					stock.setDay_d(DateUtil.strToDate(stockArr[3]));
					if (date != null && !date.before(stock.getDay_d()))
						throw new RuntimeException("查找结束");// 在数据最新时间之前的不做处理了
					stock.setBuy_p(Double.parseDouble(stockArr[4]));
					stock.setSell_p(Double.parseDouble(stockArr[5]));
					sList.add(stock);
				}
			}
		} catch (Exception e) {
		}
		return sList;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void saveBatchStockCzb(List<StockCzb> list) {
		stockWarnTrackMapper.saveBatchStockCzb(list);
	}

	private int getPagesize(String type) {
		try {
			Parser parser = new Parser();
			parser.setURL(czburl + type);
			parser.setEncoding("gbk");
			// 过滤页面中的链接标签
			NodeFilter filter = new TagNameFilter("script");// new
															// HasAttributeFilter("class",
															// "pagea");
			NodeList list = parser.extractAllNodesThatMatch(filter);
			Node node = (Node) list.elementAt(list.size() - 1);
			String str = node.toPlainTextString();// 要保存的核心题材
			return Integer.parseInt(str.substring(str.indexOf("pages:") + 6,
					str.indexOf("}")));
		} catch (Exception e) {
			log.error("编号在抓取核心题材时,出现异常：{}", e);
		}
		return 0;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateStockCoreContent(String code) {
		try {
			String[] strs = updateStockContent(code);
			if (strs == null)
				throw new RuntimeException();
			Criteria criteria = new Criteria();
			criteria.put("code", code);
			criteria.put("oldname", nodeFilterTabOldName(code));// 曾用名
			criteria.put("content", nodeFilterTagName(code));// 核心内容
			criteria.put("name", strs[0]);// 名称
			criteria.put("capital", strs[1]);// 流通股本
			stockWarnTrackMapper.updateStockContent(criteria);
		} catch (Exception e) {
			log.error("{}获取个股基本信息时失败:", code, e);
		}
	}

	/**
	 * 通过url抓取-获取股票核心题材内容
	 * 
	 * @param url
	 * @param encoding
	 * @param tagName
	 *            String url =
	 *            "http://f9.eastmoney.com/soft/gp30.php?code=30005902";
	 *            nodeFilterTagName(url, "UTF-8", "p");
	 * @return
	 */
	public String nodeFilterTagName(String code) {
		int errorTimes = 0;
		do {
			try {
				Parser parser = new Parser();
				parser.setURL("http://f9.eastmoney.com/soft/gp30.php?code="
						+ code + (code.charAt(0) == '6' ? "01" : "02"));
				parser.setEncoding("UTF-8");
				// 过滤页面中的链接标签
				NodeFilter filter = new TagNameFilter("p");
				NodeList list = parser.extractAllNodesThatMatch(filter);
				Node node = (Node) list.elementAt(0);
				return node.toPlainTextString();// 要保存的核心题材
			} catch (Exception e) {
				errorTimes++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
				if (errorTimes == 5)
					log.error("编号在抓取核心题材时,出现异常：" + e);
			}
		} while (errorTimes < 5);
		return "";
	}

	/**
	 * 根据个股编号抓去曾用名
	 * 
	 * @param code
	 * @return
	 */
	private String nodeFilterTabOldName(String code) {
		int errorTimes = 0;
		do {// 如果解析失败，再次请求
			try {
				Parser parser = new Parser();
				parser.setURL("http://f10.eastmoney.com/f10_v2/CompanySurvey.aspx?type=soft&code=!!"
						+ code + "#jbzl-0");
				parser.setEncoding("UTF-8");
				// 过滤页面中的链接标签
				NodeFilter filter = new TagNameFilter("td");
				NodeList list = parser.extractAllNodesThatMatch(filter);
				Node node = (Node) list.elementAt(2);
				return node.toPlainTextString();// 要保存的核心题材
			} catch (Exception e) {
				errorTimes++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
				if (errorTimes == 5)
					log.error("编号" + code + "在抓取核心题材时,出现异常：" + e.getMessage());
			}
		} while (errorTimes < 5);
		return "";
	}

	/**
	 * 获取名称和流通市值
	 * 
	 * @param code
	 * @return
	 */
	private String[] updateStockContent(String code) {
		int errorTimes = 0;
		do {// 如果解析失败，再次请求
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
				return new String[] { datas[2], datas[datas.length - 3] };
			} catch (Exception e) {
				errorTimes++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
				if (errorTimes == 5)
					log.error("获取" + code + "股票消息信息时失败：" + e);
			}
		} while (errorTimes < 5);
		return null;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public void updateHolderState(Criteria criteria) {
		stockWarnTrackMapper.updateHolderState(criteria);
	}

	@Override
	public List<String> updateStockHolders(String code) {
		List<String> sqls = new ArrayList<String>();
		List<StockHandler> list = nodeFilterHolderTagName(code);
		if (list == null || list.size() == 0)
			return sqls;
		for (StockHandler model : list) {
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO stock_ship");
			sql.append(" (code,stockholder,stockcount,stock_ratio,state,day_d,type,ios)");
			sql.append("values(");
			sql.append("'" + code + "',");
			String stockholder = model.getStockholder();
			sql.append("'" + stockholder + "',");
			double stockcount = model.getStockcount();
			sql.append(stockcount + ",");
			double stock_ratio = model.getStock_ratio();
			sql.append(stock_ratio + ",");
			String state = model.getState();
			sql.append("'" + state + "',");
			String day_d = DateUtil.dateToStr(model.getDay_d());
			sql.append("'" + day_d + "',");
			String type = model.getType();
			sql.append("'" + type + "',");
			String inout = model.getInout();
			sql.append("'" + inout + "'");
			sql.append(") ON DUPLICATE KEY UPDATE code='" + code + "',");
			sql.append("stockholder='" + stockholder + "',");
			sql.append("stockcount=" + stockcount + ",");
			sql.append("stock_ratio=" + stock_ratio + ",");
			sql.append("state='" + state + "',");
			sql.append("day_d='" + day_d + "',");
			sql.append("type='" + type + "',");
			sql.append("ios='" + inout + "'");
			sqls.add(sql.toString());
		}
		return sqls;
	}

	/**
	 * 抓去个股十大股东
	 * 
	 * @param code
	 * @return
	 */
	private List<StockHandler> nodeFilterHolderTagName(String code) {
		int errorTimes = 0;
		do {
			List<StockHandler> sList = new ArrayList<StockHandler>();
			try {
				Parser parser = new Parser();
				parser.setURL(handlerurl + "?code=" + code
						+ (code.charAt(0) == '6' ? "01" : "02"));
				parser.setEncoding("UTF-8");
				// 过滤页面中的链接标签
				NodeFilter filter = new TagNameFilter("tr");
				NodeList list = parser.extractAllNodesThatMatch(filter), cList;
				StockHandler stock;
				DecimalFormat df = new DecimalFormat("#.00");
				for (int i = 1; i < 11; i++) {
					try {
						Node node = list.elementAt(i);
						cList = node.getChildren();
						stock = new StockHandler();
						stock.setDay_d(DateUtil.strToDate(cList.elementAt(1)
								.toPlainTextString().replaceAll("\n", "")));
						stock.setCode(code);
						stock.setStockholder(cList.elementAt(5)
								.toPlainTextString().replaceAll("\n", "")
								.replaceAll("'", ""));
						stock.setType(cList.elementAt(7).toPlainTextString()
								.replaceAll("\n", ""));
						stock.setStockcount(Double.parseDouble(cList
								.elementAt(9).toPlainTextString()
								.replace("&nbsp;", "").replace(",", "").trim()));
						stock.setStock_ratio(Double.parseDouble(cList
								.elementAt(11).toPlainTextString()
								.replace("&nbsp;", "").replace(",", "").trim()));
						String typeStr = cList.elementAt(13)
								.toPlainTextString().replace("&nbsp;", "")
								.replaceAll("\n", "");
						String inout;
						try {
							typeStr = typeStr.replaceAll(",", "");
							double state = Double.parseDouble(typeStr);
							if (state / 1E8 > 1) {
								typeStr = df.format(state / 1E8) + "亿";
							} else if (state / 1E4 > 1) {
								typeStr = df.format(state / 1E4) + "万";
							} else if (state / 1E4 > -1E4) {
								typeStr = df.format(state / 1E4) + "万";
							} else if (state / 1E8 > -1E8) {
								typeStr = df.format(state / 1E8) + "亿";
							}
							inout = state > 0 ? "增持" : "减持";
						} catch (Exception e) {
							inout = typeStr;
						}
						stock.setInout(inout);
						stock.setState(typeStr);
						sList.add(stock);
					} catch (Exception e) {
					}
				}
				return sList;
			} catch (Exception e) {
				errorTimes++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
				if (errorTimes == 5)
					log.error("获取{}股票的十大流通股东时失败：{}", code, e);
			}
		} while (errorTimes < 5);
		return null;
	}

	@Override
	public int getLastYear() {
		Integer year = stockWarnTrackMapper.getLastYear();
		year = year== null ? 0 : year;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return year<cal.get(Calendar.YEAR)?cal.get(Calendar.YEAR):year;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public int updateIncoming(String code, int year) {
		StockIncoming stock = getStocksIncoming(code, year);
		if (stock == null)
			throw new RuntimeException("获取个股收益失败！");
		return stockWarnTrackMapper.saveIncoming(stock);
	}

	/**
	 * 抓去个股收益信息
	 * 
	 * @param conn
	 * @param codes
	 * @return
	 */
	private StockIncoming getStocksIncoming(String code, int year) {
		StockIncoming incoming = null;
		int errorTimes = 0;
		do {// 如果解析失败，再次请求
			try {// 处理掉请求失败的情况
				incoming = getIncomingOfStock(code, year);
			} catch (Exception e) {
				if (errorTimes == 4)
					log.equals("获取个股【" + code + "】收益时失败" + e.getMessage());
			}
			errorTimes++;
			if (errorTimes > 1)
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
		} while (incoming == null && errorTimes <= 5);
		if (incoming != null) {
			return incoming;
		}
		return null;
	}

	/**
	 * 解析财经信息：个股收益
	 * 
	 * @param code
	 * @return
	 * @throws ParserException
	 */
	private StockIncoming getIncomingOfStock(String code, int year)
			throws ParserException {
		Parser parser = new Parser();
		parser.setURL(INCOMINGURL + "&code=" + code
				+ (code.charAt(0) == '6' ? "01" : "02"));
		parser.setEncoding("UTF-8");
		// 过滤页面中的链接标签
		NodeFilter filter = new TagNameFilter("tr");
		NodeList list = parser.extractAllNodesThatMatch(filter);
		int stockHis = getYear(list.elementAt(32));
		if (year >= stockHis)
			throw new NullPointerException();
		StockIncoming stock = new StockIncoming();
		// 2，每股收益，14：营业收入，19:利润总额，32：报告日期
		stock.setCode(code);
		stock.setYear(stockHis);
		stock.setIncoming(getDoubleIncoming(list.elementAt(2)));
		stock.setAllincoming(getDoubleIncoming(list.elementAt(14)));
		stock.setSumincoming(getDoubleIncoming(list.elementAt(19)));
		nodeFilterTagName(stock);
		return stock;
	}

	/**
	 * 抓取某只个股对象
	 * 
	 * @param code
	 * @return
	 * @throws ParserException
	 */
	public void nodeFilterTagName(StockIncoming stock) throws ParserException {
		String code = stock.getCode();
		Parser parser = new Parser();
		parser.setURL("http://hqdigi2.eastmoney.com/EM_Quote2010NumericApplication/Index.aspx?Type=Z&ids="
				+ code + (code.charAt(0) == '6' ? "1" : "2"));
		parser.setEncoding("UTF-8");
		TextExtractingVisitor visitor = new TextExtractingVisitor();
		parser.visitAllNodesWith(visitor);
		String str = visitor.getExtractedText();
		String msg = str.substring(str.indexOf("[") + 2, str.indexOf("]") - 1);
		String[] datas = msg.split(",");
		if (datas.length < 2)
			throw new RuntimeException("信息不完整");
		else {
			stock.setCapital(Long.parseLong(datas[datas.length - 3]));// 流通股本
			stock.setNprice(Double.parseDouble(datas[3]));// 昨日收盘价，因为更新时可能出现为0的情况
		}
	}

	private int getYear(Node node) {
		String dateStr = node.getChildren().elementAt(3).toPlainTextString()
				.replaceAll("\n", "").replaceAll("&nbsp;", "").trim();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		dateStr = cal.get(Calendar.YEAR) / 100 + dateStr.substring(0, 2);
		return Integer.parseInt(dateStr);
	}

	private double getDoubleIncoming(Node node) {
		String incomingStr = node.getChildren().elementAt(3)
				.toPlainTextString().replaceAll("\n", "")
				.replaceAll("&nbsp;", "").trim();
		try {
			if ("--".equals(incomingStr))
				return 0;
			return Double.parseDouble(incomingStr);
		} catch (Exception e) {
			int index;
			if ((index = incomingStr.indexOf("万亿")) != -1) {
				return Double.parseDouble(incomingStr.substring(0, index)) * 1E12;
			} else if ((index = incomingStr.indexOf("亿")) != -1) {
				return Double.parseDouble(incomingStr.substring(0, index)) * 1E8;
			} else if ((index = incomingStr.indexOf("万")) != -1) {
				return Double.parseDouble(incomingStr.substring(0, index)) * 1E4;
			} else {
				throw new RuntimeException("单位超出最大值");
			}
		}
	}

	@Override
	public void updateStockRestricted(String code) {
		StockRestricted model = getRestricted(code);
		if (model == null)
			throw new RuntimeException("获取限售股信息失败！");
		stockWarnTrackMapper.updateStockRestricted(model);
	}

	private StockRestricted getRestricted(String code) {
		StockRestricted stock = null;
		int errorTimes=0;
		do {// 如果解析失败，再次请求
			try {
				Parser parser = new Parser();
				parser.setURL(URL + "?code=" + code
						+ (code.charAt(0) == '6' ? "01" : "02"));
				parser.setEncoding("UTF-8");
				// 过滤页面中的链接标签
				NodeFilter filter = new TagNameFilter("tr");
				NodeList list = parser.extractAllNodesThatMatch(filter);
				if (list == null)
					return null;
				Node node = list.elementAt(1);
				list = node.getChildren();
				stock = new StockRestricted();
				stock.setCode(code);
				stock.setDay_d(DateUtil.strToDate(node2Text(list.elementAt(1))));
				stock.setAllcount(Double.parseDouble(node2Text(list.elementAt(3))
						.replaceAll(",", "")));
				stock.setType(node2Text(list.elementAt(5)));
				stock.setTov(Double.parseDouble(node2Text(list.elementAt(7))
						.replaceAll(",", "")));
				stock.setToa(Double.parseDouble(node2Text(list.elementAt(9))
						.replaceAll(",", "")));
				return stock;
			} catch (Exception e) {
				errorTimes++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e.printStackTrace();
				}// 当出现错误后，让线程休息1秒再次请求
				if (errorTimes == 5)
					log.error("获取{}股票的限售时间时失败：{}", code, e);
			}
		} while (errorTimes <= 5);
		return null;
	}

	private String node2Text(Node node) {
		if (node.getChildren() != null) {
			ParagraphTag pt = (ParagraphTag) node.getChildren().elementAt(1);
			return pt.toPlainTextString();
		}
		return null;
	}
}
