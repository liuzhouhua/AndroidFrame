package com.lzh.administrator.androidframe.Hybrid.Imp;

import com.lzh.administrator.androidframe.Hybrid.HyBridConstants;
import com.lzh.administrator.androidframe.Hybrid.HybridHandler;
import com.lzh.administrator.androidframe.Utils.LogUtil;

/**
 * Created by lzh27651 on 2016/8/18.
 * URL处理事件接口类--测试
 */

public class URLHandlerTest implements HybridHandler {
    @Override
    public String getHandlerName() {
        return HyBridConstants.URL_TASK;
    }

    /**
     * 具体处理逻辑的地方
     * @return
     */
    @Override
    public boolean handlerTask() {
        LogUtil.d("URLHandlerTest handlerTask is done!");
        return true;
    }
}
