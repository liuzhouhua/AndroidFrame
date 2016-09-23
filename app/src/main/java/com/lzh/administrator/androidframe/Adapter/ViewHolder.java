package com.lzh.administrator.androidframe.Adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lzh27651 on 2016/9/20.
 * 通用的ViewHolder
 */

public class ViewHolder {

    /**
     * 存储item中的所有view
     */
    private final SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;


    private ViewHolder(Context context, ViewGroup parent,int layoutId,int position) {
        this.mViews = new SparseArray<>();
        this.mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(layoutId,parent,false);

        //setTag
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder的对象
     * @param context
     * @param mConvertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View mConvertView,
                                 ViewGroup parent,int layoutId,int position){
        if(mConvertView==null){
            return new ViewHolder(context,parent,layoutId,position);
        }
        return (ViewHolder) mConvertView.getTag();
    }

    /**
     * 通过控件的Id获取对应的控件，如果没有则加入mViews
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view==null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public View getmConvertView() {
        return mConvertView;
    }

    public Context getmContext() {
        return mContext;
    }
}
