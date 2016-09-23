package com.lzh.administrator.androidframe.Adapter.connector;

import android.view.LayoutInflater;
import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.DensityUtil;
import com.lzh.administrator.androidframe.Utils.ScreenUtil;
import com.lzh.administrator.androidframe.widget.FlowLayout;

/**
 * Created by lzh27651 on 2016/9/21.
 */

public class FlowContentItemDelegate implements ItemViewDelegate<StationAndTitleBean> {
    @Override
    public int getItemViewLayoutId() {
        return R.layout.adapter_station_flow;
    }

    @Override
    public boolean isForViewType(StationAndTitleBean item, int position) {
        if("flow".equals(item.getmStyle())){
            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, StationAndTitleBean stationAndTitleBean, int position) {
        FlowLayout flowLayout = holder.getView(R.id.station_history_content);
        flowLayout.removeAllViews();
        int width = ScreenUtil.getScreenWidth(holder.getmContext());
        int height = DensityUtil.dp2px(holder.getmContext(),stationAndTitleBean.getTrainStationBeanList().size()/3
                * DensityUtil.dp2px(holder.getmContext(),26));
        flowLayout.setMinimumHeight(height);
        for(int i = 0; i < stationAndTitleBean.getTrainStationBeanList().size(); i ++) {
            TextView view = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.adapter_station_flow_item, flowLayout, false);
            view.setWidth(DensityUtil.dp2px(holder.getmContext(), DensityUtil.px2dp(holder.getmContext(), width) - DensityUtil.dp2px(holder.getmContext(), 35)) / 3);
            view.setText(stationAndTitleBean.getTrainStationBeanList().get(i).getmName());
            flowLayout.addView(view);
        }

    }

}
