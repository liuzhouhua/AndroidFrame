package com.lzh.administrator.androidframe.Adapter.connector;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
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

    private int mCurrnet = -1;
    private onSelectedListenter listenter;

    public FlowContentItemDelegate(onSelectedListenter listenter,int current) {
        this.listenter = listenter;
        this.mCurrnet = current;
    }

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
    public void convert(final ViewHolder holder, final StationAndTitleBean stationAndTitleBean, int position) {
        final FlowLayout flowLayout = holder.getView(R.id.station_history_content);
        flowLayout.removeAllViews();
        final int width = ScreenUtil.getScreenWidth(holder.getmContext());
        int height = DensityUtil.dp2px(holder.getmContext(),stationAndTitleBean.getTrainStationBeanList().size()/3
                * DensityUtil.dp2px(holder.getmContext(),26));
        flowLayout.setMinimumHeight(height);
        for(int i = 0; i < stationAndTitleBean.getTrainStationBeanList().size(); i ++) {
            TextView view;
            if(mCurrnet==i){
                view = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.adapter_station_flow_item, flowLayout, false);
                view.setWidth(DensityUtil.dp2px(holder.getmContext(), DensityUtil.px2dp(holder.getmContext(), width) - DensityUtil.dp2px(holder.getmContext(), 35)) / 3);
                view.setText(stationAndTitleBean.getTrainStationBeanList().get(i).getmName());
                view.setBackgroundResource(R.drawable.shape_flow_item_selected);
                view.setTextColor(Color.rgb(255,255,255));
            }else{
                view = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.adapter_station_flow_item, flowLayout, false);
                view.setWidth(DensityUtil.dp2px(holder.getmContext(), DensityUtil.px2dp(holder.getmContext(), width) - DensityUtil.dp2px(holder.getmContext(), 35)) / 3);
                view.setText(stationAndTitleBean.getTrainStationBeanList().get(i).getmName());
            }
            flowLayout.addView(view);
        }
        flowLayout.setmOnTagClickListener(new FlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int currentPosition,int lastPosition, FlowLayout parent) {
                mCurrnet = currentPosition;
                TextView viewCurrent = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.adapter_station_flow_item, flowLayout, false);
                viewCurrent.setWidth(DensityUtil.dp2px(holder.getmContext(), DensityUtil.px2dp(holder.getmContext(), width) - DensityUtil.dp2px(holder.getmContext(), 35)) / 3);
                viewCurrent.setText(stationAndTitleBean.getTrainStationBeanList().get(currentPosition).getmName());
                viewCurrent.setBackgroundResource(R.drawable.shape_flow_item_selected);
                viewCurrent.setTextColor(Color.rgb(255,255,255));
                flowLayout.removeViewAt(currentPosition);
                flowLayout.addView(viewCurrent,currentPosition);
                if(lastPosition!=-1){
                    TextView viewLast = (TextView) LayoutInflater.from(holder.getmContext()).inflate(R.layout.adapter_station_flow_item, flowLayout, false);
                    viewLast.setWidth(DensityUtil.dp2px(holder.getmContext(), DensityUtil.px2dp(holder.getmContext(), width) - DensityUtil.dp2px(holder.getmContext(), 35)) / 3);
                    viewLast.setText(stationAndTitleBean.getTrainStationBeanList().get(lastPosition).getmName());
                    flowLayout.removeViewAt(lastPosition);
                    flowLayout.addView(viewLast,lastPosition);
                }
                if(listenter!=null){
                    listenter.onItemSelected(stationAndTitleBean,currentPosition);
                }
                return true;
            }
        });
    }

    public interface onSelectedListenter{
        public void onItemSelected(StationAndTitleBean bean,int postion);
    }
}
