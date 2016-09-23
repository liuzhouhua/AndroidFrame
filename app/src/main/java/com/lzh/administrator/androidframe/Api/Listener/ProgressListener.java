package com.lzh.administrator.androidframe.Api.Listener;

/**
 * Created by lzh27651 on 2016/9/22.
 * 网络请求进度接口类
 */

public interface ProgressListener {
    /**
     *
     * @param progress 已经上传或者下载的字节数
     * @param total 总字节数
     * @param done 是否完成
     */
    void onProgress(long progress, long total, boolean done);
}
