package site.code4j.async.event.spring.boot.autoconfigure;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import site.code4j.async.event.core.support.AsyncApplicationEventMulticaster;
import site.code4j.async.event.spring.boot.autoconfigure.AsyncEventProperties.ThreadPoolProperties;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/26 23:38
 */
@Configuration
@EnableConfigurationProperties(AsyncEventProperties.class)
public class AsyncEventAutoConfiguration {

    private final AsyncEventProperties properties;

    public AsyncEventAutoConfiguration(AsyncEventProperties properties) {
        this.properties = properties;
    }

    @ConditionalOnProperty(prefix = AsyncEventProperties.CODE4J_ASYNC_EVENT_PREFIX, name = "enabled", havingValue = "true")
    @Bean(name = AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public ApplicationEventMulticaster applicationEventMulticaster() {
        AsyncApplicationEventMulticaster eventMulticaster = new AsyncApplicationEventMulticaster();
        // 指定线程池，可以手动创建 ThreadPoolExecutor
        ThreadPoolProperties threadPoolProperties = properties.getThreadPool();

        // 如果是无界队列的话，那么使用默认拒绝策略（反正永远不会触发），否则使用 CallerRuns 策略，避免线程池满了而出现事件无法处理
        boolean infinityTaskQueue = threadPoolProperties.getQueueCapacity() == -1;
        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                threadPoolProperties.getMaxPoolSize(),
                0L,
                TimeUnit.MILLISECONDS,
                infinityTaskQueue ? new LinkedBlockingQueue<>() : new ArrayBlockingQueue<>(threadPoolProperties.getQueueCapacity()),
                new ThreadFactoryBuilder().setNameFormat("async-event-pool-%d").build(),
                infinityTaskQueue ? new ThreadPoolExecutor.AbortPolicy() : new CallerRunsPolicy());

        eventMulticaster.setTaskExecutor(executor);
        return eventMulticaster;
    }
}