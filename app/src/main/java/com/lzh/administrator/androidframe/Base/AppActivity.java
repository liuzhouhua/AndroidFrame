package com.lzh.administrator.androidframe.Base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lzh.administrator.androidframe.Common.ActivityManager;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by lzh27651 on 2016/9/13.
 */

public abstract class AppActivity extends BaseActivity {

    public Context mContext;
    private static Toast toast;
    private Toolbar mToolbar;
    private ImageView mToolbar_Back;
    private ImageView mToolbar_Help;
    private TextView mToolbar_Title;
    private TextView mToolbar_SunTitile;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base;
    }

    @Override
    protected int getFragmentContentId() {
        return R.id.fragment_container;
    }

    //获取第一个fragment
    protected abstract BaseFragment getFirstFragment();

    //子类自己的一些设置
    protected abstract void otherInit();

    //获取Intent
    protected void handleIntent(Intent intent) {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.colorBule);//通知栏所需颜色
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        if (null != getIntent()) {
            handleIntent(getIntent());
        }

        // 设置不能横屏
        mContext = this;
        init();
        //避免重复添加Fragment
        if (null == getSupportFragmentManager().getFragments()) {
            LogUtil.d("getSupportFragmentManager().getFragments() is null");
            BaseFragment firstFragment = getFirstFragment();
            if (null != firstFragment) {
                addFragment(firstFragment,true,false);
            }
        }
        ActivityManager.getInstance().addActivity(this);
    }

    private void init() {
        initDefaultActionbar();
        otherInit();
    }

    /**
     * 设置默认actionbar
     */
    protected void initDefaultActionbar(){
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar_Back = (ImageView) findViewById(R.id.toolbar_back);
        mToolbar_Help = (ImageView) findViewById(R.id.toolbar_menu);
        mToolbar_Title = (TextView) findViewById(R.id.toolbar_title);
        mToolbar_SunTitile = (TextView) findViewById(R.id.toolbar_subtitle);

        mToolbar_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
        }
        if(mToolbar_Title!=null){
            mToolbar_Title.setText("");
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mToolbar_Title.setText(title);
    }

    /**
     * 显示短时间Toast
     * @param msg
     */
    public void showShortToast(String msg){
        if (toast == null) {
            toast = Toast.makeText(mContext,
                    msg,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 显示长时间Toast
     * @param msg
     */
    public void showLongToast(String msg){
        if (toast == null) {
            toast = Toast.makeText(mContext,
                    msg,
                    Toast.LENGTH_LONG);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 设置右边的图标隐藏与否
     */
    public void setRightIconVisibility(int visibility){
        mToolbar_Help.setVisibility(visibility);
    }

    /**
     * 设置右边的图标隐藏与否
     */
    public void setLeftIconVisibility(int visibility){
        mToolbar_Back.setVisibility(visibility);
    }

    /**
     * 设置右边的文字隐藏与否
     */
    public void setRightTextVisibility(int visibility){
        mToolbar_SunTitile.setVisibility(visibility);
    }

    /**
     * 设置右边的文字内容
     * @param display
     */
    public void setRightText(String display){
        mToolbar_SunTitile.setText(display);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().removeActivity(this);
    }


    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams layoutParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            layoutParams.flags |= bits;
        } else {
            layoutParams.flags &= ~bits;
        }
        win.setAttributes(layoutParams);
    }
}
