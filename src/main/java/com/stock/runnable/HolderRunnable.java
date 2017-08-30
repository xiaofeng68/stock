package com.stock.runnable;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authority.common.db.Mysql;
import com.stock.service.StockWarnTrackService;

/**
 * 更新股东明细
 * 
 * @author yu_qhai
 * 
 */
public class HolderRunnable implements Runnable {
	private Logger log = LoggerFactory.getLogger(HolderRunnable.class);
	private StockWarnTrackService stockWarnTrackService;
	private String code;

	public HolderRunnable(StockWarnTrackService stockWarnTrackService,
			String code) {
		this.stockWarnTrackService = stockWarnTrackService;
		this.code = code;
	}

	@Override
	public void run() {
		List<String> sqls = stockWarnTrackService.updateStockHolders(code);
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
				log.error("{}更新十大流通股东失败{}", code, e);
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
