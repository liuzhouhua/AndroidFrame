package com.lzh.administrator.androidframe.Hybrid;

/**
 * Created by lzh27651 on 2016/8/18.
 * HybridHandler的工厂类，管理制造不同的Hybrid对象
 */

public class HybridHandlerFactory {

    /**
     *  创建HybridHandler对象
     * @param eventName
     * @return
     */
    public static HybridHandler createHybridHandler(String eventName){
        HybridManager.getInstance().addHybridHandler(eventName,HybridClassLoader.getHybridHandler(eventName));
        return HybridManager.getInstance().getHybridHandler(eventName);
    }
}
