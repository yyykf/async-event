package site.code4j.async.event.spring.boot.autoconfigure;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import site.code4j.async.event.spring.boot.autoconfigure.AsyncEventProperties.ThreadPoolProperties;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/26 23:38
 */
@Configuration
@ConditionalOnProperty(prefix = AsyncEventProperties.CODE4J_ASYNC_EVENT_PREFIX, name = "enabled", havingValue = "true")
public class AsyncEventAutoConfiguration {

    private final AsyncEventProperties properties;


    public AsyncEventAutoConfiguration(AsyncEventProperties properties) {
        this.properties = properties;
    }

    @Bean(name = AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
    public ApplicationEventMulticaster applicationEventMulticaster() {
        // todo 需要重写 Multicaster，如果事件实现了异步接口，那么就异步调用监听器，否则按原有逻辑，这样可以避免系统中所有的事件都被异步处理，无法兼容以前的逻辑
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        // 指定线程池，可以手动创建 ThreadPoolExecutor
        ThreadPoolProperties threadPoolProperties = properties.getThreadPool();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(threadPoolProperties.getCorePoolSize(),
                threadPoolProperties.getMaxPoolSize(),
                0L,
                TimeUnit.MILLISECONDS,
                threadPoolProperties.getQueueCapacity() == -1 ? new LinkedBlockingQueue<>()
                        : new ArrayBlockingQueue<>(threadPoolProperties.getQueueCapacity()),
                new ThreadFactoryBuilder().setNameFormat("async-event-pool-%d").build());

        eventMulticaster.setTaskExecutor(executor);
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }
}
