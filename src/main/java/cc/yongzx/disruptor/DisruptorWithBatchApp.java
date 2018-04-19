package cc.yongzx.disruptor;

import cc.yongzx.disruptor.disruptor.DisruptorWithBatch;
import cc.yongzx.disruptor.disruptor.DisruptorWithWorkerPool;
import cc.yongzx.disruptor.task.TaskWithBatch;
import cc.yongzx.disruptor.task.TaskWithWorkerPool;
import org.apache.lucene.util.NamedThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 * @author yzx
 */
public class DisruptorWithBatchApp {

    /**
     * Main
     *
     * @param args args
     * @throws InterruptedException InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        DisruptorWithBatch.getInstance().start();
        // 线程工厂
        ThreadFactory threadFactory = new NamedThreadFactory("scheduler-batch-thread");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3000,
                5000, 5, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(5000),
                threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
        Integer total = 3000;
        while (true) {
            for (int i = 0; i < total; i++) {
                executor.execute(new TaskWithBatch());
            }
            Thread.sleep(1000L);
        }

    }

}
