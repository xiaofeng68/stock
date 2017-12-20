package com.kidinfor.capture.core.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.kidinfor.capture.core.entity.ShareholderNumber;
import com.kidinfor.capture.core.entity.StockCode;
import com.kidinfor.capture.core.entity.StockJijinBD;
import com.kidinfor.capture.core.entity.StockJijinCC;
import com.kidinfor.capture.core.entity.StockPrice;
import com.kidinfor.capture.core.entity.TenLargestShareholder;
import com.kidinfor.capture.core.entity.TenldLargestShareholder;
import com.kidinfor.capture.core.repo.ShareholderNumberRepo;
import com.kidinfor.capture.core.repo.StockCodeRepo;
import com.kidinfor.capture.core.repo.StockJijinBDRepo;
import com.kidinfor.capture.core.repo.StockJijinCCRepo;
import com.kidinfor.capture.core.repo.StockPriceRepo;
import com.kidinfor.capture.core.repo.TenLargesShareholerRepo;
import com.kidinfor.capture.core.repo.TenldLargesShareholerRepo;
import com.kidinfor.capture.core.service.StockService;
import com.kidinfor.capture.utils.DateUtil;
import com.kidinfor.capture.utils.StockUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class StockServiceImpl implements StockService {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Value("${stock.sdgd}")
	private String sdgd;
	@Value("${stock.codes}")
	private String codes;
	@Value("${stock.price}")
	private String price;
	@Value("${stock.jijin}")
	private String jijincc;
	@Value("${stock.jijinbd}")
	private String jijinbd;

	@Autowired
	private TenLargesShareholerRepo tenLargesShareholerRepo;
	@Autowired
	private TenldLargesShareholerRepo tenldLargesShareholerRepo;
	@Autowired
	private ShareholderNumberRepo shareholderNumberRepo;
	@Autowired
	private StockCodeRepo stockCodeRepo;
	@Autowired
	private StockPriceRepo stockPriceRepo;
	@Autowired
	private StockJijinCCRepo stockJijinCCRepo;
	@Autowired
	private StockJijinBDRepo stockJijinBDRepo;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateHolders(String code) throws Exception {
		Response res = Jsoup.connect(sdgd + code).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		JSONObject json = JSONObject.fromObject(body);
		List<ShareholderNumber> list = new ArrayList<>();
		json.getJSONArray("gdrs").forEach(gdrs -> {
			JSONObject cache = JSONObject.fromObject(gdrs);
			ShareholderNumber number = new ShareholderNumber();
			number.setCode(code);
			number.setRq(cache.getString("rq"));
			number.setGdrs(StockUtil.string2Double(cache.getString("gdrs")));
			number.setGdrs_jsqbh(StockUtil.string2Double(cache.getString("gdrs_jsqbh")));
			number.setRjltg(StockUtil.string2Double(cache.getString("rjltg")));
			number.setRjltg_jsqbh(StockUtil.string2Double(cache.getString("rjltg_jsqbh")));
			number.setCmjzd(cache.getString("cmjzd"));
			number.setGj(StockUtil.string2Double(cache.getString("gj")));
			number.setRjcgje(StockUtil.string2Double(cache.getString("rjcgje")));
			number.setQsdgdcghj(StockUtil.string2Double(cache.getString("qsdgdcghj")));
			number.setQsdltgdcghj(StockUtil.string2Double(cache.getString("qsdltgdcghj")));
			list.add(number);
		});
		shareholderNumberRepo.save(list);
		List<TenLargestShareholder> tenList = new ArrayList<>();
		json.getJSONArray("sdgd").forEach(gd -> {
			JSONObject cache = JSONObject.fromObject(gd);
			cache.getJSONArray("sdgd").forEach(csdgd -> {
				JSONObject ccache = JSONObject.fromObject(csdgd);
				TenLargestShareholder ten = new TenLargestShareholder();
				ten.setCode(code);
				ten.setRq(ccache.getString("rq"));
				ten.setMc(ccache.getInt("mc"));
				ten.setGdmc(ccache.getString("gdmc"));
				ten.setGflx(ccache.getString("gflx"));
				ten.setCgs(StockUtil.string2Long(ccache.getString("cgs")));
				ten.setZltgbcgbl(StockUtil.string2Double(ccache.getString("zltgbcgbl")));
				ten.setZj(ccache.getString("zj"));
				ten.setBdbl(StockUtil.string2Double(ccache.getString("bdbl")));
				tenList.add(ten);
			});
		});
		tenLargesShareholerRepo.save(tenList);
		List<TenldLargestShareholder> tenltList = new ArrayList<>();
		json.getJSONArray("sdltgd").forEach(gd -> {
			JSONObject cache = JSONObject.fromObject(gd);
			cache.getJSONArray("sdltgd").forEach(csdgd -> {
				JSONObject ccache = JSONObject.fromObject(csdgd);
				TenldLargestShareholder ten = new TenldLargestShareholder();
				ten.setCode(code);
				ten.setRq(ccache.getString("rq"));
				ten.setMc(ccache.getInt("mc"));
				ten.setGdmc(ccache.getString("gdmc"));
				ten.setGflx(ccache.getString("gflx"));
				ten.setCgs(StockUtil.string2Long(ccache.getString("cgs")));
				ten.setZltgbcgbl(StockUtil.string2Double(ccache.getString("zltgbcgbl")));
				ten.setZj(ccache.getString("zj"));
				ten.setBdbl(StockUtil.string2Double(ccache.getString("bdbl")));
				tenltList.add(ten);
			});
		});
		tenldLargesShareholerRepo.save(tenltList);
	}

	@Override
	public void updateCodes() throws Exception {
		Document document = Jsoup.connect(codes).timeout(50000).get();
		List<StockCode> codeList = new ArrayList<>();
		document.body().getElementsByClass("quotebody").get(0).getElementsByTag("li").forEach(li -> {
			StockCode stock = new StockCode();
			stock.setCode(StockUtil.getCode(li.getElementsByTag("a").get(0).attr("href")));
			String text = li.text();
			int index = text.indexOf("(");
			stock.setName(text.substring(0, index));
			stock.setScode(text.substring(index + 1, index + 7));
			codeList.add(stock);
		});
		stockCodeRepo.save(codeList);
	}

	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updatePrice(String code) throws Exception {
		Response res = Jsoup.connect(price + code).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		int start = body.indexOf("=") + 1;
		int end = body.indexOf(";") - 1;
		String[] datas = body.substring(start, end).split(",");
		if (datas.length == 0)
			return;
		StockPrice price = new StockPrice();
		price.setCode(code);
		price.setJk(Double.parseDouble(datas[1]));// 今日开盘价
		price.setZs(Double.parseDouble(datas[2]));// 昨日收盘价
		price.setPrice(Double.parseDouble(datas[3]));// 当前价格
		price.setZg(Double.parseDouble(datas[4]));// 今日最高价
		price.setZd(Double.parseDouble(datas[5]));// 今日最低价
		price.setCjl(Long.parseLong(datas[8]));
		price.setCje(Double.parseDouble(datas[9]));
		price.setTime(datas[30] + " " + datas[31]);
		stockPriceRepo.save(price);
	}

	@Override
	public List<StockCode> getCodes() {
		return stockCodeRepo.findAll();
	}

	@Override
	public void truncateHolders() {
		shareholderNumberRepo.deleteAllInBatch();
		tenLargesShareholerRepo.deleteAllInBatch();
		tenldLargesShareholerRepo.deleteAllInBatch();
	}

	@Override
	public void truncatePrice() {
		stockPriceRepo.deleteAllInBatch();
	}

	@Override
	public void updateType(String code, String scode) throws Exception {
		// 获取token
		String codeUrl = "http://quote.eastmoney.com/" + code + ".html";
		Document document = Jsoup.connect(codeUrl).timeout(50000).get();
		String src = document.body().getElementById("picr").attr("src");
		String token = src.split("&token=")[1];
		Long random = ((Double) (Math.random() * 1000000000)).longValue();
		// 获取异动
		String ydUrl = "http://nuyd.eastmoney.com/EM_UBG_PositionChangesInterface/api/js?style=top&js=([(x)])&ac=normal&check=itntcd&dtformat=HH:mm:ss&id="
				+ scode + "2&_=" + random;
		String ydBack = getUrlBack(ydUrl);
		System.out.println("ydBack=" + ydBack);
		// 所属板块
		String typeUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=E." + scode
				+ "2&sty=DCRRBKCPALTB&st=z&sr=-1&p=&ps=5&token=" + token;
		String typeBack = getUrlBack(typeUrl);
		System.out.println("typeBack=" + typeBack);
		// 最新价格
		String dtUrl = "http://nuff.eastmoney.com/EM_Finance2015TradeInterface/JS.ashx?id=" + scode + "2&token=" + token
				+ "&_=" + random;
		String dtBack = getUrlBack(dtUrl);
		System.out.println("dtBack=" + dtBack);
		// 板块领涨
		String lzUrl = "http://nufm.dfcfw.com/EM_Finance2014NumericApplication/JS.aspx?type=CT&cmd=C.BK05381&sty=FDCS&st=C&sr=-1&p=1&ps=5&lvl=&cb=&js=var%20jspy=[(x)];&token="
				+ token + "&v=0.7949215805889078&_=" + random;
		String lzBack = getUrlBack(lzUrl);
		System.out.println("lzBack=" + lzBack);
	}

	private String getUrlBack(String ydUrl) throws IOException {
		Response ydRes = Jsoup.connect(ydUrl).timeout(50000).ignoreContentType(true).execute();
		return ydRes.body();
	}

	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateJijinCC() throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date maxDate = stockJijinCCRepo.findMaxDate();
		if (maxDate == null) {
			maxDate = df.parse("2011-06-30");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(maxDate);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 3);
		Date time = DateUtil.getLastDayOfQuarter(calendar.getTime());

		String date = df.format(time);
		Response res = Jsoup.connect(jijincc + date).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		int start = body.indexOf("=") + 1;
		String obj = body.substring(start);
		JSONObject jsonObj = JSONObject.fromObject(obj);
		JSONArray array = jsonObj.getJSONArray("data");
		List<StockJijinCC> list = new ArrayList<StockJijinCC>();
		for (Object data : array) {
			jsonObj = (JSONObject) data;
			StockJijinCC jijinCC = new StockJijinCC();
			jijinCC.setCode(jsonObj.getString("SCode"));
			jijinCC.setNum(jsonObj.getInt("Count"));
			jijinCC.setcGChange(jsonObj.getString("CGChange"));
			jijinCC.setShareHDNum(jsonObj.getInt("ShareHDNum"));
			jijinCC.setVPosition(jsonObj.getDouble("VPosition"));
			jijinCC.setTabRate(jsonObj.getDouble("TabRate"));
			jijinCC.setLTZB(jsonObj.getDouble("LTZB"));
			jijinCC.setShareHDNumChange(jsonObj.getDouble("ShareHDNumChange"));
			jijinCC.setRateChange(jsonObj.getDouble("RateChange"));
			jijinCC.setTime(time);
			list.add(jijinCC);
		}
		if (list.size() > 0)
			stockJijinCCRepo.save(list);
	}

	@Override
	public List<String> findJJCode() {
		return stockJijinCCRepo.findJJCodes();
	}

	@SuppressWarnings("resource")
	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateJijinBD(String code) throws Exception {
		String codeUrl = jijinbd + code + ".html";
		try {
		// 构造一个webClient 模拟Chrome 浏览器
		WebClient webClient = new WebClient(BrowserVersion.CHROME);
		
		// 支持JavaScript
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setTimeout(5000);
		HtmlPage rootPage = webClient.getPage(codeUrl);
		// 设置一个运行JavaScript的时间
		webClient.waitForBackgroundJavaScript(5000);
		String html = rootPage.asXml();
		Document document = Jsoup.parse(html);
		Elements trs = document.body().getElementsByClass("tab1").select("tr");
		List<StockJijinBD> list = new ArrayList<StockJijinBD>();
		for (int i = 0,k=trs.size(); i < k; i++) {
			Elements tds = trs.get(i).select("td");
			if(tds.size()>0) {
				StockJijinBD bd = new StockJijinBD();
				bd.setCode(code);
				bd.setTime(tds.get(1).text());
				bd.setShareHDNum(10E4*Double.parseDouble(tds.get(2).text()));
				bd.setvPosition(10E8*Double.parseDouble(tds.get(3).text()));
				bd.setTabRate(Double.parseDouble(tds.get(4).text()));
				bd.setlTZB(Double.parseDouble(tds.get(5).text()));
				bd.setCgzj(10E4*Double.parseDouble(tds.get(6).text()));
				bd.setCgzjd(Double.parseDouble(tds.get(7).text()));
				list.add(bd);
			}
		}
		stockJijinBDRepo.save(list);
		}catch(Exception e) {
			log.error("{}获取基金变动失败！",code);
		}
	}

	@Override
	public void truncateJijinBD() {
		stockJijinBDRepo.deleteAllInBatch();
	}
}