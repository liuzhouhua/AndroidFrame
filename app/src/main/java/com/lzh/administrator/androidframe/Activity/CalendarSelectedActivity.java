package com.lzh.administrator.androidframe.Activity;

import com.lzh.administrator.androidframe.Base.AppActivity;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Fragment.CalendarSelectedFragment;

/**
 * Created by lzh27651 on 2016/9/26.
 */

public class CalendarSelectedActivity extends AppActivity{
    @Override
    protected BaseFragment getFirstFragment() {
        return CalendarSelectedFragment.newInstance();
    }

    @Override
    protected void otherInit() {

    }
}
