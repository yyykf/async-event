package site.code4j.async.event.sample.controller;

import javax.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.code4j.async.event.sample.event.AsyncSampleEvent;
import site.code4j.async.event.sample.event.SampleEvent;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/27 0:14
 */
@RestController
public class SampleController {

    @Resource
    private ApplicationContext ctx;

    @GetMapping("/publishEvent")
    public void publishEvent(){
        ctx.publishEvent(new SampleEvent("hello world"));
    }

    @GetMapping("/publishAsyncEvent")
    public void publishAsyncEvent(){
        ctx.publishEvent(new AsyncSampleEvent("hello world"));
    }
}
