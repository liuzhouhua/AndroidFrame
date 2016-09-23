package com.lzh.administrator.androidframe.Presenter;

import com.lzh.administrator.androidframe.View.IWebView;

/**
 * Created by lzh27651 on 2016/8/19.
 * WebView的业务接口实现类
 */

public class WebViewPresenterCompl implements IWebViewPresenter{

    private IWebView iWebView;

    public WebViewPresenterCompl(IWebView iWebView) {
        this.iWebView = iWebView;
    }

    @Override
    public void setTitle(String title) {
        iWebView.setTitle(title);
    }

    @Override
    public void updateProgressChanged(int progress) {
        iWebView.updateProgress(progress);
    }
}
