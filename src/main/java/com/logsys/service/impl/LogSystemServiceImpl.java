package com.logsys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authority.pojo.Criteria;
import com.logsys.dao.LogSystemMapper;
import com.logsys.service.LogSystemService;
@Service
public class LogSystemServiceImpl implements LogSystemService {
	@Autowired
	LogSystemMapper logSystemMapper;
	@Override
	public List<Map<String, Object>> selectSystemLog(Criteria criteria) {
		return logSystemMapper.selectSystemLog(criteria);
	}

	@Override
	public int countSystemLog(Criteria criteria) {
		return logSystemMapper.countSystemLog(criteria);
	}

}
