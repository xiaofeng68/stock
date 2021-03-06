package com.kidinfor.mthread.config;  
  
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;  
  
@Configuration  
@EnableAsync  
public class TaskExecutePool {  
	@Value("${thread.corePoolSize}")
	private int corePoolSize;  
	@Value("${thread.maxPoolSize}")  
    private int maxPoolSize;  
	@Value("${thread.keepAliveSeconds}") 
    private int keepAliveSeconds;  
	@Value("${thread.queueCapacity}") 
    private int queueCapacity;  
  
    @Bean  
    public Executor myTaskAsyncPool() {  
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();  
        executor.setCorePoolSize(corePoolSize);  
        executor.setMaxPoolSize(maxPoolSize);  
        executor.setQueueCapacity(queueCapacity);  
        executor.setKeepAliveSeconds(keepAliveSeconds);  
        executor.setThreadNamePrefix("MyExecutor-");  
  
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务  
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行  
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());  
        executor.initialize();  
        return executor;  
    }  
}