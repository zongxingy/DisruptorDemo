package cc.yongzx.disruptor.handler;


import cc.yongzx.disruptor.event.MyEvent;
import com.lmax.disruptor.EventHandler;

/**
 * EventHandler
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class MyEventHandler implements EventHandler<MyEvent> {

//    private static Logger logger = LogManager.getLogger(MyEventHandler.class.getName());

    @Override
    public void onEvent(MyEvent event, long l, boolean b) throws Exception {
//        logger.info("MyEventHandler#myEvent:" + (null == myEvent ? null : myEvent.toString()));
        System.out.println("MyEventHandler#myEvent:" + (null == event ? null : event.toString()));
    }

}
