package com.stock.schedule;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.authority.common.springmvc.SpringContextHolder;
import com.stock.pojo.StockCzb;
import com.stock.service.StockWarnTrackService;

/**
 * 财政部监控Job 同步任务执行：每次执行都是new了一个新的执行类，具有线程安全性
 * 
 * @author yu_qhai
 * 
 */
public class StockCzbJobOne extends StatefulMethodInvokingJob {
	private Logger log = LoggerFactory.getLogger(StockCzbJobOne.class);
	private StockWarnTrackService stockWarnTrackService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		init();
		Set<String> set = typeMap.keySet();
		Iterator<String> iterator = set.iterator();
		int num = 0;
		while (iterator.hasNext()) {
			// 个股某个财政部操作的个股信息
			List<StockCzb> list = stockWarnTrackService
					.getPageCzbStocks(iterator.next());
			if (list != null && list.size() > 0) {
				stockWarnTrackService.saveBatchStockCzb(list);
				num += list.size();
			}
		}
		log.info("{}个营业部更新完毕,交易了{}支股票", typeMap.size(), num);
	}

	private void init() {
		stockWarnTrackService = SpringContextHolder
				.getBean("stockWarnTrackService");
	}

	private static Map<String, String> typeMap = new Hashtable<String, String>();
	public static String $011513 = "011513", $014134 = "014134",
			$013154 = "013154", $013776 = "013776", $012108 = "012108";
	static {
		typeMap.put($011513, "中国中投无锡清扬路证券");
		typeMap.put($014134, "光大证券杭州庆春路证券");
		typeMap.put($013154, "国泰君安深圳益田路证券");
		typeMap.put($013776, "五矿证券深圳金田路证券");
		typeMap.put($012108, "国信证券深圳泰然九路证");
	}

}
