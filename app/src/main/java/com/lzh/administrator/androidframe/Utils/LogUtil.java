package com.lzh.administrator.androidframe.Utils;

import android.util.Log;

/**
 * Created by lzh27651 on 2016/8/16.
 * 日志类
 */

public class LogUtil {
    private LogUtil()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static final String TAG = "MYDEBUG";
    private static final String LOG_FORMAT = "%1$s\n%2$s";

    // 默认TAG打印msg
    public static void i(String msg)
    {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg)
    {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg)
    {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg)
    {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 自定义TAG打印msg
    public static void i(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg)
    {
        if (isDebug)
            Log.i(tag, msg);
    }

    //默认TAG打印对象Object
    public static void d(Object... args) {
        log(Log.DEBUG, null, TAG, args);
    }

    public static void v(Object... args) {
        log(Log.VERBOSE, null, TAG, args);
    }

    public static void i(Object... args) {
        log(Log.INFO, null, TAG, args);
    }

    public static void w( Object... args) {
        log(Log.WARN, null, TAG, args);
    }

    public static void e(Object... args) {
        log(Log.ERROR, null, TAG, args);
    }

    public static void e(Throwable ex,Object... args) {
        log(Log.ERROR, ex, TAG, args);
    }

    //自定义TAG打印对象Object
    public static void d(String tag, Object... args) {
        log(Log.DEBUG, null, tag, args);
    }

    public static void v(String tag, Object... args) {
        log(Log.VERBOSE, null, tag, args);
    }

    public static void i(String tag, Object... args) {
        log(Log.INFO, null, tag, args);
    }

    public static void w(String tag, Object... args) {
        log(Log.WARN, null, tag, args);
    }

    public static void e(Throwable ex) {
        log(Log.ERROR, ex, null);
    }

    public static void e(String tag, Object... args) {
        log(Log.ERROR, null, tag, args);
    }

    public static void e(Throwable ex, String tag, Object... args) {
        log(Log.ERROR, ex, tag, args);
    }

    private static void log(int priority, Throwable ex, String tag, Object... args) {

        if (!LogUtil.isDebug) return;

        String log = "";
        if (ex == null) {
            if(args != null && args.length > 0){
                for(Object obj : args){
                    log += String.valueOf(obj);
                }
            }
        } else {
            String logMessage = ex.getMessage();
            String logBody = Log.getStackTraceString(ex);
            log = String.format(LOG_FORMAT, logMessage, logBody);
        }
        Log.println(priority, tag, log);
    }
}
