package com.lzh.administrator.androidframe.Adapter.adapter;

import android.content.Context;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;
import com.lzh.administrator.androidframe.Adapter.adapter.base.CommonAdapter;
import com.lzh.administrator.androidframe.GreenDAo.StationBean;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzh27651 on 2016/9/23.
 */

public class AutoCompleteTextViewAdapter extends CommonAdapter<StationBean> implements Filterable{

    private ArrayFilter filter;
    private ArrayList<StationBean> unfilterdatas;

    public AutoCompleteTextViewAdapter(Context mContext, List<StationBean> mDatas, int itemLayoutId) {
        super(mContext, mDatas, itemLayoutId);
    }

    @Override
    protected void convert(ViewHolder viewHolder, StationBean item) {
        TextView textView = viewHolder.getView(R.id.station_location_content);
        textView.setText(item.getName());
    }

    @Override
    public Filter getFilter() {
        if(filter==null){
            filter = new ArrayFilter();
        }
        return filter;
    }

    public class ArrayFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(unfilterdatas==null){
                unfilterdatas = new ArrayList<>(mDatas);
            }
            if(constraint==null || constraint.length()==0){
                ArrayList<StationBean> list = unfilterdatas;
                results.values = list;
                results.count = list.size();
            }else{
                String prefixString = constraint.toString().toLowerCase();

                ArrayList<StationBean> unfilteredValues = unfilterdatas;
                int count = unfilteredValues.size();

                ArrayList<StationBean> newValues = new ArrayList<>(count);

                for (int i = 0; i < count; i++) {
                    StationBean s = unfilteredValues.get(i);
                    if (s != null) {

                        if(s.getName()!=null && s.getName().startsWith(prefixString)){
                            LogUtil.d("name :"+s.getName()+" ,prefixString :"+prefixString);
                            newValues.add(s);
                        }else if(s.getPinyin()!=null && s.getPinyin().startsWith(prefixString)){
                            LogUtil.d("Pinyin :"+s.getPinyin()+" ,prefixString :"+prefixString);
                            newValues.add(s);
                        }else if(s.getPinyins()!=null && s.getPinyins().startsWith(prefixString)){
                            LogUtil.d("Pinyins :"+s.getPinyins()+" ,prefixString :"+prefixString);
                            newValues.add(s);
                        }else if(s.getFirstpys()!=null && s.getFirstpys().startsWith(prefixString)){
                            LogUtil.d("Firstpys :"+s.getFirstpys()+" ,prefixString :"+prefixString);
                            newValues.add(s);
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mDatas = (List<StationBean>) results.values;
            if(results.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }
}
