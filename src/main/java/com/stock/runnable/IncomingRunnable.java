package com.stock.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.stock.service.StockWarnTrackService;

/**
 * 个股收益处理线程
 * @author yu_qhai
 *
 */
public class IncomingRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(IncomingRunnable.class);
	private StockWarnTrackService stockWarnTrackService;
	private String code;
	private int year;
	public IncomingRunnable(StockWarnTrackService stockWarnTrackService,String code,int year){
		this.stockWarnTrackService = stockWarnTrackService;
		this.code = code;
		this.year = year;
	}
	@Override
	public void run() {
		try{
			stockWarnTrackService.updateIncoming(code,year);
		}catch(Exception e){
			log.error("{}更新个股收益时异常{}",code,e);
		}
	}

}
