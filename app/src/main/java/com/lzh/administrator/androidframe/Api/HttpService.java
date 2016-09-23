package com.lzh.administrator.androidframe.Api;

import com.lzh.administrator.androidframe.Api.Entity.BaseResultEntity;
import com.lzh.administrator.androidframe.Test.Subject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lzh27651 on 2016/8/16.
 * Service统一接口数据
 */

public interface HttpService {

    //********************************************使用RxJava******************************************
    /**
     * 余票查询接口
     * @param date 出发日期
     * @param from_station 出发站
     * @param to_station 到达站
     * @param purposeCode 成人票（ADULT）或学生票
     * @return
     */
    @GET("leftTicket/queryT")
    Observable<BaseResultEntity<List<Subject>>> queryLeftTicket(@Query("leftTicketDTO.train_date") String date,
                                                                @Query("leftTicketDTO.from_station") String from_station,
                                                                @Query("leftTicketDTO.to_station") String to_station,
                                                                @Query("purpose_codes") String purposeCode);

    //*******************************************不是呀RxJava*****************************************

    /**
     * 下载火车站点文件
     * @return
     */
    @GET("resources/js/framework/station_name.js")
    Call<ResponseBody> downloadFileWithFixedUrl();
}
