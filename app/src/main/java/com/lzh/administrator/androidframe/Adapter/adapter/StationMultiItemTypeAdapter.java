package com.lzh.administrator.androidframe.Adapter.adapter;

import android.content.Context;

import com.lzh.administrator.androidframe.Adapter.adapter.base.MultiItemTypeAdapter;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;

import java.util.List;

/**
 * Created by lzh27651 on 2016/9/23.
 */

public class StationMultiItemTypeAdapter extends MultiItemTypeAdapter<StationAndTitleBean>{

    private List<StationAndTitleBean> mData;

    public StationMultiItemTypeAdapter(Context mContext, List<StationAndTitleBean> mDatas) {

        super(mContext, mDatas);

        this.mData = mDatas;
    }

    public int getPositionForSection(int sectionIndex) {
        if(mData!=null){
            for(int i= 4;i<mData.size();i++){
                char assii = mData.get(i).getTrainStationBean().getmFirstPYS().charAt(0);
                if(assii==sectionIndex){
                    return i;
                }
            }
        }
        return -1;
    }
}
