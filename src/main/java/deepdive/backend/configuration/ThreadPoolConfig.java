package deepdive.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfig {

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setMaxPoolSize(5);
		threadPoolTaskExecutor.setCorePoolSize(5);
		threadPoolTaskExecutor.initialize();

		threadPoolTaskExecutor.setThreadNamePrefix("async-task-");
		threadPoolTaskExecutor.setThreadGroupName("async-group");

		return threadPoolTaskExecutor;
	}
}
