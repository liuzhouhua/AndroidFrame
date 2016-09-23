package com.lzh.administrator.androidframe.Hybrid;

import android.graphics.Bitmap;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lzh27651 on 2016/8/18.
 * WebViewClient主要帮助WebView处理各种通知、请求事件的
 */

public class HybridWebViewClient extends WebViewClient{


    /**
     * 网页开始加载
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    /**
     * 网页加载完毕
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    /**
     * 控制新的连接在当前WebView中打开
     * @param view
     * @param request
     * @return
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        // 更具对应的url 做相应的事件
        return true;
    }

}
