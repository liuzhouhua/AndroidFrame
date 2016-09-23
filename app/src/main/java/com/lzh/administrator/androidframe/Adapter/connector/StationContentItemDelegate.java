package com.lzh.administrator.androidframe.Adapter.connector;

import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;
import com.lzh.administrator.androidframe.R;

/**
 * Created by lzh27651 on 2016/9/21.
 */

public class StationContentItemDelegate implements ItemViewDelegate<StationAndTitleBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.adapter_station_content;
    }

    @Override
    public boolean isForViewType(StationAndTitleBean item, int position) {
        if("content".equals(item.getmStyle())){
            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, StationAndTitleBean station, int position) {
        TextView textView = holder.getView(R.id.station_location_content);
        textView.setText(station.getTrainStationBean().getmName());
    }
}
