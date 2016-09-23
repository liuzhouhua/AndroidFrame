package com.lzh.administrator.androidframe.Hybrid;

import java.util.HashMap;

/**
 * Created by lzh27651 on 2016/8/18.
 * Hybrid管理类
 */

public class HybridManager {

    private volatile  static HybridManager INSTANCE;
    /**
     * 维护整个webView交互事件的集合
     */
    private HashMap<String,HybridHandler> mAllHybridHandler = new HashMap<>();

    public static HybridManager getInstance(){
        if(INSTANCE==null){
            synchronized (HybridManager.class){
                if(INSTANCE==null){
                    INSTANCE = new HybridManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 添加HybridHandler处理事件交互类
     * @param eventName
     * @param hybridHandler
     */
    public void addHybridHandler(String eventName , HybridHandler hybridHandler){
        if(eventName==null || hybridHandler==null){
            return;
        }
        if(mAllHybridHandler.containsKey(eventName)){
            return;
        }
        mAllHybridHandler.put(eventName,hybridHandler);
    }

    /**
     * 取出某个事件的交互类
     * @param eventName
     * @return
     */
    public HybridHandler getHybridHandler(String eventName){
        if(eventName==null || "".equals(eventName)){
            return null;
        }
        if(mAllHybridHandler.containsKey(eventName)){
            return mAllHybridHandler.get(eventName);
        }
        return null;
    }

}
