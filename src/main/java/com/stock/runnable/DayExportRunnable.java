package com.stock.runnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authority.common.db.Mysql;
import com.stock.service.StockStatisticsService;

/**
 * 导入历史除权记录信息
 * 
 * @author yu_qhai
 * 
 */
public class DayExportRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(getClass());
	private StockStatisticsService stockStatisticsService;
	private String path, code;
	private double capital;

	public DayExportRunnable(StockStatisticsService stockStatisticsService,
			String path, String code, double capital) {
		this.stockStatisticsService = stockStatisticsService;
		this.path = path;
		this.code = code;
		this.capital = capital;
	}

	@Override
	public void run() {
		List<String> sqls = stockStatisticsService.readStockExrightHis(path,
				code, capital);
		if (sqls.size() > 0) {
			Connection conn = null;
			Statement st = null;
			try {
				conn = Mysql.getConnection();
				st = conn.createStatement();
				for (String sql : sqls) {
					st.addBatch(sql);
				}
				st.executeBatch();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("{}个股统计时异常：{}", code, e);
			} finally {
				if (st != null)
					try {
						st.close();
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

}
