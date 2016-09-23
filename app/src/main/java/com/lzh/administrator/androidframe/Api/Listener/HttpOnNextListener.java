package com.lzh.administrator.androidframe.Api.Listener;

/**
 * Created by lzh27651 on 2016/8/16.
 * 成功回调处理
 */

public interface HttpOnNextListener<T> {
    void onNext(T t);
}
