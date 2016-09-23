package com.lzh.administrator.androidframe.Api.Entity;

/**
 * Created by lzh27651 on 2016/8/16.
 * 回调信息统一封装类
 */

public class BaseResultEntity<T> {

    private String validateMessagesShowId;
    private boolean status;
    private int httpstatus;

    private T data;

    public int getHttpstatus() {
        return httpstatus;
    }

    public T getData() {
        return data;
    }
}
