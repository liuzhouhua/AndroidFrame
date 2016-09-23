package com.lzh.administrator.androidframe.Adapter.connector;

import android.view.View;
import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;
import com.lzh.administrator.androidframe.Model.StationAndTitleBean;
import com.lzh.administrator.androidframe.R;

import java.util.List;

/**
 * Created by lzh27651 on 2016/9/21.
 */

public class StationLetterAndContentDelegate implements ItemViewDelegate<StationAndTitleBean>{

    private List<StationAndTitleBean> stationAndTitleBeens;

    public StationLetterAndContentDelegate(List<StationAndTitleBean> stationAndTitleBeens) {
        this.stationAndTitleBeens = stationAndTitleBeens;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.adapter_station_letter_content;
    }

    @Override
    public boolean isForViewType(StationAndTitleBean item, int position) {
        if("letter_content".equals(item.getmStyle())){
            return true;
        }
        return false;
    }

    @Override
    public void convert(ViewHolder holder, StationAndTitleBean stationAndTitleBean, int position) {
        TextView letter = holder.getView(R.id.station_letter_content_title);
        TextView content = holder.getView(R.id.station_letter_content_content);

        int section = getSectionForPosition(stationAndTitleBean);
        if(position==getPositionForSection(section)){
            letter.setText(stationAndTitleBean.getTrainStationBean().getmFirstPYS());
            letter.setVisibility(View.VISIBLE);
        }else{
            letter.setVisibility(View.GONE);
        }
        content.setText(stationAndTitleBean.getTrainStationBean().getmName());
    }

    public int getSectionForPosition(StationAndTitleBean stationAndTitleBean) {
        return stationAndTitleBean.getTrainStationBean().getmFirstPYS().charAt(0);
    }

    public int getPositionForSection(int sectionIndex) {
        if(stationAndTitleBeens!=null){
            for(int i= 4;i<stationAndTitleBeens.size();i++){
                char assii = stationAndTitleBeens.get(i).getTrainStationBean().getmFirstPYS().charAt(0);
                if(assii==sectionIndex){
                    return i;
                }
            }
        }
        return -1;
    }
}
