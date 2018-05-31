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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.kidinfor.capture.core.entity.ShareholderNumber;
import com.kidinfor.capture.core.entity.StockBk;
import com.kidinfor.capture.core.entity.StockCode;
import com.kidinfor.capture.core.entity.StockJijinBD;
import com.kidinfor.capture.core.entity.StockJijinCC;
import com.kidinfor.capture.core.entity.StockLt;
import com.kidinfor.capture.core.entity.StockPrice;
import com.kidinfor.capture.core.entity.TenLargestShareholder;
import com.kidinfor.capture.core.entity.TenldLargestShareholder;
import com.kidinfor.capture.core.repo.ShareholderNumberRepo;
import com.kidinfor.capture.core.repo.StockBkRepo;
import com.kidinfor.capture.core.repo.StockCodeRepo;
import com.kidinfor.capture.core.repo.StockJijinBDRepo;
import com.kidinfor.capture.core.repo.StockJijinCCRepo;
import com.kidinfor.capture.core.repo.StockLtRepo;
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
	@Value("${stock.redian}")
	private String redian;
	@Value("${stock.lingzhang}")
	private String lingzhang;

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
	@Autowired
	private StockBkRepo stockBkRepo;
	@Autowired
	private StockLtRepo stockLtRepo;

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
    public void truncateCodes() {
	    stockCodeRepo.deleteAllInBatch();
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
		calendar.set(Calendar.DAY_OF_MONTH, 1);
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

	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateJijinBD(String code) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-M-d");
		Date maxDate = stockJijinBDRepo.findMaxDate(code);
		if (maxDate == null) {
			maxDate = df.parse("2011-6-30");
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(maxDate);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 3);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		Date time = DateUtil.getLastDayOfQuarter(calendar.getTime());
		String date = df.format(time);
		String codeUrl = jijinbd.replace("_code", code).replace("_fd", date);
		Response res = Jsoup.connect(codeUrl).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		JSONArray array = JSONArray.fromObject(body);
		List<StockJijinBD> list = new ArrayList<StockJijinBD>();
		for (Object data : array) {
			JSONObject jsonObj = (JSONObject) data;
			StockJijinBD jijinCC = new StockJijinBD();
			jijinCC.setCode(jsonObj.getString("SCode"));
			jijinCC.setShareHDNum(jsonObj.getDouble("ShareHDNum"));
			jijinCC.setVPosition(jsonObj.getDouble("Vposition"));
			jijinCC.setTabRate(jsonObj.getDouble("TabRate"));
			jijinCC.setSHName(jsonObj.getString("SHName"));
			jijinCC.setSHCode(jsonObj.getString("SHCode"));
			jijinCC.setType(jsonObj.getString("Type"));
			jijinCC.setBuyState(jsonObj.getString("BuyState"));
			jijinCC.setTime(time);
			list.add(jijinCC);
		}
		if (list.size() > 0)
			stockJijinBDRepo.save(list);
	}

	@Override
	public void truncateJijinBD() {
		stockJijinBDRepo.deleteAllInBatch();
	}

	@Override
	public void updateGainian() throws Exception {
		stockBkRepo.deleteByUpdateAt(new Date());
		Response res = Jsoup.connect(redian+Math.random()).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		String datas = body.substring("var BKCache=[".length(), body.length()-1);
		String[] arr = datas.split("\"");
		List<StockBk> list = new ArrayList<StockBk>();
		int i = 1;
		for(String str : arr) {
			if(StringUtils.isEmpty(str) ||",".equals(str)) continue;
			String[] bkArr = str.split(",");
			StockBk bk = new StockBk();
			bk.setCode(bkArr[1]);
			bk.setName(bkArr[2]);
			bk.setUd(Double.parseDouble(bkArr[3]));
			bk.setMarket(Double.parseDouble(bkArr[4]));
			bk.setRate(Double.parseDouble(bkArr[5]));
			bk.setSeq(i);
			String js = bkArr[6];
			String[] jsArr = js.split("\\|");
			bk.setUpnum(Integer.parseInt(jsArr[0]));
			bk.setDownnum(Integer.parseInt(jsArr[2]));
			list.add(bk);
			i++;
		}
		stockBkRepo.save(list);
	}
	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateLongtou(String code) throws Exception {
		String url = lingzhang.replace("_BK", code);
		Response res = Jsoup.connect(url+Math.random()).timeout(50000).ignoreContentType(true).execute();
		String body = res.body();
		String datas = body.substring(body.indexOf("[")+1, body.indexOf("]"));
		String[] arr = datas.split("\"");
		List<StockLt> list = new ArrayList<StockLt>();
		int i=1;
		for(String str : arr) {
			try {//去除停牌的股票
			if(i>3) break;
			if(StringUtils.isEmpty(str) ||",".equals(str)) continue;
			String[] bkArr = str.split(",");
			StockLt bk = new StockLt();
			bk.setBk(code);
			bk.setCode(bkArr[1]);
			bk.setName(bkArr[2]);
			bk.setPrice(Double.parseDouble(bkArr[3]));//最新价
			bk.setUd(Double.parseDouble(bkArr[4]));//涨跌额
			bk.setUdrate(bkArr[5]);
			bk.setCgl(Integer.parseInt(bkArr[7]));
			bk.setCge(Double.parseDouble(bkArr[8]));
			bk.setLastDay(Double.parseDouble(bkArr[9]));
			bk.setOpen(Double.parseDouble(bkArr[10]));
			bk.setHeigh(Double.parseDouble(bkArr[11]));
			bk.setLow(Double.parseDouble(bkArr[12]));
			bk.setSeq(i);
			list.add(bk);
			i++;
			}catch(Exception e) {}
		}
		stockLtRepo.save(list);
	}
	
	@Override
	public List<String> findBKCode(Date date) {
		return stockBkRepo.findCodes(date);
	}

	@Override
	public void truncateLongtou(Date date) {
		stockLtRepo.deleteByUpdateAt(date);
	}
	
	
}