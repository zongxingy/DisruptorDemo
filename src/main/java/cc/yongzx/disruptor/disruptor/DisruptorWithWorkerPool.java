package cc.yongzx.disruptor.disruptor;

import cc.yongzx.disruptor.event.MyEvent;
import cc.yongzx.disruptor.factory.MyEventFactory;
import cc.yongzx.disruptor.handler.WorkPollEventHandler;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Disruptor#WorkerPool
 *
 * @author yzx
 * @date 2018/4/19.
 */
public class DisruptorWithWorkerPool {

    /**
     * ThreadPoolExecutor
     */
    private final ThreadPoolExecutor executor;

    /**
     * RingBuffer
     */
    private final RingBuffer<MyEvent> ringBuffer;

    /**
     * workerPool
     */
    private final WorkerPool<MyEvent> workerPool;

    /**
     * 单例
     */
    private static class SingleTonBuilder {
        private static DisruptorWithWorkerPool instance = new DisruptorWithWorkerPool();
    }

    /**
     * 工厂方法
     */
    public static DisruptorWithWorkerPool getInstance() {
        return DisruptorWithWorkerPool.SingleTonBuilder.instance;
    }


    /**
     * 私有的构造方法
     */
    private DisruptorWithWorkerPool() {
        // 线程池
        ThreadFactory threadFactory = new NamedThreadFactory("disruptor-workPool-thread");
        // workerPoolNumbers
        int workerPoolNumbers = 4;
        executor = new ThreadPoolExecutor(workerPoolNumbers,
                10, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(50),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        // RingBuffer
        // bufferSize
        int bufferSize = 1024 * 1024;
        ringBuffer = RingBuffer.create(ProducerType.SINGLE, new MyEventFactory(), bufferSize, new YieldingWaitStrategy());
        // WorkerPool
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();
        // TODO 多线程 内存泄漏
        /*WorkPollEventHandler[] workPollEventHandlers = new WorkPollEventHandler[workerPoolNumbers];
        for (int i = 0; i < workerPoolNumbers; i++) {
            workPollEventHandlers[i] = new WorkPollEventHandler();
        }
         workerPool = new WorkerPool<MyEvent>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), workPollEventHandlers);*/
        // 单线程
        workerPool = new WorkerPool<MyEvent>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), new WorkPollEventHandler());
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
    }

    //////

    /**
     * 启动
     */
    public void start() {
        workerPool.start(executor);
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
