package zerobase.stock.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler(); // 스레드 풀 생성

        int n = Runtime.getRuntime().availableProcessors(); // 코어 개수
        threadPool.setPoolSize(n); // 사이즈 넣기
        threadPool.initialize();

        taskRegistrar.setTaskScheduler(threadPool); // 스케쥴러에서 스레드 풀 사용 가능할 수 있게 만듦
    }
}
