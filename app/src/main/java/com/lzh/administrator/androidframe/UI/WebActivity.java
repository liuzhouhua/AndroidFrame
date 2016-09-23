package com.lzh.administrator.androidframe.UI;

import com.lzh.administrator.androidframe.Base.AppActivity;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Fragment.WebViewFragment;

/**
 * Created by lzh27651 on 2016/8/18.
 */

public class WebActivity extends AppActivity {

    @Override
    protected BaseFragment getFirstFragment() {
        return WebViewFragment.newInstance();
    }

    @Override
    protected void otherInit() {

    }

}
