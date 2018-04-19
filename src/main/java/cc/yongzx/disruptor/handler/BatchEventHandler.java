package cc.yongzx.disruptor.handler;


import cc.yongzx.disruptor.event.MyEvent;
import com.lmax.disruptor.EventHandler;

/**
 * BatchEventHandler
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class BatchEventHandler implements EventHandler<MyEvent> {

//    private static Logger logger = LogManager.getLogger(BatchEventHandler.class.getName());

    @Override
    public void onEvent(MyEvent event, long l, boolean b) throws Exception {
//        logger.info("BatchEventHandler#myEvent:" + (null == event ? null : event.toString()));
        System.out.println("BatchEventHandler#myEvent:" + (null == event ? null : event.toString()));
        if (null != event) {
            event.clear();
        }
    }

}
