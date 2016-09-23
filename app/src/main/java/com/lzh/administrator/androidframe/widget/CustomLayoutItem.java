package com.lzh.administrator.androidframe.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.lzh.administrator.androidframe.R;

/**
 * Created by lzh27651 on 2016/9/18.
 */

public class CustomLayoutItem extends RelativeLayout{

    private RelativeLayout mView;

    private Bitmap mIcon;
    private String mName;
    private String mDisplay;
    private int mDisplay_visibility;
    private int mForward_visibility;
    private int mSwitch_visibility;
    private int mLine_visibility;

    private ImageView mItem_icon;
    private TextView mItem_name;
    private TextView mItem_display;
    private ImageView mItem_forward;
    private SwitchButton mItem_switch;
    private TextView mItem_line;

    public CustomLayoutItem(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public CustomLayoutItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_item,this);
        mItem_icon = (ImageView) mView.findViewById(R.id.main_item_icon);
        mItem_name = (TextView) mView.findViewById(R.id.main_item_name);
        mItem_display = (TextView) mView.findViewById(R.id.main_item_display);
        mItem_forward = (ImageView) mView.findViewById(R.id.main_item_forward);
        mItem_line = (TextView) mView.findViewById(R.id.main_item_line);
        mItem_switch = (SwitchButton) mView.findViewById(R.id.main_item_switch);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomLayoutItem,defStyleAttr,0);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.CustomLayoutItem_item_icon:
                    mIcon = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    mItem_icon.setImageBitmap(mIcon);
                    break;
                case R.styleable.CustomLayoutItem_item_name:
                    mName = a.getString(attr);
                    mItem_name.setText(mName);
                    break;
                case R.styleable.CustomLayoutItem_item_display:
                    mDisplay = a.getString(attr);
                    mItem_display.setText(mDisplay);
                    break;
                case R.styleable.CustomLayoutItem_item_display_visibility:
                    mDisplay_visibility = a.getInt(attr,0);
                    if(mDisplay_visibility==View.GONE){
                        mItem_display.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomLayoutItem_item_forward_visibility:
                    mForward_visibility = a.getInt(attr,0);
                    if(mForward_visibility==View.GONE){
                        mItem_forward.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomLayoutItem_item_switch_visibility:
                    mSwitch_visibility = a.getInt(attr,0);
                    if(mSwitch_visibility==View.VISIBLE){
                        mItem_switch.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.styleable.CustomLayoutItem_item_line_visibility:
                    mLine_visibility = a.getInt(attr,0);
                    if(mLine_visibility==View.INVISIBLE){
                        mItem_line.setVisibility(View.INVISIBLE);
                    }
                    break;
            }
        }
        a.recycle();
    }
}
