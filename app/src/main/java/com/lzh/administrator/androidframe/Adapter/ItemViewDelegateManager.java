package com.lzh.administrator.androidframe.Adapter;

import android.support.v4.util.SparseArrayCompat;

import com.lzh.administrator.androidframe.Adapter.connector.ItemViewDelegate;

/**
 * Created by lzh27651 on 2016/9/20.
 */

public class ItemViewDelegateManager<T> {

    /**
     * item类型存储
     * 每种Item类型对应一个ItemViewDelegate
     * T : 数据类型
     * ItemViewDelegate : item接口
     */
    SparseArrayCompat<ItemViewDelegate<T>> delegates = new SparseArrayCompat<>();

    /**
     * 获取类型数量
     * @return
     */
    public int getItemViewDelegateCount(){
        return delegates.size();
    }

    /**
     * 添加一种item类型
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(ItemViewDelegate<T> delegate){

        int viewType = getItemViewDelegateCount();
        if(delegate!=null){
            delegates.put(viewType,delegate);
        }
        return this;
    }

    /**
     * 添加一种item类型（类型自定）
     * @param viewType
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> addDelegate(int viewType, ItemViewDelegate<T> delegate){
        if(delegates.get(viewType) != null){
            throw new IllegalArgumentException(
                    "An ItemViewDelegate is already registered for the viewType = "
                            + viewType
                            + ". Already registered ItemViewDelegate is "
                            + delegates.get(viewType));
        }
        delegates.put(viewType,delegate);
        return this;
    }

    /**
     * 通过ItemViewDelegate删除一种类型
     * @param delegate
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(ItemViewDelegate<T> delegate){
        if(delegate == null){
            throw new NullPointerException("ItemViewDelegate is null");
        }
        int indexToMove = delegates.indexOfValue(delegate);
        if(indexToMove>=0){
            delegates.removeAt(indexToMove);
        }
        return this;
    }

    /**
     * 通过类型key删除一种类型
     * @param viewType
     * @return
     */
    public ItemViewDelegateManager<T> removeDelegate(int viewType){
        int indexToMove = delegates.indexOfKey(viewType);
        if(indexToMove>=0){
            delegates.removeAt(indexToMove);
        }
        return this;
    }

    /**
     * 获取在position上的item的类型
     * @param item
     * @param position
     * @return
     */
    public int getItemViewType(T item,int position){
        int delegatesCount = delegates.size();
        for(int i=delegatesCount -1;i>=0;i--){
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if(delegate.isForViewType(item,position)){
                return delegates.keyAt(i);
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }

    /**
     * 通过itemViewDelegate获取item类型
     * @param itemViewDelegate
     * @return
     */
    public int getItemViewType(ItemViewDelegate<T> itemViewDelegate){
        return delegates.indexOfValue(itemViewDelegate);
    }

    /**
     * 获取某种类型的布局id
     * @param viewType
     * @return
     */
    public int getItemViewLayoutId(int viewType){
        return delegates.get(viewType).getItemViewLayoutId();
    }


    /**
     * 获取position上的item的布局id
     * @param item
     * @param position
     * @return
     */
    public int getItemViewLayoutId(T item,int position){
       return getItemViewDelegate(item,position).getItemViewLayoutId();
    }


    /**
     * 获取position上的item的ItemViewDelegate
     * @param item
     * @param position
     * @return
     */
    public ItemViewDelegate getItemViewDelegate(T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = delegatesCount - 1; i >= 0; i--)
        {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);
            if (delegate.isForViewType(item, position))
            {
                return delegate;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegate added that matches position=" + position + " in data source");
    }


    public void convert(ViewHolder holder, T item, int position)
    {
        int delegatesCount = delegates.size();
        for (int i = 0; i < delegatesCount; i++)
        {
            ItemViewDelegate<T> delegate = delegates.valueAt(i);

            if (delegate.isForViewType(item, position))
            {
                delegate.convert(holder, item, position);
                return;
            }
        }
        throw new IllegalArgumentException(
                "No ItemViewDelegateManager added that matches position=" + position + " in data source");
    }
}
