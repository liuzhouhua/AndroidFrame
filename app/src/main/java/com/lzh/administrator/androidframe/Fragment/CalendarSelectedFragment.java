package com.lzh.administrator.androidframe.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;
import com.lzh.administrator.androidframe.constant.Keys;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Date;

/**
 * Created by lzh27651 on 2016/9/26.
 */

public class CalendarSelectedFragment extends BaseFragment {

    private Date mCurrentDate;
    private MaterialCalendarView materialCalendarView;

    public static CalendarSelectedFragment newInstance(){
        return new CalendarSelectedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_date_time;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        (mBaseActivity).setTitle(R.string.main_date);
        mCurrentDate = (Date) getActivity().getIntent().getSerializableExtra(Keys.DATE);
        if(mCurrentDate == null){
            mCurrentDate = new Date();
        }
        materialCalendarView = (MaterialCalendarView) view.findViewById(R.id.materialCalendarView);
        materialCalendarView.setSelectedDate(mCurrentDate);
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                LogUtil.d("date :"+date.getYear()+":"+(date.getMonth()+1)+":"+date.getDay()+" ,selected :"+selected);
                Intent intent = new Intent();
                intent.putExtra(Keys.YEAR,date.getYear());
                intent.putExtra(Keys.MONTH,(date.getMonth()+1));
                intent.putExtra(Keys.DAY,date.getDay());
                intent.putExtra(Keys.DATE,date.getDate());
                getActivity().setResult(Activity.RESULT_OK,intent);
                getActivity().finish();
            }
        });
    }

    @Override
    public void initData() {

    }
}
