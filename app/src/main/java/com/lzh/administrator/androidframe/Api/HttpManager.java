package com.lzh.administrator.androidframe.Api;

import com.lzh.administrator.androidframe.Api.Entity.BaseEntity;
import com.lzh.administrator.androidframe.Api.Listener.ProgressListener;
import com.lzh.administrator.androidframe.Api.Response.ProgressResponseBody;
import com.lzh.administrator.androidframe.MyApplication;
import com.lzh.administrator.androidframe.Utils.NetWorkUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lzh27651 on 2016/8/16.
 * Http交互处理类
 */

public class HttpManager {

//    public static final String BASE_URL = "https://api.douban.com/v2/movie/";
    public static final String BASE_URL = "https://kyfw.12306.cn/otn/";
    private static final int DEFAULT_TIMEOUT = 6;

    // 消息头（仅举例）
    private static final String HEADER_X_HB_Client_Type = "X-HB-Client-Type";
    private static final String FROM_ANDROID = "ayb-android";

    private HttpService httpService;
    private volatile static HttpManager INSTANCE;
    private ProgressListener listener;

    //拦截器 添加header
    private Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader(HEADER_X_HB_Client_Type,FROM_ANDROID)
                    .build();
            return chain.proceed(request);
        }
    };

    /**
     * 拦截器 缓存设置
     */
    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if(!NetWorkUtil.isNetConnected(MyApplication.getInstance().getContext())){
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            Response response1;
            if(NetWorkUtil.isNetConnected(MyApplication.getInstance().getContext())){
                int maxAge = 60;
                // 有网络时 设置缓存超时时间1分钟
                response1 = response.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .body(new ProgressResponseBody(response.body(),listener))
                        .build();
            }else{
                // 无网络时，设置超时为6小时
                int maxStale = 60 * 60 * 6;
                response1 = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .body(new ProgressResponseBody(response.body(),listener))
                        .build();
            }
            return response1;
        }
    };

    //私有构造函数
    private HttpManager(){
        //log拦截器  打印所有的log
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //设置缓存
        File cacheFile = new File(MyApplication.getInstance().getContext().getCacheDir(),"cache");
        Cache cache = new Cache(cacheFile,1024 * 1024 * 50);//大小50M

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(loggingInterceptor);
        //以下两个拦截器必须加上少一个都不行
        builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        builder.addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        builder.cache(cache);
        InputStream is = null;
        try {
            is = MyApplication.getInstance().getContext().getAssets().open("srca.cer");
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpUtils.SSLParams sslParams = HttpUtils.getSslSocketFactory(new InputStream[]{is}, null, null);
        builder.sslSocketFactory(sslParams.sSLSocketFactory,sslParams.trustManager);

        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        httpService = retrofit.create(HttpService.class);
    }

    //获取单例
    public static HttpManager getInstance(){
        if(INSTANCE==null){
            synchronized (HttpManager.class){
                if(INSTANCE==null){
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    public ProgressListener getListener() {
        return listener;
    }

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    public HttpService getHttpService() {
        return httpService;
    }

    /**
     * 处理http请求(有类似的返回值类型使用RxJava)
     * @param baseEntity 封装的请求数据
     */
    public void doHttpDeal(BaseEntity baseEntity){
        Observable observable = baseEntity.getObservable(httpService)
                .map(baseEntity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(baseEntity.getSubscriber());
    }

    /**
     * 处理http请求（没有类似的返回值类型不使用RxJava）
     * @param call
     * @param callback
     */
    public void doHttpDeal(Call<ResponseBody> call,Callback<ResponseBody> callback){
        Call<ResponseBody> mCall = call;
        mCall.enqueue(callback);
    }
}
