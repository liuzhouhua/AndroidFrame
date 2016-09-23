package com.lzh.administrator.androidframe.Test;

import com.lzh.administrator.androidframe.Api.HttpService;
import com.lzh.administrator.androidframe.Api.Entity.BaseEntity;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by lzh27651 on 2016/8/16.
 * 测试请求数据
 */

public class SubjectPost extends BaseEntity{

    private Subscriber subscriber;
    private boolean all;

    public SubjectPost(Subscriber subscriber, boolean all) {
        this.subscriber = subscriber;
        this.all = all;
    }

    /**
     * 必须实现，网络访问实际方法调用
     * @param service
     * @return
     */
    @Override
    public Observable getObservable(HttpService service) {
        return service.queryLeftTicket("2016-09-23","SZH","SHH","ADULT");
    }

    /**
     * 观察者，可实现自定义观察者
     * @return
     */
    @Override
    public Subscriber getSubscriber() {
        return subscriber;
    }
}
