package com.stock.runnable;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.authority.common.db.Mysql;
import com.quartz.dynamic.pojo.ThreadPool;
import com.stock.service.StockStatisticsService;
import com.stock.util.DeCompressUtil;

/**
 * 个股数据统计更新
 * 
 * @author yu_qhai
 * 
 */
public class UpdateDataRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(getClass());
	private Map<String, Object> map;// 存储是否更新完毕
	private StockStatisticsService stockStatisticsService;
	private ThreadPoolTaskExecutor stockTaskExecutor;
	private List<String> codeList;
	private Map<String, Map<String, Object>> capitalMap;

	public UpdateDataRunnable(StockStatisticsService stockStatisticsService,
			Map<String, Object> map) {
		this.stockStatisticsService = stockStatisticsService;
		this.map = map;
		codeList = stockStatisticsService.selectStocks();
		capitalMap = stockStatisticsService.getStocksCapital();
	}

	@Override
	public void run() {
		String path = (String) map.get("path");
		// 如果文件存在判断旗下是否存在rar文件存在解压，解压完毕后删除文件
		File file = new File(path + File.separator + "stock");
		if (file != null) {
			File[] childList = file.listFiles();
			String fileName;
			for (File f : childList) {
				fileName = f.getName();
				if (fileName.toLowerCase().indexOf(".rar") != -1
						|| fileName.indexOf(".zip") != -1)
					try {
						DeCompressUtil.deCompress(f.getPath(), file.getPath()
								+ File.separator + "T0002"+ File.separator );
					} catch (Exception e) {
						e.printStackTrace();
						log.error("文件解压失败：{}", e);
					}
				f.delete();
			}
		}
		truncateCache();
		try {
			ThreadPool threadPool = new ThreadPool(20, "StatisticsTheme"); // 创建一个有个3工作线程的线程池
			try {// 休眠500毫秒,以便让线程池中的工作线程全部运行
				Thread.sleep(500);
			} catch (InterruptedException e3) {
				e3.printStackTrace();
			}
			// 运行任务
			Map<String, Object> cacheMap;
			double capital;
			for (String code : codeList) {
				cacheMap = capitalMap.get(code);
				try {
					capital = (Double) cacheMap.get("capital");
				} catch (Exception e) {
					capital = 0;
				}
				threadPool.execute(new DayExportRunnable(
						stockStatisticsService, file.getPath(), code, capital));
			}
			threadPool.waitFinish(); // 等待所有任务执行完毕
			threadPool.closePool(); // 关闭线程池
			this.map.put("updated", true);
			log.info("{}个个股数据更新完毕", codeList.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * 清空历史表
	 * 
	 * @param conn
	 */
	public void truncateCache() {
		Connection conn = null;
		Statement ps = null;

		try {
			conn = Mysql.getConnection();
			ps = conn.createStatement();
			ps.addBatch("truncate stock_cache");
			ps.addBatch("truncate stock_statistics_month");
			ps.addBatch("truncate stock_vol");
			ps.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (ps != null)
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
}
