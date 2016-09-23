package com.lzh.administrator.androidframe.Fragment;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Hybrid.HybridChromeClient;
import com.lzh.administrator.androidframe.Hybrid.HybridJSInterface;
import com.lzh.administrator.androidframe.Hybrid.HybridWebViewClient;
import com.lzh.administrator.androidframe.Presenter.IWebViewPresenter;
import com.lzh.administrator.androidframe.Presenter.WebViewPresenterCompl;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;
import com.lzh.administrator.androidframe.Utils.ScreenUtil;
import com.lzh.administrator.androidframe.View.IWebView;

/**
 * Created by lzh27651 on 2016/9/13.
 */

public class WebViewFragment extends BaseFragment implements IWebView{

    private WebView mWebView;

    private IWebViewPresenter iWebViewPresenter;

    public static WebViewFragment newInstance(){
        return new WebViewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        iWebViewPresenter = new WebViewPresenterCompl(this);
        mWebView = (WebView) view.findViewById(R.id.webview);
        int width = ScreenUtil.getScreenWidth(mBaseActivity);
        //=======================================================================================
        /**
         * 设置缩放一些功能
         */
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.setVerticalScrollBarEnabled(false);
        //设置是否支持变焦
        mWebView.getSettings().setSupportZoom(true);
        //=======================================================================================
        /**
         * 设置WebView可触摸放大缩小
         */
        //设置缩放等级
        if(width > 650) {
            this.mWebView.setInitialScale(190);
        }else if(width > 520) {
            this.mWebView.setInitialScale(160);
        }else if(width > 450) {
            this.mWebView.setInitialScale(140);
        }else if(width > 300) {
            this.mWebView.setInitialScale(120);
        }else {
            this.mWebView.setInitialScale(100);
        }
        mWebView.setHorizontalScrollbarOverlay(true);
        //设置是否显示缩放按钮
        mWebView.getSettings().setBuiltInZoomControls(true);
        //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
        mWebView.getSettings().setUseWideViewPort(true);
        //=======================================================================================
        //提高渲染的优先级
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        //允许JS执行
        mWebView.getSettings().setJavaScriptEnabled(true);
        //把图片加载放在最后来加载渲染
        mWebView.getSettings().setBlockNetworkImage(true);
        //=======================================================================================
        //设置缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //=======================================================================================


        mWebView.setWebChromeClient(new HybridChromeClient(iWebViewPresenter));
        mWebView.setWebViewClient(new HybridWebViewClient());
        mWebView.addJavascriptInterface(new HybridJSInterface(),"hybrid_h5_call_native");
        mWebView.loadUrl("file:///android_asset/js_web.html");
    }

    @Override
    public void initData() {

    }

    @Override
    public void setTitle(String title) {
        LogUtil.d(title);
        (mBaseActivity).setTitle(title);
    }

    @Override
    public void updateProgress(int progress) {
        LogUtil.d(progress);

    }
}
