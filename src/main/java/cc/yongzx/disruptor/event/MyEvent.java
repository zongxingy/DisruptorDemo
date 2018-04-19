package cc.yongzx.disruptor.event;

import cc.yongzx.disruptor.bean.UserInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * Event
 *
 * @author yzx
 * @date 2018/4/18.
 */
@SuppressWarnings("serial")
public class MyEvent implements Serializable {

    /////////////////////////////////////////////////////属性///////////////////////////////////////////////////////////

    /**
     * ID
     */
    private Long id;

    /**
     * 用户
     */
    private UserInfo user;

    /**
     * 时间
     */
    private Date date;

    ////////////////////////////////////////////////////构造器//////////////////////////////////////////////////////////

    public MyEvent() {

    }

    ////////////////////////////////////////////////////GET SET/////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    ////////////////////////////////////////////////////toString////////////////////////////////////////////////////////

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MyEvent{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

    /////////////////////////////////////////////////////others/////////////////////////////////////////////////////////

    /**
     * 清除
     */
    public void clear() {
        this.id = null;
        this.user = null;
        this.date = null;
    }

}
