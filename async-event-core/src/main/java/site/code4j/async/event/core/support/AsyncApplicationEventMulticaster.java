package site.code4j.async.event.core.support;

import java.util.concurrent.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

/**
 * @Description 异步事件广播器
 * @Author yukaifan
 * @Date 2023/6/27 23:58
 * @see AsyncEvent
 */
public class AsyncApplicationEventMulticaster extends SimpleApplicationEventMulticaster {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsyncApplicationEventMulticaster.class);

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        ResolvableType type = (eventType != null ? eventType : ResolvableType.forInstance(event));

        // 是否异步处理
        boolean needAsync = false;
        // todo 需要研究一下是否存在特殊场景
        if (event instanceof PayloadApplicationEvent) {
            needAsync = ((PayloadApplicationEvent<?>) event).getPayload() instanceof AsyncEvent;
        }
        LOGGER.info("Current event type: {}, need async: {}", type, needAsync);

        Executor executor = getTaskExecutor();
        for (ApplicationListener<?> listener : getApplicationListeners(event, type)) {
            if (executor != null && needAsync) {
                executor.execute(() -> invokeListener(listener, event));
            } else {
                // 没有实现AsyncEvent的事件默认同步进行处理
                invokeListener(listener, event);
            }
        }
    }
}
