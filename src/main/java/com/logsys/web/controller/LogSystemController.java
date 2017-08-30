package com.logsys.web.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.authority.common.utils.DateUtil;
import com.authority.common.utils.StringUtil;
import com.authority.pojo.Criteria;
import com.authority.pojo.ExtGridReturn;
import com.authority.pojo.ExtPager;
import com.logsys.service.LogSystemService;

/**
 * 日志系统查看
 * 
 * @author yu_qhai
 * 
 */
@Controller
@RequestMapping("/logsys")
public class LogSystemController {
	@Autowired
	private LogSystemService logSystemService;

	/**
	 * index
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String logsys() {
		return "logsys/logsys";
	}

	/**
	 * 日志查询
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public Object selectLogsys(ExtPager pager, HttpServletRequest request) {
		Criteria criteria = getQueryCondition(request);
		// 设置分页信息
		if (pager.getLimit() != null && pager.getStart() != null) {
			criteria.setMysqlLength(pager.getLimit());
			criteria.setMysqlOffset(pager.getStart());
		}
		// 排序信息
		if (StringUtils.isNotBlank(pager.getDir())
				&& StringUtils.isNotBlank(pager.getSort())) {
			criteria.setOrderByClause(pager.getSort() + " " + pager.getDir());
		}else {
			criteria.setOrderByClause(" timestmp  desc ");
		}
		List<Map<String, Object>> list = this.logSystemService
				.selectSystemLog(criteria);
		int total = this.logSystemService.countSystemLog(criteria);
		return new ExtGridReturn(total, list);
	}

	/**
	 * 根据请求组装查询条件
	 * 
	 * @param request
	 */
	private Criteria getQueryCondition(HttpServletRequest request) {
		Criteria criteria = new Criteria();
		//日志查询时间
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Date start = DateUtil.strToDateTime(startDate),end = DateUtil.strToDateTime(endDate);
		criteria.put("startDate", start.getTime());
		criteria.put("endDate", end.getTime());
		
		String mapped_key = request.getParameter("mapped_key");
		String mapped_value = request.getParameter("mapped_value");
		if (StringUtil.isNotNull(mapped_key)&&StringUtil.isNotNull(mapped_value)) {//根据用户查询
			criteria.put("mapped_key", mapped_key);
			criteria.put("mapped_value", mapped_value);
		}
		
		String level_string = request.getParameter("level_string");
		if (StringUtil.isNotNull(level_string)) {//日志级别
			criteria.put("level_string", level_string);
		}
		return criteria;
	}
}
