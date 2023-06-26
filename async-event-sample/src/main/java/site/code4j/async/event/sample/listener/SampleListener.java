package site.code4j.async.event.sample.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import site.code4j.async.event.sample.event.SampleEvent;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/27 0:16
 */
@Component
public class SampleListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleListener.class);

    @EventListener
    public void handleSampleEvent(SampleEvent event) {
        LOGGER.info("handle event, msg: {}", event.getMsg());
    }

}
