package com.lzh.administrator.androidframe.Activity;

import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.lzh.administrator.androidframe.Base.AppActivity;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Fragment.GrabFragment;
import com.lzh.administrator.androidframe.Fragment.BookFragment;
import com.lzh.administrator.androidframe.Fragment.MeFragment;
import com.lzh.administrator.androidframe.Fragment.OrderFragment;
import com.lzh.administrator.androidframe.Fragment.ServiceFragment;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;

import java.util.ArrayList;

public class MainActivity extends AppActivity {

    private ArrayList<BaseFragment> fragments;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_base_main;
    }

    @Override
    protected BaseFragment getFirstFragment() {
        return BookFragment.newInstance();
    }

    @Override
    protected void otherInit() {
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.setActiveColor(R.color.colorBule);
        bottomNavigationBar.setBarBackgroundColor(R.color.colorBarBackground);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.bar_book, R.string.bar_book))
                .addItem(new BottomNavigationItem(R.drawable.bar_grab, R.string.bar_grab))
                .addItem(new BottomNavigationItem(R.drawable.bar_order, R.string.bar_order))
                .addItem(new BottomNavigationItem(R.drawable.bar_service, R.string.bar_server))
                .addItem(new BottomNavigationItem(R.drawable.bar_me, R.string.bar_me))
                .initialise();


        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                LogUtil.d("onTabSelected :"+position);
                if(position<fragments.size()){
                    addFragment(fragments.get(position),false,true);
                }
            }

            @Override
            public void onTabUnselected(int position) {
                LogUtil.d("onTabUnselected :"+position);
            }

            @Override
            public void onTabReselected(int position) {
                LogUtil.d("onTabReselected :"+position);
            }
        });

        fragments = getFragments();
        setDefaultFragment();
    }

    private ArrayList<BaseFragment> getFragments() {
        ArrayList<BaseFragment> fragments = new ArrayList<>();
        fragments.add(BookFragment.newInstance());
        fragments.add(GrabFragment.newInstance(1));
        fragments.add(OrderFragment.newInstance(2));
        fragments.add(ServiceFragment.newInstance(3));
        fragments.add(MeFragment.newInstance(4));
        return fragments;
    }

    private void setDefaultFragment() {
        addFragment(fragments.get(0),false,true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setLeftIconVisibility(View.GONE);
    }
}
