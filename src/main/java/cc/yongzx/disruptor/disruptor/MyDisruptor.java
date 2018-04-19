package cc.yongzx.disruptor.disruptor;

import cc.yongzx.disruptor.event.MyEvent;
import cc.yongzx.disruptor.factory.MyEventFactory;
import cc.yongzx.disruptor.handler.ClearingEventHandler;
import cc.yongzx.disruptor.handler.MyEventHandler;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * Disruptor
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class MyDisruptor {

    /**
     * Disruptor
     */
    public Disruptor<MyEvent> disruptor;

    /**
     * RingBuffer
     */
    private final RingBuffer<MyEvent> ringBuffer;

    /**
     * 单例
     */
    private static class SingleTonBuilder {
        private static MyDisruptor instance = new MyDisruptor();
    }

    /**
     * 工厂方法
     */
    public static MyDisruptor getInstance() {
        return MyDisruptor.SingleTonBuilder.instance;
    }


    /**
     * 私有的构造方法
     */
    private MyDisruptor() {
        // 生产者的线程工厂
        ThreadFactory threadFactory = new NamedThreadFactory("disruptor-thread");
        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();
        // 指定RingBuffer的大小
        int bufferSize = 1024 * 1024;
        // 创建disruptor，采用单生产者模式
        disruptor = new Disruptor<MyEvent>(new MyEventFactory(), bufferSize, threadFactory, ProducerType.SINGLE, strategy);
        // 设置EventHandler
        disruptor.handleEventsWith(new MyEventHandler()).then(new ClearingEventHandler());
        // RingBuffer
        ringBuffer = disruptor.getRingBuffer();
    }

    //////

    /**
     * 启动
     */
    public void start() {
        // 启动disruptor的线程
        disruptor.start();
    }

    /**
     * 发布事件
     *
     * @param data 数据
     */
    public void publishEvent(MyEvent data) {
        // Grab the next sequence
        long sequence = ringBuffer.next();
        try {
            // Get the entry in the Disruptor
            MyEvent event = ringBuffer.get(sequence);
            // for the sequence
            // Fill with data
            event.setId(data.getId());
            event.setDate(data.getDate());
            event.setUser(data.getUser());
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
