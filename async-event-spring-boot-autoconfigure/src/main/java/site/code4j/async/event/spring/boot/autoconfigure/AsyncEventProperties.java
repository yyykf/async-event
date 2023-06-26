package site.code4j.async.event.spring.boot.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/26 23:26
 */
@ConfigurationProperties(prefix = AsyncEventProperties.CODE4J_ASYNC_EVENT_PREFIX)
public class AsyncEventProperties {

    public static final String CODE4J_ASYNC_EVENT_PREFIX = "code4j.async.event";

    /** 是否开启异步事件 */
    private boolean enabled = true;
    /** 线程池配置 */
    private ThreadPoolProperties threadPool;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public ThreadPoolProperties getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolProperties threadPool) {
        this.threadPool = threadPool;
    }

    @Override
    public String toString() {
        return "AsyncProperties{" +
                "enabled=" + enabled +
                ", threadPool=" + threadPool +
                '}';
    }

    public static class ThreadPoolProperties {

        /** 核心线程数 */
        private int corePoolSize = Runtime.getRuntime().availableProcessors();
        /** 最大线程数 */
        private int maxPoolSize = Runtime.getRuntime().availableProcessors();
        /** 队列大小，-1 使用无界队列 */
        private int queueCapacity = -1;

        public int getCorePoolSize() {
            return corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }

        @Override
        public String toString() {
            return "ThreadPoolProperties{" +
                    "corePoolSize=" + corePoolSize +
                    ", maxPoolSize=" + maxPoolSize +
                    ", queueCapacity=" + queueCapacity +
                    '}';
        }
    }
}
