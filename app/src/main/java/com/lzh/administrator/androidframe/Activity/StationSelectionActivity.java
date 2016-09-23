package com.lzh.administrator.androidframe.Activity;

import android.view.View;

import com.lzh.administrator.androidframe.Base.AppActivity;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Fragment.StationSelectionFragment;

/**
 * Created by lzh27651 on 2016/9/19.
 */

public class StationSelectionActivity extends AppActivity {
    @Override
    protected BaseFragment getFirstFragment() {
        return StationSelectionFragment.newInstance();
    }

    @Override
    protected void otherInit() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        setRightIconVisibility(View.GONE);
    }
}
