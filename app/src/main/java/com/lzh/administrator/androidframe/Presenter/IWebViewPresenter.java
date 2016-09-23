package com.lzh.administrator.androidframe.Presenter;

/**
 * Created by lzh27651 on 2016/8/19.
 * webview的业务接口
 */

public interface IWebViewPresenter {

    /**
     * 设置标题
     * @param title
     */
    void setTitle(String title);

    /**
     * 更新加载进度
     * @param progress
     */
    void updateProgressChanged(int progress);
}
