package com.kidinfor.quartz.core.config;  
  
import javax.annotation.Resource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kidinfor.quartz.core.repo.ConfigRepo;

  
@Configuration  
@EnableScheduling  
@Component  
public class ScheduleRefreshDatabase {  
    @Autowired  
    private ConfigRepo repository;  
  
    @Resource(name = "jobDetail")  
    private JobDetail jobDetail;  
  
    @Resource(name = "jobTrigger")  
    private CronTrigger cronTrigger;  
  
    @Resource(name = "scheduler")  
    private Scheduler scheduler;  
  
    @Scheduled(fixedRate = 300000) // 每隔5分钟查库，并根据查询结果决定是否重新设置定时任务  
    public void scheduleUpdateCronTrigger() throws SchedulerException {  
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
        String currentCron = trigger.getCronExpression();// 当前Trigger使用的  
        String searchCron = repository.findOne(1L).getCron();// 从数据库查询出来的  
        if (currentCron.equals(searchCron)) {  
            // 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务  
        } else {  
            // 表达式调度构建器  
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);  
            // 按新的cronExpression表达式重新构建trigger  
            trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());  
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey()).withSchedule(scheduleBuilder).build();  
            // 按新的trigger重新设置job执行  
            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);  
            currentCron = searchCron;  
        }  
    }  
}  