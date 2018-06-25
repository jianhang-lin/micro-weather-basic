package work.jianhang.microweatherbasic.vo;

import java.io.Serializable;

/**
 * 返回消息对象
 *
 * @author jianhanglin
 */
public class WeatherResponse implements Serializable {

    private static final long serialVersionUID = 91647248198388507L;

    /**
     * 消息数据
     */
    private Weather data;

    /**
     * 消息状态
     */
    private int status;

    /**
     * 消息描述
     */
    private String desc;

    public Weather getData() {
        return data;
    }

    public void setData(Weather data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
