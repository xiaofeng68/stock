package com.authority.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.quartz.dynamic.pojo.QuartzJob;
import com.quartz.dynamic.service.QuartzManagerService;

/**
 * 动态任务执行测试
 * 
 * @author yu_qhai
 * 
 */
@ContextConfiguration(locations = { "classpath:/config/spring/spring-*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class QuartzManagerTest {
	@Autowired
	QuartzManagerService quartzManagerService;


	@Test
	public void testQuartz() throws ClassNotFoundException {
		QuartzJob job = new QuartzJob();
		job.setCode("job1");
		job.setGcode("job1_group");
		job.setDes("我是第一个测试定时器的描述");
		job.setExp("0/5 * * * * ?");// 每五秒执行一次
		// job.setStateFulljobExecuteClass(QuartzJobOne.class);
		// job.setClsdes("com.quartz.dynamic.job.QuartzJobOne");
//		job.setClsdes("com.stock.schedule.WarnTrackJobOne");
//		job.setClsdes("com.stock.schedule.StockCzbJobOne");
//		job.setClsdes("com.stock.schedule.StockHolderJobOne");
//		job.setClsdes("com.stock.schedule.IncomingJobOne");
		job.setClsdes("com.stock.schedule.RestrictedJobOne");
		JobDataMap paramsMap = new JobDataMap();
		quartzManagerService.enableCronSchedule(job, paramsMap);
		while(true){
			try {
				Thread.sleep(100000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
