package com.lzh.administrator.androidframe.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lzh27651 on 2016/9/21.
 * 标签流式view
 */

public class FlowLayout extends ViewGroup{

    //存储所有子View
    private List<List<View>> mAllChildViews = new ArrayList<>();
    //每一行的高度
    private List<Integer> mLineHeight = new ArrayList<>();
    private MotionEvent mMotionEvent;
    private OnTagClickListener mOnTagClickListener;
    private int mLastSelectedPosition = -1;

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FlowLayout(Context context) {
        this(context,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        /**
         * 父控件传进来的宽度、高度以及对应的测量模式
         */
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        /**
         * 如果当前viewGroup宽高为wrap_content情况下，用来记录自己测量的总宽和总高
         */
        int width = 0;
        int height = 0;

        /**
         * 记录每一行的宽高
         */
        int lineWidth = 0;
        int lineHeight = 0;

        /**
         * 获得子view的个数
         */
        int childCount = getChildCount();

        for(int i=0;i<childCount;i++){
            /**
             * 通过索引拿到每一个子view
             */
            View child = getChildAt(i);
            /**
             * 测量子view的宽和高
             */
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            /**
             * 子View.getLayoutParams 得到的LayoutParams对应的就是 子View所在的父控件的LayoutParams
            * 即得到ViewGroup的LayoutParams
             */
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            /**
             * 子view占据的宽度
             */
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            /**
             * 子view占据的高度
             */
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            /**
             * 换行判断,如果这一行之前的总宽度，即行宽lineWidth，加上新的子view的宽度，大于父控件传过来的宽度，即换行
             */
            if(lineWidth + childWidth > sizeWidth - getPaddingLeft() - getPaddingRight()){
                /**
                 * 对比得到最大的宽度
                 */
                width = Math.max(width,lineWidth);
                /**
                 * 重置lineWidth
                 */
                lineWidth = childWidth;
                /**
                 * 记录行高
                 */
                lineHeight = childHeight;
                height += lineHeight;

            }else{
                /**
                 * 未换行
                 */
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }

            /**
             * 特殊处理最后一个view
             */
            if(i==childCount-1){
                width = Math.max(width,lineWidth);
                height += lineHeight;
            }
        }

        /**
         * 设置最终的宽和高
         */
        setMeasuredDimension(modeWidth==MeasureSpec.EXACTLY?sizeWidth:width,modeHeight==MeasureSpec.EXACTLY?sizeHeight:height);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllChildViews.clear();
        mLineHeight.clear();

        /**
         * 当前viewGroup的宽度
         */
        int width = getWidth();

        int lineWidth = 0;
        int lineHeight = 0;
        //记录当前行的view
        List<View> lineViews = new ArrayList<View>();
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            /**
             * 通过索引拿到每一个子view
             */
            View child = getChildAt(i);
            /**
             * 子View.getLayoutParams 得到的LayoutParams对应的就是 子View所在的父控件的LayoutParams
             * 即得到ViewGroup的LayoutParams
             */
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            /**
             * 子view占据的宽度
             */
            int childWidth = child.getMeasuredWidth();
            /**
             * 子view占据的高度
             */
            int childHeight = child.getMeasuredHeight();

            if(lineWidth+childWidth+lp.leftMargin+lp.rightMargin>width){
                //记录每一行的行高
                mLineHeight.add(lineHeight);
                //记录每行的views
                mAllChildViews.add(lineViews);
                //重置
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                lineViews = new ArrayList<>();
            }

            /**
             * 每一行的行宽高
             */
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight,childHeight + lp.topMargin + lp.bottomMargin);
            lineViews.add(child);
        }

        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllChildViews.add(lineViews);

        /**
         * 设置子view的位置
         */
        int left = 0;
        int top = 0;
        //获取行数
        int lineCount = mAllChildViews.size();
        for(int i=0;i<lineCount;i++){
            /**
             * 设置当前行的views和高度
             */
            lineHeight = mLineHeight.get(i);
            lineViews = mAllChildViews.get(i);
            for(int j=0;j<lineViews.size();j++){
                View child = lineViews.get(j);
                if(child.getVisibility()==GONE){
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int cleft = left + lp.leftMargin;
                int ctop = top + lp.topMargin;
                int cright = cleft + child.getMeasuredWidth();
                int cbottom = ctop + child.getMeasuredHeight();
                //布局子view
                child.layout(cleft,ctop,cright,cbottom);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(),attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP)
        {
            mMotionEvent = MotionEvent.obtain(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if (mMotionEvent == null)
            return super.performClick();

        int x = (int) mMotionEvent.getX();
        int y = (int) mMotionEvent.getY();
        mMotionEvent = null;

        View view = findChild(x, y);
        int current = findPosByView(view);
        if(view!=null){
            doSelect(view, current);
            if(mOnTagClickListener!=null){
                boolean re = mOnTagClickListener.onTagClick(view, current,mLastSelectedPosition,this);
                if(mLastSelectedPosition!=current){
                    mLastSelectedPosition = current;
                }
                return re;

            }
        }
        return super.performClick();
    }

    private void doSelect(View view, int pos) {
        view.setSelected(true);
    }

    private int findPosByView(View view) {
        final int count = getChildCount();
        for(int j=0;j<count;j++){
            if(view==getChildAt(j)){
                return j;
            }
        }
        return -1;
    }

    private View findChild(int x, int y) {
        final int count = getChildCount();
        for(int i=0;i<count;i++){
            View view = getChildAt(i);
            if(view.getVisibility()==GONE){
                continue;
            }
            Rect outRect = new Rect();
            view.getHitRect(outRect);
            if(outRect.contains(x,y)){
                return view;
            }
        }
        return null;
    }

    public interface OnTagClickListener{
        public boolean onTagClick(View view, int currentPosition,int lastPostion, FlowLayout parent);
    }

    public void setmOnTagClickListener(OnTagClickListener mOnTagClickListener) {
        this.mOnTagClickListener = mOnTagClickListener;
        if(mOnTagClickListener!=null){
            setClickable(true);
        }
    }
}
