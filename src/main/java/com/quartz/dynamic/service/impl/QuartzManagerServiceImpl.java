package com.quartz.dynamic.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.authority.pojo.Criteria;
import com.quartz.dynamic.dao.QuartzJobMapper;
import com.quartz.dynamic.pojo.QuartzJob;
import com.quartz.dynamic.service.QuartzManagerService;

/**
 * 任务管理器
 * 
 * @author yu_qhai
 * 
 */
@Service(value="quartzManagerService")
public class QuartzManagerServiceImpl implements QuartzManagerService {
	@Autowired SchedulerFactoryBean schedulerFactory;
	@Autowired QuartzJobMapper quartzJobMapper;

	@Override
	public List<QuartzJob> selectByExample(Criteria example) {
		return this.quartzJobMapper.selectByExample(example);
	}

	@Override
	public int countByExample(Criteria example) {
		return this.quartzJobMapper.countByExample(example);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String saveFields(Criteria example) {
		QuartzJob job = (QuartzJob) example.get("quartz");

		int result = 0;
		if (StringUtils.isBlank(job.getId())) {
			result = this.quartzJobMapper.insert(job);
		} else {
			result = this.quartzJobMapper.updateByPrimaryKeySelective(job);
		}
		return result > 0 ? "01" : "00";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = { Exception.class })
	public String delete(Criteria example) {
		String id = example.getAsString("id");
		int result = 0;
		// 删除自己
		result = this.quartzJobMapper.deleteByPrimaryKey(id);
		return result > 0 ? "01" : "00";
	}
	
	/**
	 * 启动一个自定义的job
	 * 
	 * @param schedulingJob
	 *            自定义的job
	 * @param paramsMap
	 *            传递给job执行的数据
	 * @param isStateFull
	 *            是否是一个同步定时任务，true：同步，false：异步
	 * @return 成功则返回true，否则返回false
	 */
	@Override
	public boolean enableCronSchedule(QuartzJob schedulingJob,
			JobDataMap paramsMap) {
		if (schedulingJob == null) {
			return false;
		}
		try {
			CronTrigger trigger = (CronTrigger) schedulerFactory.getScheduler()
					.getTrigger(schedulingJob.getName(),
							schedulingJob.getGcode());
			if (null == trigger) {// 如果不存在该trigger则创建一个
				String clsDes = schedulingJob.getClsdes();
				Class<?> cls = Class.forName(clsDes);
				JobDetail jobDetail = null;
				jobDetail = new JobDetail(schedulingJob.getCode(),
						schedulingJob.getGcode(), cls);
				jobDetail.setJobDataMap(paramsMap);
				trigger = new CronTrigger(schedulingJob.getName(),
						schedulingJob.getGcode(), schedulingJob.getExp());
				schedulerFactory.getScheduler().scheduleJob(jobDetail, trigger);
			} else {
				// Trigger已存在，那么更新相应的定时设置
				trigger.setCronExpression(schedulingJob.getExp());
				schedulerFactory.getScheduler().rescheduleJob(
						trigger.getName(), trigger.getGroup(), trigger);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 禁用一个job
	 * 
	 * @param jobId
	 *            需要被禁用的job的ID
	 * @param jobGroupId
	 *            需要被警用的jobGroupId
	 * @return 成功则返回true，否则返回false
	 */
	@Override
	public boolean disableSchedule(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("")) {
			return false;
		}
		try {
			Trigger trigger = getJobTrigger(jobId, jobGroupId);
			if (null != trigger) {
				schedulerFactory.getScheduler().deleteJob(jobId, jobGroupId);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 得到job的详细信息
	 * 
	 * @param jobId
	 *            job的ID
	 * @param jobGroupId
	 *            job的组ID
	 * @return job的详细信息,如果job不存在则返回null
	 */
	@Override
	public JobDetail getJobDetail(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("") || null == jobId
				|| jobGroupId == null) {
			return null;
		}
		try {
			return schedulerFactory.getScheduler().getJobDetail(jobId,
					jobGroupId);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 得到job对应的Trigger
	 * 
	 * @param jobId
	 *            job的ID
	 * @param jobGroupId
	 *            job的组ID
	 * @return job的Trigger,如果Trigger不存在则返回null
	 */
	private Trigger getJobTrigger(String jobId, String jobGroupId) {
		if (jobId.equals("") || jobGroupId.equals("") || null == jobId
				|| jobGroupId == null) {
			return null;
		}
		try {
			return schedulerFactory.getScheduler().getTrigger(
					jobId + "Trigger", jobGroupId);
		} catch (SchedulerException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void updateQuartzJob(QuartzJob job) {
		this.quartzJobMapper.updateByPrimaryKeySelective(job);
	}

}
