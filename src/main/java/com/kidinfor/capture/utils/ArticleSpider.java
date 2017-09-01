package com.kidinfor.capture.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kidinfor.capture.core.entity.StockCode;
import com.kidinfor.capture.utils.analyzer.impl.CsdnWeeklyDocumentAnalyzer;

/**
 * 文章抓取工具
 * Created by anxpp.com on 2017/3/11.
 */
public class ArticleSpider {

    private static final Logger log = LoggerFactory.getLogger(ArticleSpider.class);

    public static <T> List<T> forEntityList(String url, CsdnWeeklyDocumentAnalyzer docAnalyzer, Class<T> type) throws Exception {

        log.info("开始抓取文章："+url);

        List<T> results = new ArrayList<>();
        docAnalyzer.forListMap(Jsoup.connect(url).timeout(50000).get()).forEach(map->{
            try {
                results.add(TinyUtil.mapToBean(map, type));
            } catch (Exception ignored) {}
         });
        return results;
    }
    
    @SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
    	String url = "http://quote.eastmoney.com/stocklist.html";
    	Document document = Jsoup.connect(url).timeout(50000).get();
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
    	System.out.println(codeList.size());
    	
    	
    	
    	
//    	十大股东抓取
//    	String url = "http://emweb.securities.eastmoney.com/PC_HSF10/ShareholderResearch/ShareholderResearchAjax?code=sh600999";
//    	String code = "sh600999";
//    	Response res = Jsoup.connect(url).timeout(50000).ignoreContentType(true).execute();
//    	String body = res.body();
//    	JSONObject json = JSONObject.fromObject(body);
//    	List<ShareholderNumber> list = new ArrayList<>();
//    	json.getJSONArray("gdrs").forEach(gdrs -> {
//    		JSONObject cache = JSONObject.fromObject(gdrs);
//    		ShareholderNumber number = new ShareholderNumber();
//    		number.setCode(code);
//    		number.setRq(cache.getString("rq"));
//    		number.setGdrs(StockUtil.string2Double(cache.getString("gdrs")));
//    		number.setGdrs_jsqbh(StockUtil.string2Double(cache.getString("gdrs_jsqbh")));
//    		number.setRjltg(StockUtil.string2Double(cache.getString("rjltg")));
//    		number.setRjltg_jsqbh(StockUtil.string2Double(cache.getString("rjltg_jsqbh")));
//    		number.setCmjzd(cache.getString("cmjzd"));
//    		number.setGj(StockUtil.string2Double(cache.getString("gj")));
//    		number.setRjcgje(StockUtil.string2Double(cache.getString("rjcgje")));
//    		number.setQsdgdcghj(StockUtil.string2Double(cache.getString("qsdgdcghj")));
//    		number.setQsdltgdcghj(StockUtil.string2Double(cache.getString("qsdltgdcghj")));
//    		list.add(number);
//    	});
//    	List<TenLargestShareholder> tenList = new ArrayList<>();
//    	json.getJSONArray("sdgd").forEach(sdgd -> {
//    		JSONObject cache = JSONObject.fromObject(sdgd);
//    		cache.getJSONArray("sdgd").forEach(csdgd -> {
//    			JSONObject ccache = JSONObject.fromObject(csdgd);
//    			TenLargestShareholder ten = new TenLargestShareholder();
//    			ten.setCode(code);
//    			ten.setRq(ccache.getString("rq"));
//    			ten.setMc(ccache.getInt("mc"));
//    			ten.setGdmc(ccache.getString("gdmc"));
//    			ten.setGflx(ccache.getString("gflx"));
//    			ten.setCgs(StockUtil.string2Long(ccache.getString("cgs")));
//    			ten.setZltgbcgbl(StockUtil.string2Double(ccache.getString("zltgbcgbl")));
//    			ten.setZj(ccache.getString("zj"));
//    			ten.setBdbl(StockUtil.string2Double(ccache.getString("bdbl")));
//    			tenList.add(ten);
//    		});;
//    	});
//    	List<TenLargestShareholder> tenltList = new ArrayList<>();
//    	json.getJSONArray("sdltgd").forEach(sdgd -> {
//    		JSONObject cache = JSONObject.fromObject(sdgd);
//    		cache.getJSONArray("sdltgd").forEach(csdgd -> {
//    			JSONObject ccache = JSONObject.fromObject(csdgd);
//    			TenLargestShareholder ten = new TenLargestShareholder();
//    			ten.setCode(code);
//    			ten.setRq(ccache.getString("rq"));
//    			ten.setMc(ccache.getInt("mc"));
//    			ten.setGdmc(ccache.getString("gdmc"));
//    			ten.setGflx(ccache.getString("gflx"));
//    			ten.setCgs(StockUtil.string2Long(ccache.getString("cgs")));
//    			ten.setZltgbcgbl(StockUtil.string2Double(ccache.getString("zltgbcgbl")));
//    			ten.setZj(ccache.getString("zj"));
//    			ten.setBdbl(StockUtil.string2Double(ccache.getString("bdbl")));
//    			tenltList.add(ten);
//    		});;
//    	});
//    	System.out.println(tenltList);
	}
}