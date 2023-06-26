package site.code4j.async.event.sample.event;

/**
 * @Description
 * @Author yukaifan
 * @Date 2023/6/27 0:15
 */
public class SampleEvent {

    private final String msg;

    public SampleEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
