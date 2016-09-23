package com.lzh.administrator.androidframe.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lzh27651 on 2016/9/13.
 */

public abstract class BaseFragment extends Fragment{

    protected BaseActivity mBaseActivity;

    protected BaseFragment mCurrentFragment;

    //获取布局文件ID
    protected abstract int getLayoutId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    public abstract void initData();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mBaseActivity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mBaseActivity = (BaseActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(),container,false);
        initView(view,savedInstanceState);
        initData();
        return view;
    }
}
