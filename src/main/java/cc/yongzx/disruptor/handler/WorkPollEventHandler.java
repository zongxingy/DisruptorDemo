package cc.yongzx.disruptor.handler;


import cc.yongzx.disruptor.event.MyEvent;
import com.lmax.disruptor.WorkHandler;

/**
 * WorkPollEventHandler
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class WorkPollEventHandler implements WorkHandler<MyEvent> {

//    private static Logger logger = LogManager.getLogger(WorkPollEventHandler.class.getName());

    @Override
    public void onEvent(MyEvent event) throws Exception {
//        logger.info("WorkPollEventHandler#myEvent:" + (null == event ? null : event.toString()));
        System.out.println("WorkPollEventHandler#myEvent:" + (null == event ? null : event.toString()));
        if (null != event) {
            event.clear();
        }
    }
}
