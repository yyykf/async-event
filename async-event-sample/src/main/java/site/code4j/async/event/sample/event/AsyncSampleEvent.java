package site.code4j.async.event.sample.event;

import site.code4j.async.event.core.support.AsyncEvent;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/28 0:33
 */
public class AsyncSampleEvent extends SampleEvent implements AsyncEvent {

    public AsyncSampleEvent(String msg) {
        super(msg);
    }
}
