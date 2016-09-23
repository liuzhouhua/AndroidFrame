package com.lzh.administrator.androidframe;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lzh.administrator.androidframe.GreenDAo.DaoMaster;
import com.lzh.administrator.androidframe.GreenDAo.DaoSession;
import com.lzh.administrator.androidframe.GreenDAo.StationBeanDao;
import com.lzh.administrator.androidframe.Utils.LogUtil;

/**
 * Created by lzh27651 on 2016/8/16.
 * 系统Application类，设置全局变量以及初始化组件]
 */

public class MyApplication extends Application {

    private static String TAG = MyApplication.class.getSimpleName();
    private volatile static MyApplication INSTANCE;
    private static SQLiteDatabase db;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static StationBeanDao stationBeanDao;


    public static MyApplication getInstance(){
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        init();
    }

    private void init() {
        //初始化Log
        LogUtil.isDebug = true;
    }

    public Context getContext(){
        return getApplicationContext();
    }

    public static DaoMaster getDaoMaster(Context context) {
        if(daoMaster==null){
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context,"gtgj.db",null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    public static SQLiteDatabase getDb(Context context) {
        if(db==null){
            if(daoMaster==null){
                daoMaster = getDaoMaster(context);
            }
            db = daoMaster.getDatabase();
        }
        return db;
    }

    public static DaoSession getDaoSession(Context context) {
        if(daoSession==null){
            if(daoMaster==null){
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }

    public static StationBeanDao getStationBeanDao(Context context){
        if(stationBeanDao==null){
            if(daoSession==null){
                daoSession = getDaoSession(context);
            }
            stationBeanDao = daoSession.getStationBeanDao();
        }
        return stationBeanDao;
    }
}
