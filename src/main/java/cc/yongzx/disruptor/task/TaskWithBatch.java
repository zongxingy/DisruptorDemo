package cc.yongzx.disruptor.task;

import cc.yongzx.disruptor.bean.UserInfo;
import cc.yongzx.disruptor.disruptor.DisruptorWithBatch;
import cc.yongzx.disruptor.event.MyEvent;

import java.util.Date;
import java.util.Random;

/**
 * TaskWithBatch
 *
 * @author yzx
 * @date 2018/4/18.
 */
public class TaskWithBatch implements Runnable {

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        MyEvent data = new MyEvent();
        data.setId(new Random().nextLong());
        UserInfo userInfo = new UserInfo();
        userInfo.setId(data.getId());
        userInfo.setName("用户" + userInfo.getId());
        userInfo.setDate(new Date());
        data.setUser(userInfo);
        data.setDate(new Date());
        DisruptorWithBatch.getInstance().publishEvent(data);
    }

}
