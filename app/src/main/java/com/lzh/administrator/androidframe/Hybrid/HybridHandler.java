package com.lzh.administrator.androidframe.Hybrid;

/**
 * Created by lzh27651 on 2016/8/18.
 * 处理交互事件的统一接口类
 */

public interface HybridHandler {

    /**
     * 处理交互事件的对象的名称
     */
    String getHandlerName();

    /**
     * 对应的实现类处理对应的事件，返回 ：true ：处理了，false：未处理
     */
    boolean handlerTask();

}
