package com.quartz.dynamic.service;

import java.util.List;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import com.authority.pojo.Criteria;
import com.quartz.dynamic.pojo.QuartzJob;

/**
 * 动态任务管理
 * @author yu_qhai
 *
 */
public interface QuartzManagerService {
	/**
	 * 根据条件查询记录集
	 */
	List<QuartzJob> selectByExample(Criteria example);
	/**
	 * 根据条件查询记录总数
	 */
	int countByExample(Criteria example);
	/**
	 * 保存系统字段设置
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String saveFields(Criteria example);
	/**
	 * 删除系统字段设置
	 * 
	 * @param example
	 * @return 00：失败，01：成功 ,其他情况
	 */
	String delete(Criteria example);
	/**
	 * 启动一个自定义的job
	 * 
	 * @param schedulingJob
	 *            自定义的job
	 * @param paramsMap
	 *            传递给job执行的数据
	 * @return 成功则返回true，否则返回false
	 */
	public boolean enableCronSchedule(QuartzJob schedulingJob,
			JobDataMap paramsMap);

	/**
	 * 禁用一个job
	 * 
	 * @param jobId
	 *            需要被禁用的job的ID
	 * @param jobGroupId
	 *            需要被警用的jobGroupId
	 * @return 成功则返回true，否则返回false
	 */
	public boolean disableSchedule(String jobId, String jobGroupId);
	/**
	 * 得到job的详细信息
	 * 
	 * @param jobId
	 *            job的ID
	 * @param jobGroupId
	 *            job的组ID
	 * @return job的详细信息,如果job不存在则返回null
	 */
	public JobDetail getJobDetail(String jobId, String jobGroupId);
	/**
	 * 更新job
	 * @param job
	 */
	public void updateQuartzJob(QuartzJob job);
}
