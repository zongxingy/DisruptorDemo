package cc.yongzx.disruptor.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @author yzx
 * @date 2018/4/18.
 */
@SuppressWarnings("serial")
public class UserInfo implements Serializable {

    /////////////////////////////////////////////////////属性///////////////////////////////////////////////////////////

    /**
     * ID
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 时间
     */
    private Date date;

    ////////////////////////////////////////////////////构造器//////////////////////////////////////////////////////////

    public UserInfo() {

    }

    ////////////////////////////////////////////////////GET SET/////////////////////////////////////////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

}
