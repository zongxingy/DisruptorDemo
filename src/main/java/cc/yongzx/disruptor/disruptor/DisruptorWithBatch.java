package cc.yongzx.disruptor.disruptor;

import cc.yongzx.disruptor.event.MyEvent;
import cc.yongzx.disruptor.factory.MyEventFactory;
import cc.yongzx.disruptor.handler.BatchEventHandler;
import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Disruptor#BatchEventProcessor
 *
 * @author yzx
 * @date 2018/4/19.
 */
public class DisruptorWithBatch {

    /**
     * ThreadPoolExecutor
     */
    private final ThreadPoolExecutor executor;

    /**
     * RingBuffer
     */
    private final RingBuffer<MyEvent> ringBuffer;

    /**
     * BatchEventProcessor
     */
    private final BatchEventProcessor<MyEvent> batchEventProcessor;

    /**
     * 单例
     */
    private static class SingleTonBuilder {
        private static DisruptorWithBatch instance = new DisruptorWithBatch();
    }

    /**
     * 工厂方法
     */
    public static DisruptorWithBatch getInstance() {
        return DisruptorWithBatch.SingleTonBuilder.instance;
    }


    /**
     * 私有的构造方法
     */
    private DisruptorWithBatch() {
        // 线程池
        ThreadFactory threadFactory = new NamedThreadFactory("disruptor-batch-thread");
        // batchPoolNumbers
        int batchPoolNumbers = 4;
        executor = new ThreadPoolExecutor(batchPoolNumbers,
                10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        // RingBuffer
        // bufferSize
        int bufferSize = 1024 * 1024;
        ringBuffer = RingBuffer.create(ProducerType.SINGLE, new MyEventFactory(), bufferSize, new YieldingWaitStrategy());
        // BatchEventProcessor
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        // TODO 实际也是单线程
        batchEventProcessor = new BatchEventProcessor<MyEvent>(ringBuffer, sequenceBarrier, new BatchEventHandler());
        ringBuffer.addGatingSequences(batchEventProcessor.getSequence());
    }

    //////

    /**
     * 启动
     */
    public void start() {
        executor.execute(batchEventProcessor);
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
