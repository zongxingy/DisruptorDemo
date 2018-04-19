package cc.yongzx.disruptor.handler;

import cc.yongzx.disruptor.event.MyEvent;
import com.lmax.disruptor.EventHandler;

/**
 * ClearingEventHandler
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class ClearingEventHandler implements EventHandler<MyEvent> {

    @Override
    public void onEvent(MyEvent event, long l, boolean b) throws Exception {
        if (null != event) {
            event.clear();
        }
    }

}
