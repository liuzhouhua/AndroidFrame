package com.lzh.administrator.androidframe.Model;

import java.util.List;

/**
 * Created by lzh27651 on 2016/9/20.
 * 站点
 */

public class StationAndTitleBean {

    private TrainStationBean trainStationBean;

    private TitleBean titleBean;

    private List<TrainStationBean> trainStationBeanList;

    /**
     * 1、title 2、content 3、flow 4、letter_content
     */
    private String mStyle;

    public TrainStationBean getTrainStationBean() {
        return trainStationBean;
    }

    public void setTrainStationBean(TrainStationBean trainStationBean) {
        this.trainStationBean = trainStationBean;
    }

    public String getmStyle() {
        return mStyle;
    }

    public void setmStyle(String mStyle) {
        this.mStyle = mStyle;
    }

    public List<TrainStationBean> getTrainStationBeanList() {
        return trainStationBeanList;
    }

    public void setTrainStationBeanList(List<TrainStationBean> trainStationBeanList) {
        this.trainStationBeanList = trainStationBeanList;
    }

    public TitleBean getTitleBean() {
        return titleBean;
    }

    public void setTitleBean(TitleBean titleBean) {
        this.titleBean = titleBean;
    }
}
