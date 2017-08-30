package com.authority.web.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.authority.common.jackjson.JackJson;
import com.authority.common.springmvc.SpringContextHolder;
import com.authority.pojo.Criteria;
import com.authority.service.BaseFieldsService;
import com.quartz.dynamic.pojo.QuartzJob;
import com.quartz.dynamic.service.QuartzManagerService;

/**
 * 系统初始化监听器
 * 
 * @author Administrator
 * @date 2011-12-16 下午11:26:14
 */
public class SystemInitListener implements ServletContextListener {
	private Logger log = LoggerFactory.getLogger(SystemInitListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info("=====================系统内存初始化监听启动==========================");
		// 把字典放入缓存
		ServletContext servletContext = sce.getServletContext();
		BaseFieldsService baseFieldsService = SpringContextHolder
				.getBean("baseFieldsServiceImpl");
		Criteria criteria = new Criteria();
		criteria.setOrderByClause(" field desc ,sort asc ");
		criteria.put("enabled", "1");
		Map<String, String> fieldsMap = baseFieldsService
				.selectAllByExample(criteria);
		servletContext.setAttribute("fields", fieldsMap);
		Map<String, String> stockMap = getStockDescriptionMap(fieldsMap);
		servletContext.setAttribute("stocks",
				JackJson.fromObjectToJson(stockMap));
		log.info("=====================系统内存初始化监听启动完毕==========================");
		log.info("=====================任务定时启动==========================");
		QuartzManagerService quartzManagerService = SpringContextHolder
				.getBean("quartzManagerService");
		criteria = new Criteria();
		criteria.put("job_state",1);
		List<QuartzJob> list = quartzManagerService.selectByExample(criteria);
		for(QuartzJob job : list){
			quartzManagerService.enableCronSchedule(job,null);
		}
		log.info("=====================任务定时启动完毕==========================");
	}

	/**
	 * 合并个股字典缓存
	 * @param fieldsMap
	 * @return
	 */
	private Map<String, String> getStockDescriptionMap(
			Map<String, String> fieldsMap) {
		Map<String, String> stockMap = new HashMap<String, String>();
		Map<String, String> busTypeMap = JackJson.fromJsonToObject(
				fieldsMap.get("bus_type"),
				new TypeReference<Map<String, String>>() {
				});
		if (busTypeMap != null) {
			stockMap.putAll(busTypeMap);
		}
		Map<String, String> formTypeMap = JackJson.fromJsonToObject(
				fieldsMap.get("form_type"),
				new TypeReference<Map<String, String>>() {
				});
		if (formTypeMap != null) {
			stockMap.putAll(formTypeMap);
		}
		Map<String, String> sosmeTypeMap = JackJson.fromJsonToObject(
				fieldsMap.get("sosme_type"),
				new TypeReference<Map<String, String>>() {
				});
		if (sosmeTypeMap != null) {
			stockMap.putAll(sosmeTypeMap);
		}
		Map<String, String> newTypeMap = JackJson.fromJsonToObject(
				fieldsMap.get("new_type"),
				new TypeReference<Map<String, String>>() {
				});
		if (newTypeMap != null) {
			stockMap.putAll(newTypeMap);
		}
		Map<String, String> lcstockTypeMap = JackJson.fromJsonToObject(
				fieldsMap.get("lcstock_type"),
				new TypeReference<Map<String, String>>() {
				});
		if (lcstockTypeMap != null) {
			stockMap.putAll(lcstockTypeMap);
		}
		return stockMap;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}
