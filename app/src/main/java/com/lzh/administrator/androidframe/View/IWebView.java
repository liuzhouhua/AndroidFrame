package com.lzh.administrator.androidframe.View;

/**
 * Created by lzh27651 on 2016/8/19.
 * webview的界面接口
 */

public interface IWebView {
    /**
     * 设置ToolBar的Title
     * @param title
     */
    void setTitle(String title);

    /**
     * 更新进度条
     * @param progress
     */
    void updateProgress(int progress);
}
