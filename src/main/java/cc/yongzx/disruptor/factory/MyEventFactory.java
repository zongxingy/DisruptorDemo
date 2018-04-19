package cc.yongzx.disruptor.factory;

import cc.yongzx.disruptor.event.MyEvent;
import com.lmax.disruptor.EventFactory;

/**
 * EventFactory
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class MyEventFactory implements EventFactory<MyEvent> {

    @Override
    public MyEvent newInstance() {
        return new MyEvent();
    }

}
