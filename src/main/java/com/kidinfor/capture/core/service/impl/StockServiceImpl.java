package com.kidinfor.capture.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kidinfor.capture.core.entity.ShareholderNumber;
import com.kidinfor.capture.core.entity.StockCode;
import com.kidinfor.capture.core.entity.StockPrice;
import com.kidinfor.capture.core.entity.TenLargestShareholder;
import com.kidinfor.capture.core.entity.TenldLargestShareholder;
import com.kidinfor.capture.core.repo.ShareholderNumberRepo;
import com.kidinfor.capture.core.repo.StockCodeRepo;
import com.kidinfor.capture.core.repo.StockPriceRepo;
import com.kidinfor.capture.core.repo.TenLargesShareholerRepo;
import com.kidinfor.capture.core.repo.TenldLargesShareholerRepo;
import com.kidinfor.capture.core.service.StockService;
import com.kidinfor.capture.utils.StockUtil;

import net.sf.json.JSONObject;

@Service
public class StockServiceImpl implements StockService {
    @Value("${stock.sdgd}")
    private String sdgd;
    @Value("${stock.codes}")
    private String codes;
    @Value("${stock.price}")
    private String price;
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
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updateHolders(String code) throws Exception {
		Response res = Jsoup.connect(sdgd+code).timeout(50000).ignoreContentType(true).execute();
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
    	document.body().getElementsByClass("quotebody").get(0).getElementsByTag("li").forEach(li ->{
    		StockCode stock = new StockCode();
    		stock.setCode(StockUtil.getCode(li.getElementsByTag("a").get(0).attr("href")));
    		String text = li.text();
    		int index = text.indexOf("(");
    		stock.setName(text.substring(0,index));
    		stock.setScode(text.substring(index+1,index+7));
    		codeList.add(stock);
    	});
    	stockCodeRepo.save(codeList);
	}
	@Override
	@Transactional
	@Async("myTaskAsyncPool")
	public void updatePrice(String code) throws Exception {
		Response res = Jsoup.connect(price+code).timeout(50000).ignoreContentType(true).execute();
    	String body = res.body();
    	int start = body.indexOf("=")+1;
    	int end = body.indexOf(";")-1;
    	String [] datas = body.substring(start, end).split(",");
    	if(datas.length==0) return;
    	StockPrice price = new StockPrice();
    	price.setCode(code);
    	price.setJk(Double.parseDouble(datas[1]));//今日开盘价
    	price.setZs(Double.parseDouble(datas[2]));//昨日收盘价
    	price.setPrice(Double.parseDouble(datas[3]));//当前价格
    	price.setZg(Double.parseDouble(datas[4]));//今日最高价
    	price.setZd(Double.parseDouble(datas[5]));//今日最低价
    	price.setCjl(Long.parseLong(datas[8]));
    	price.setCje(Double.parseDouble(datas[9]));
    	price.setTime(datas[30]+" "+datas[31]);
    	stockPriceRepo.save(price);
	}
	@Override
	public List<StockCode> getCodes(){
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
    
}