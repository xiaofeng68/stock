package com.kidinfor.quartz.core.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.kidinfor.capture.core.service.StockService;

@Configuration
@Component
@EnableScheduling
public class ScheduleTask {
	@Resource
	private StockService stockService;

	public void run() {
		try {
			Date now = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(now);
			/**周六周天不执行*/
			if(cal.get(Calendar.DAY_OF_MONTH)==Calendar.SATURDAY||cal.get(Calendar.DAY_OF_MONTH)==Calendar.SUNDAY) return;
			/**9:35  至 11:40    13:05至15:40 才更新*/
			if(betweenTime(now, cal)) return;
			
			stockService.updateGainian();
			stockService.truncateLongtou(now);
			List<String> list = stockService.findBKCode(now);
			for (String code : list) {
				stockService.updateLongtou(code);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean betweenTime(Date now, Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 9);
		cal.set(Calendar.MILLISECOND, 35);
		cal.set(Calendar.SECOND, 0);
		Date start = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 11);
		cal.set(Calendar.MILLISECOND, 40);
		Date end = cal.getTime();
		if(now.before(start) || now.after(end)) return true;
		cal.set(Calendar.HOUR_OF_DAY, 13);
		cal.set(Calendar.MILLISECOND, 05);
		cal.set(Calendar.SECOND, 0);
		start = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 15);
		cal.set(Calendar.MILLISECOND, 40);
		end = cal.getTime();
		if(now.before(start) || now.after(end)) return true;
		return false;
	}
}