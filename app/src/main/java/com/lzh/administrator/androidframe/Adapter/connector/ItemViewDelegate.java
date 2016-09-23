package com.lzh.administrator.androidframe.Adapter.connector;

import com.lzh.administrator.androidframe.Adapter.ViewHolder;

/**
 * Created by lzh27651 on 2016/9/20.
 * item对应接口
 */

public interface ItemViewDelegate<T> {

    /**
     * 得到Item的LayoutId
     * @return
     */
    public abstract int getItemViewLayoutId();

    /**
     * 判断类型
     * @param item
     * @param position
     * @return
     */
    public abstract boolean isForViewType(T item, int position);

    /**
     * 具体业务
     * @param holder
     * @param t
     * @param position
     */
    public abstract void convert(ViewHolder holder, T t, int position);
}
