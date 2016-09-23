package com.lzh.administrator.androidframe.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lzh27651 on 2016/9/19.
 */

public class SideBar extends View {

    private String[] alphabet = {
            "当前","历史","热门",
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z", "#"
    };

    private Paint mPaint = new Paint();

    private int currentChoosenAlphabetIndex = -1;

    private onLetterTouchedChangeListener onLetterTouchedChangeListener = null;

    private TextView mTextView;

    public SideBar(Context context) {
        this(context,null,0);
    }

    public SideBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //得到sidebar的高度
        int viewHeight = getHeight();
        //得到sidebar的宽度
        int viewWidth = getWidth();
        //得到每个字母的高度
        int heightPerAlphabet = viewHeight / alphabet.length;

        //绘制每个字母
        for(int i=0;i<alphabet.length;i++){
            //设置颜色
            mPaint.setColor(Color.rgb(153, 153, 153));
            //设置字体
            mPaint.setTypeface(Typeface.DEFAULT_BOLD);
            //字体大小
            mPaint.setTextSize(20);
            //设置抗锯齿
            mPaint.setAntiAlias(true);

            /**
             * 如果当前的字母索引被手指触摸到，那么字体颜色要进行区分
             */
            if(currentChoosenAlphabetIndex == i){
                //设置颜色
                mPaint.setColor(Color.parseColor("#3399ff"));
                //字体加粗
                mPaint.setFakeBoldText(true);
            }

            /**
             * 绘制字体，需要制定绘制的x,y轴坐标
             */
            float xPos = viewWidth/2 - mPaint.measureText(alphabet[i])/2;
            float yPos = heightPerAlphabet * i + heightPerAlphabet;
            canvas.drawText(alphabet[i],xPos,yPos,mPaint);

            //重置画笔，准备绘制下一个字母
            mPaint.reset();
        }
    }

    /**
     * 当手指触摸的字母索引发生变化时，调用该回调接口
     */
    public interface onLetterTouchedChangeListener{
        public void onTouchedLetterChange(String letterTouched);
    }

    public SideBar.onLetterTouchedChangeListener getOnLetterTouchedChangeListener() {
        return onLetterTouchedChangeListener;
    }

    public void setOnLetterTouchedChangeListener(SideBar.onLetterTouchedChangeListener onLetterTouchedChangeListener) {
        this.onLetterTouchedChangeListener = onLetterTouchedChangeListener;
    }

    public void setmTextView(TextView mTextView) {
        this.mTextView = mTextView;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //触摸事件的代码
        int action = event.getAction();
        //手指触摸的y坐标
        float touchYPos = event.getY();

        // 因为currentChoosenAlphabetIndex会不断发生变化，所以用一个变量存储起来
        int preChoosenAlphabetIndex = currentChoosenAlphabetIndex;

        onLetterTouchedChangeListener listener = getOnLetterTouchedChangeListener();
        // 比例 = 手指触摸点在屏幕的y轴坐标 / SideBar的高度
        // 触摸点的索引 = 比例 * 字母索引数组的长度
        int currentTouchIndex = (int) (touchYPos / getHeight() * alphabet.length);

        if(MotionEvent.ACTION_UP == action){
//            setBackgroundResource(R.drawable.shape_side_bar_normal_bg);
            currentChoosenAlphabetIndex = -1;
            if(mTextView!=null){
                mTextView.setVisibility(INVISIBLE);
            }
            invalidate();
        }else{
//            setBackgroundResource(R.drawable.shape_side_bar_normal_bg);

            if(currentTouchIndex != preChoosenAlphabetIndex){
                if(currentTouchIndex>=0 && currentTouchIndex<alphabet.length){
                    if(listener!=null){
                        listener.onTouchedLetterChange(alphabet[currentTouchIndex]);
                    }
                    if(mTextView!=null){
                        mTextView.setText(alphabet[currentTouchIndex]);
                        mTextView.setVisibility(VISIBLE);
                    }
                    currentChoosenAlphabetIndex = currentTouchIndex;
                    invalidate();
                }
            }
        }

        return true;
    }
}
