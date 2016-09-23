package com.lzh.administrator.androidframe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lzh.administrator.androidframe.Api.HttpManager;
import com.lzh.administrator.androidframe.Api.Listener.HttpOnNextListener;
import com.lzh.administrator.androidframe.Api.Subscriber.ProgressSubscriber;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Test.Subject;
import com.lzh.administrator.androidframe.Test.SubjectPost;
import com.lzh.administrator.androidframe.UI.WebActivity;
import com.lzh.administrator.androidframe.Utils.LogUtil;

import java.util.List;

import rx.Subscriber;

/**
 * Created by lzh27651 on 2016/9/13.
 */

public class ServiceFragment extends BaseFragment{

    private Button mButton;
    private TextView mTextView;
    private int[] a = {1,2,3,4,5,6,7,8,9,0};
    private int index;

    public static ServiceFragment newInstance(int index){
        ServiceFragment mainFragment = new ServiceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("INDEX",index);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LogUtil.d("onCreateView 第"+getArguments().getInt("INDEX")+"页");
        mTextView = (TextView) view.findViewById(R.id.text);
        mButton = (Button) view.findViewById(R.id.start_webactivity);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mBaseActivity, WebActivity.class);
                startActivity(intent);
            }
        });
        mButton.setText("第"+getArguments().getInt("INDEX")+"页");
    }

    protected void setmCurrentFragment() {
        mCurrentFragment = this;
    }

    @Override
    public void initData() {
        SubjectPost subjectPost = new SubjectPost(new Subscriber<List<Subject>>() {
            @Override
            public void onCompleted() {
                Log.d("GRAB","onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("GRAB","onError");
            }

            @Override
            public void onNext(List<Subject> subjects) {
                Log.d("GRAB",subjects.toString());
                mTextView.setText(subjects.toString());
            }
        },true);



        HttpOnNextListener httpOnNextListener = new HttpOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                LogUtil.i(subjects.get(0).toString());
                mTextView.setText(subjects.get(0).toString());
            }
        };

        /**
         * 带有进度框的观察者
         */
        SubjectPost post = new SubjectPost(new ProgressSubscriber(httpOnNextListener,mBaseActivity),true);

        HttpManager.getInstance().doHttpDeal(post);
    }

    @Override
    public void onResume() {
        super.onResume();
        setmCurrentFragment();
    }
}
