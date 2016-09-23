package com.lzh.administrator.androidframe.Hybrid;

import android.webkit.JavascriptInterface;

/**
 * Created by lzh27651 on 2016/8/19.
 * JS调用的方法接口
 */

public class HybridJSInterface {


    /**
     * H5调用Native的统一方法
     */
    @JavascriptInterface
    public void h5CallNative(String eventName){
        HybridHandler handler = HybridHandlerFactory.createHybridHandler(eventName);
        if(handler!=null){
            handler.handlerTask();
        }
    }
}
