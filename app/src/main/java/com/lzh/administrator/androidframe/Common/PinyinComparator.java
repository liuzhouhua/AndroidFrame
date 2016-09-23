package com.lzh.administrator.androidframe.Common;

import com.lzh.administrator.androidframe.GreenDAo.StationBean;

import java.util.Comparator;

/**
 * Created by lzh27651 on 2016/9/23.
 */

public class PinyinComparator implements Comparator<StationBean> {

    @Override
    public int compare(StationBean lhs, StationBean rhs) {
        //这里主要是用来对ListView里面的数据根据ABCDEFG...来排序
        if (rhs.getFirstpys().equals("#")) {
            return -1;
        } else if (lhs.getFirstpys().equals("#")) {
            return 1;
        } else {
            return lhs.getPinyins().compareTo(rhs.getPinyins());
        }
    }
}
