package com.quartz.dynamic.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean.StatefulMethodInvokingJob;

import com.quartz.dynamic.service.impl.QuartzManagerServiceImpl;

/**
 * 同步任务执行：每次执行都是new了一个新的执行类，具有线程安全性
 * @author yu_qhai
 *
 */
public class QuartzJobOne extends StatefulMethodInvokingJob {
	@Autowired QuartzManagerServiceImpl quartzManagerService;
	private boolean stop = false;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("my name is QuartzJobOne");
		System.out.println(context.getJobDetail().getJobDataMap().get("p2"));/*拿到传入的数据*/
		if(stop){//停止某个Job
			System.out.println("我只执行三次.....");
			quartzManagerService.disableSchedule("job1","job1_group");
		}
	}
}
