package com.logsys.dao;

import java.util.List;
import java.util.Map;

import com.authority.pojo.Criteria;


/**
 * 日志系统查看
 * @author yu_qhai
 *
 */
public interface LogSystemMapper {
	/**
	 * 日志查询
	 * @param criteria
	 * @return
	 */
	List<Map<String,Object>> selectSystemLog(Criteria criteria);
	/**
	 * 日志统计数量
	 * @param criteria
	 * @return
	 */
	int countSystemLog(Criteria criteria);
}