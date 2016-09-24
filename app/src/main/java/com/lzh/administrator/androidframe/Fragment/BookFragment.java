package com.lzh.administrator.androidframe.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.lzh.administrator.androidframe.Activity.StationSelectionActivity;
import com.lzh.administrator.androidframe.Base.BaseFragment;
import com.lzh.administrator.androidframe.Model.TrainStationBean;
import com.lzh.administrator.androidframe.R;
import com.lzh.administrator.androidframe.Utils.LogUtil;
import com.lzh.administrator.androidframe.constant.Keys;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lzh27651 on 2016/9/13.
 */

public class BookFragment extends BaseFragment{

    private View mMain_starting;
    private View mMain_destination;
    private View mMain_starting_date;
    private View mMain_student_ticket;

    private TextView mMain_starting_display;

    private TextView mMain_destination_display;

    private TextView mMain_starting_date_display;

    private SwitchButton mMain_student_ticket_switch;

    private Button mMain_query;

    private ImageView mMain_change;

    private TrainStationBean mFrom;
    private TrainStationBean mTo;


    public static BookFragment newInstance(){
        return new BookFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        (mBaseActivity).setTitle(getResources().getString(R.string.main_title));

        mMain_starting = view.findViewById(R.id.main_starting);
        mMain_destination = view.findViewById(R.id.main_destination);
        mMain_starting_date = view.findViewById(R.id.main_starting_date);
        mMain_student_ticket = view.findViewById(R.id.main_student_ticket);

        /**
         * 设置出发地item
         */
        mMain_starting_display = (TextView) mMain_starting.findViewById(R.id.main_item_display);
        mMain_starting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StationSelectionActivity.class);
                if(mFrom!=null){
                    intent.putExtra(Keys.FROM_SELECTED_STATION,mFrom);
                }
                startActivityForResult(intent,Keys.FROM_STATION);
            }
        });

        /**
         * 设置目的地item
         */
        mMain_destination_display = (TextView) mMain_destination.findViewById(R.id.main_item_display);
        mMain_destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), StationSelectionActivity.class);
                if(mTo!=null){
                    intent.putExtra(Keys.TO_SELECTED_STATION,mTo);
                }
                startActivityForResult(intent,Keys.TO_STATION);
            }
        });
        /**
         * 设置出发日期item
         */
        mMain_starting_date_display = (TextView) mMain_starting_date.findViewById(R.id.main_item_display);

        /**
         * 设置学生票item
         */
        mMain_student_ticket_switch = (SwitchButton) mMain_student_ticket.findViewById(R.id.main_item_switch);

        /**
         * 查询按钮
         */
        mMain_query = (Button) view.findViewById(R.id.main_query_btn);
        mMain_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("mMain_query clicked!");
            }
        });

        mMain_change = (ImageView) view.findViewById(R.id.main_change);
        mMain_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.d("mMain_change clicked!");
            }
        });
    }

    protected void setmCurrentFragment() {
        mCurrentFragment = this;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        setmCurrentFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Keys.FROM_STATION:
                if(resultCode==RESULT_OK){
                    mFrom = data.getParcelableExtra(Keys.FROM_SELECTED_STATION);
                    mMain_starting_display.setText(mFrom.getmName());
                }
                break;
            case Keys.TO_STATION:
                if(resultCode==RESULT_OK){
                    mTo = data.getParcelableExtra(Keys.TO_SELECTED_STATION);
                    mMain_destination_display.setText(mTo.getmName());
                }
                break;
        }
    }
}
