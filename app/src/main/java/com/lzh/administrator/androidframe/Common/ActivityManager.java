package com.lzh.administrator.androidframe.Common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by lzh27651 on 2016/8/18.
 * activity管理者
 */

public class ActivityManager {


    private volatile static ActivityManager INSTANCE;
    private static Stack<Activity> activityStack;

    private ActivityManager() {
    }

    public static ActivityManager getInstance(){
        if(INSTANCE==null){
            synchronized (ActivityManager.class){
                if(INSTANCE==null){
                    INSTANCE = new ActivityManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * add Activity to stack
     * @param activity
     */
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack = new Stack<>();
        }
        activityStack.add(activity);
    }

    /**
     * remove Activity from stack
     * @param activity
     */
    public void removeActivity(Activity activity){
        if(activityStack==null){
            activityStack = new Stack<>();
        }
        activityStack.remove(activity);
    }

    /**
     * get current activity
     * @return
     */
    public Activity getCurrentActivity(){
        Activity activity = activityStack.lastElement();
        return activity;
    }


    /**
     * finish all activities
     */
    public void finishAllActivities(){
        for(Activity activity : activityStack){
            if(activity!=null){
                activity.finish();
            }
        }
        activityStack.clear();
    }


    /**
     * exit System
     */
    public void exit(){
        try {
            finishAllActivities();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
