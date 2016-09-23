package com.lzh.administrator.androidframe.Exception;

/**
 * Created by lzh27651 on 2016/8/16.
 * 自定义错误信息统一处理
 */

public class HttpTimeException extends RuntimeException {

    public static final int NO_DATA = 0x2;

    public HttpTimeException(String message) {
        super(message);
    }

    public HttpTimeException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    /**
     * 转换错误数据
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code){
            case NO_DATA:
                message = "无数据";
                break;
            default:
                message = "error";
                break;
        }
        return message;
    }
}
