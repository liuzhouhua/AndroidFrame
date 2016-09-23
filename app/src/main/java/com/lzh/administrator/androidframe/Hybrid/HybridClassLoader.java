package com.lzh.administrator.androidframe.Hybrid;

import android.text.TextUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Created by lzh27651 on 2016/8/19.
 * 加载webView事件处理类
 */

public class HybridClassLoader {

    /**
     * 加载所有的事件
     */
    private static HashMap<String,String> mAllHybridHandlerClass = new HashMap<String,String>(){
        {
            put(HyBridConstants.URL_TASK,"com.lzh.administrator.androidframe.Hybrid.Imp.URLHandlerTest");
            put(HyBridConstants.URL_TASK1,"com.lzh.administrator.androidframe.Hybrid.Imp.OtherHandlerTest");
        }
    };

    /**
     * 获取事件处理类
     * @param eventName
     * @return
     */
    public static HybridHandler getHybridHandler(String eventName){
        String classPath = mAllHybridHandlerClass.get(eventName);
        Object obj = loadWebappClass(classPath);
        if(obj!=null && obj instanceof HybridHandler){
            return (HybridHandler) obj;
        }
        return null;
    }


    /**
     * 加载对象
     * @param classPath
     * @return
     */
    public static Object loadWebappClass(String classPath){
        if(TextUtils.isEmpty(classPath)){
            return null;
        }
        try {
            Class name = Class.forName(classPath);

            Constructor[] constructors = name.getConstructors();
            Object object = constructors[0].newInstance();
            return object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }
}
