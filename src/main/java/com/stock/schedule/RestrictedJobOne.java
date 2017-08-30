package com.stock.schedule;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.authority.common.springmvc.SpringContextHolder;
import com.quartz.dynamic.pojo.ThreadPool;
import com.stock.runnable.RestrictedRunnable;
import com.stock.runnable.StockUtils;
import com.stock.service.StockStatisticsService;
import com.stock.service.StockWarnTrackService;

/**
 * 个股限售时间Job 同步任务执行：每次执行都是new了一个新的执行类，具有线程安全性
 * 
 * @author yu_qhai
 * 
 */
public class RestrictedJobOne extends StatefulMethodInvokingJob {
	private Logger log = LoggerFactory.getLogger(RestrictedJobOne.class);
	private List<String> codeList;// 编号集合
	private StockWarnTrackService stockWarnTrackService;
	private StockStatisticsService stockStatisticsService;

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// 获取初始化数据
		init();
		if (codeList == null || codeList.size() == 0) {
			log.info("个股编号为空请确保数据完整性");
			return;
		}
		ThreadPool threadPool = new ThreadPool(20, "RestrictedTheme"); // 创建一个有个3工作线程的线程池
		try {// 休眠500毫秒,以便让线程池中的工作线程全部运行
			Thread.sleep(500);
		} catch (InterruptedException e3) {
			e3.printStackTrace();
		}
		// 运行任务
		for (String code : codeList) {
			if (StockUtils.isStock(code))
				threadPool.execute(new RestrictedRunnable(
						stockWarnTrackService, code));
		}
		threadPool.waitFinish(); // 等待所有任务执行完毕
		threadPool.closePool(); // 关闭线程池
		log.info("共{}支股票更新基本信息完毕", codeList.size());

	}

	private void init() {
		stockWarnTrackService = SpringContextHolder
				.getBean("stockWarnTrackService");
		stockStatisticsService = SpringContextHolder
				.getBean("stockStatisticsService");
		codeList = stockStatisticsService.selectStocks();

	}

	/**
	 * 指数不做监控处理
	 * 
	 * @param code
	 * @return
	 */
	public boolean isStock(String code) {
		if ("399001".equals(code) || "399300".equals(code)
				|| "999999".equals(code) || "399006".equals(code)) {
			return false;
		}
		return true;
	}
}