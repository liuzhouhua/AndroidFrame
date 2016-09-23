package com.lzh.administrator.androidframe.Api.Entity;

import com.lzh.administrator.androidframe.Api.HttpService;
import com.lzh.administrator.androidframe.Exception.HttpTimeException;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by lzh27651 on 2016/8/16.
 * 请求数据统一封装类
 */

public abstract class BaseEntity<T> implements Func1<BaseResultEntity<T>,T> {


    /**
     * 获得被观察者(服务器的数据)
     * @param service
     * @return
     */
    public abstract Observable<BaseResultEntity<T>> getObservable(HttpService service);


    /**
     * 获取观察者(处理数据)
     * @return
     */
    public abstract Subscriber getSubscriber();

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param tBaseResultEntity
     * @return
     */
    @Override
    public T call(BaseResultEntity<T> tBaseResultEntity) {
        if(tBaseResultEntity.getHttpstatus()!=200){
            throw new HttpTimeException(HttpTimeException.NO_DATA);
        }
        return tBaseResultEntity.getData();
    }
}
