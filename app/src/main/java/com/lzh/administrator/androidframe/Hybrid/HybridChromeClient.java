package com.lzh.administrator.androidframe.Hybrid;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.lzh.administrator.androidframe.Presenter.IWebViewPresenter;
import com.lzh.administrator.androidframe.Utils.LogUtil;

/**
 * Created by lzh27651 on 2016/8/19.
 * 辅助WebView处理Javascript的对话框、网站图标、网站title、加载进度
 */

public class HybridChromeClient extends WebChromeClient{

    private IWebViewPresenter iWebViewPresenter;

    public HybridChromeClient(IWebViewPresenter iWebViewPresenter) {
        this.iWebViewPresenter = iWebViewPresenter;
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        LogUtil.d("HybridChromeClient title :"+title);
        iWebViewPresenter.setTitle(title);
    }

    @Override
    public void onProgressChanged(final WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        iWebViewPresenter.updateProgressChanged(newProgress);
        if(newProgress==100){
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.loadUrl("javascript:callJS()");
                }
            });
        }
    }
}
