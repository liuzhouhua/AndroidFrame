package com.lzh.administrator.androidframe.HybridModer;

import java.io.Serializable;

/**
 * Created by lzh27651 on 2016/8/18.
 * 交互实体类的基础类
 */

public class BaseHybrid implements Serializable{

    //处理事件的名字
    public String eventName;
    //处理事件的内容,此字段不使用，只是打印
    public String msg;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
