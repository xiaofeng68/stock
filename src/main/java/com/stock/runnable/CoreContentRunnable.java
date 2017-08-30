package com.stock.runnable;

import com.stock.service.StockWarnTrackService;

/**
 * 更新核心题材或曾用名线程
 * 
 * @author yu_qhai
 * 
 */
public class CoreContentRunnable implements Runnable {
	private StockWarnTrackService stockWarnTrackService;
	private String code;

	/**
	 * 避免多次创建数据库连接造成资源浪费
	 * 
	 * @param conn
	 * @param code
	 * @param column
	 */
	public CoreContentRunnable(StockWarnTrackService stockWarnTrackService,
			String code) {
		this.stockWarnTrackService = stockWarnTrackService;
		this.code = code;
	}

	@Override
	public void run() {
		stockWarnTrackService.updateStockCoreContent(code);
	}

}
