/**
 * @Placed com.exterro.mobile.config
 */
package com.exterro.mobile.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author mrangasamy
 *
 * @date 14-Aug-2024
 */
@Configuration
@EnableAsync
public class AsyncConfig {
	@Bean("asyncTask")
	public Executor asyncTaskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setThreadNamePrefix("AsyncTask-");
		taskExecutor.setCorePoolSize(4);
		taskExecutor.setQueueCapacity(150);
		taskExecutor.setMaxPoolSize(4);
		taskExecutor.initialize();
		return taskExecutor;
	}
}
