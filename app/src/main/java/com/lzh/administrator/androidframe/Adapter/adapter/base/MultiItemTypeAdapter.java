package com.lzh.administrator.androidframe.Adapter.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lzh.administrator.androidframe.Adapter.connector.ItemViewDelegate;
import com.lzh.administrator.androidframe.Adapter.ItemViewDelegateManager;
import com.lzh.administrator.androidframe.Adapter.ViewHolder;

import java.util.List;

/**
 * Created by lzh27651 on 2016/9/20.
 * 多item类型adapter
 */

public class MultiItemTypeAdapter<T> extends BaseAdapter {

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected ItemViewDelegateManager mItemViewDelegateManager;

    public MultiItemTypeAdapter(Context mContext, List<T> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    /**
     * 添加一种item类型
     * @param itemViewDelegate
     * @return
     */
    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate){
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    /**
     * 判断是否是多类型item
     * @return
     */
    public boolean useItemViewDelegateManager(){
        return mItemViewDelegateManager.getItemViewDelegateCount()>0;
    }

    @Override
    public int getViewTypeCount() {
        if(useItemViewDelegateManager()){
            return mItemViewDelegateManager.getItemViewDelegateCount();
        }
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        if(useItemViewDelegateManager()){
            int viewType = mItemViewDelegateManager.getItemViewType(mDatas.get(position),position);
            return viewType;
        }
        return super.getItemViewType(position);
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
        ItemViewDelegate delegate = mItemViewDelegateManager.getItemViewDelegate(getItem(position),position);
        int layoutId = delegate.getItemViewLayoutId();
        ViewHolder viewHolder = ViewHolder.get(mContext,convertView,parent,layoutId,position);
        convert(viewHolder,getItem(position),position);
        return viewHolder.getmConvertView();
    }

    protected void convert(ViewHolder viewHolder, T item, int position) {
        mItemViewDelegateManager.convert(viewHolder,item,position);
    }
}
