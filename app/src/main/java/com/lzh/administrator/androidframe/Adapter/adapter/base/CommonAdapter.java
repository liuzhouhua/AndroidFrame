package com.lzh.administrator.androidframe.Adapter.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;

import java.util.List;

/**
 * Created by lzh27651 on 2016/9/20.
 * 单item类型adapter
 */

public abstract class CommonAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected final int mItemLayoutId;

    public CommonAdapter(Context mContext, List<T> mDatas ,int itemLayoutId) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent,mItemLayoutId,position);

        convert(viewHolder,getItem(position));

        return viewHolder.getmConvertView();
    }

    protected abstract void convert(ViewHolder viewHolder, T item);
}
