package com.anxpp.tinysoft.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anxpp.tinysoft.core.entity.ShareholderNumber;
import com.anxpp.tinysoft.utils.analyzer.impl.CsdnWeeklyDocumentAnalyzer;

import net.sf.json.JSONObject;

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
    	String url = "http://emweb.securities.eastmoney.com/PC_HSF10/ShareholderResearch/ShareholderResearchAjax?code=sh600999";
    	Response res = Jsoup.connect(url).timeout(50000).ignoreContentType(true).execute();
    	String body = res.body();
    	JSONObject json = JSONObject.fromObject(body);
    	List<ShareholderNumber> list = new ArrayList<>();
    	json.getJSONArray("gdrs").forEach(gdrs -> {
    		JSONObject cache = JSONObject.fromObject(gdrs);
    		ShareholderNumber number = new ShareholderNumber();
    		number.setRq(cache.getString("rq"));
//    		number.setGdrs(cache.getString("gdrs"));
    		list.add(number);
    	});
        
	}
}