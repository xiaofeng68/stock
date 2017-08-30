package com.stock.runnable;

import com.stock.service.StockWarnTrackService;

/**
 * 更新解禁股线程
 * @author yu_qhai
 *
 */
public class RestrictedRunnable implements Runnable {
	private StockWarnTrackService stockWarnTrackService;
	private String code;
	/**
	 * 避免多次创建数据库连接造成资源浪费
	 * @param conn
	 * @param code
	 */
	public RestrictedRunnable(StockWarnTrackService stockWarnTrackService,String code){
		this.stockWarnTrackService = stockWarnTrackService;
		this.code = code;
	}

	@Override
	public void run() {
		stockWarnTrackService.updateStockRestricted(code);
	}

}
