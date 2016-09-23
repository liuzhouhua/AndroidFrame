package com.lzh.administrator.androidframe.Base;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * Created by lzh27651 on 2016/8/18.
 * activity 基类 待补充
 */

public abstract class BaseActivity extends AppCompatActivity {


    //布局文件ID
    protected abstract int getContentViewId();

    //布局中Fragment的ID
    protected abstract int getFragmentContentId();

    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    private BaseFragment mCurrentFragment;

    //添加fragment
    protected void addFragment(BaseFragment fragment,boolean isNeedAddToBackStack
                ,boolean isNeedHide) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(isNeedHide){
                if(getSupportFragmentManager().getFragments()!=null){
                    //隐藏当前fragment
                    if(mCurrentFragment!=null){
                        ft.hide(getSupportFragmentManager().findFragmentByTag(mCurrentFragment.getClass().getSimpleName()));
                    }
                }
                //显示添加的fragment
                if(!fragment.isAdded()){
                    ft.add(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
                }else{
                    ft.show(fragment);
                }
            }else{
                ft.replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName());
            }
            if(isNeedAddToBackStack){
                ft.addToBackStack(fragment.getClass().getSimpleName());
            }
            ft.commitAllowingStateLoss();

            mCurrentFragment = fragment;
        }
    }

    //移除fragment
    protected void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //返回键返回事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
