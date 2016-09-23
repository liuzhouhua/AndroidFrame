package com.lzh.administrator.androidframe.Adapter.connector;

import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;
import com.lzh.administrator.androidframe.R;

/**
 * Created by lzh27651 on 2016/9/21.
 */

public class StationTitleDelegate implements ItemViewDelegate<StationAndTitleBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.adapter_station_title;
    }

    @Override
    public boolean isForViewType(StationAndTitleBean item, int position) {
        if("title".equals(item.getmStyle())){
            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, StationAndTitleBean stationAndTitleBean, int position) {
        TextView textView = holder.getView(R.id.station_location);
        textView.setText(stationAndTitleBean.getTitleBean().getmTitle());
    }
}
