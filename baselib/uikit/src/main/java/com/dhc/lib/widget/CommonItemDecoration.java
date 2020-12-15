package com.dhc.lib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 创建者：dhc
 * 时间 ：2017/10/18   16:45
 * 描述 ：线性布局管理器通用的recyclerView分割线
 */
public class CommonItemDecoration extends RecyclerView.ItemDecoration {
    public static final int HORIZONTAL = LinearLayout.HORIZONTAL;
    public static final int VERTICAL = LinearLayout.VERTICAL;

    private Context mContext;

    /**
     *  分割线高度
     */
    private int mDividerHeight;

    /**
     * 分割线颜色
     */
    private int mDividerColor;

    /**
     *   是否显示最后一行或最后一列的分割线
     */
    private boolean isShowLastLine;

    /**
     * 方向
     */
    private int mOrientation;

    private Paint mPaint;

    public CommonItemDecoration(Context context) {
        this.mContext = context;
        this.mDividerColor = Color.parseColor("#eeeded");
        this.isShowLastLine = false;
        this.mDividerHeight = 1;
        this.mOrientation = VERTICAL;

        initPaint();
    }

    public CommonItemDecoration(Context mContext, int mDividerHeight, int mDividerColor,
                                boolean isShowLastLine, int mOrientation) {
        this.mContext = mContext;
        this.mDividerHeight = mDividerHeight;
        this.mDividerColor = mDividerColor;
        this.isShowLastLine = isShowLastLine;
        this.mOrientation = mOrientation;

        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mDividerColor);
    }

    /**
     * 设置分割线方向
     * @param orientation 方向
     */
    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL && orientation != VERTICAL) {
            mOrientation = VERTICAL;
            return;
        }
        mOrientation = orientation;
    }

    /**
     * 设置分割线的高度
     * @param dividerHeight 高度
     */
    public void setDividerHeight(int dividerHeight){
        if (dividerHeight <= 0){
            mDividerHeight = 1;
            return;
        }

        this.mDividerHeight = dividerHeight;
    }

    /**
     * 设置最后一行数据的分割线是否显示
     * @param isShow 是否显示
     */
    public void setShowLastLine(boolean isShow){
        this.isShowLastLine = isShow;
    }

    /**
     * 设置分割线的颜色
     * @param dividerColor
     */
    public void setDividerColor(int dividerColor){
        if (dividerColor == -1){
            this.mDividerColor =  Color.parseColor("#eeeded");
            return;
        }
        this.mDividerColor = dividerColor;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getLayoutManager() == null) {
            return;
        }

        if (mOrientation == VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }
    }

    @SuppressLint("NewApi")
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final float left;
        final float right;
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();
        if (isShowLastLine){
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final float bottom = mDividerHeight + child.getBottom();
                canvas.drawRect(left,child.getBottom(),right,bottom,mPaint);
            }
        }else {
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final float bottom = mDividerHeight + child.getBottom();
                canvas.drawRect(left,child.getBottom(),right,bottom,mPaint);
            }
        }
        canvas.restore();
    }

    @SuppressLint("NewApi")
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int top;
        final int bottom;
        if (parent.getClipToPadding()) {
            top = parent.getPaddingTop();
            bottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.clipRect(parent.getPaddingLeft(), top,
                    parent.getWidth() - parent.getPaddingRight(), bottom);
        } else {
            top = 0;
            bottom = parent.getHeight();
        }

        final int childCount = parent.getChildCount();
        if (isShowLastLine){
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final float right = mDividerHeight + child.getRight();
                final float left = child.getLeft();
                canvas.drawRect(c`hild.getRight(),top,right,bottom,mPaint);
            }
        }else {
            for (int i = 0; i < childCount - 1; i++) {
                final View child = parent.getChildAt(i);
                final float right = mDividerHeight + child.getRight();
                final float left = child.getLeft();
                canvas.drawRect(child.getRight(),top,right,bottom,mPaint);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mDividerHeight);
        } else {
            outRect.set(0, 0, mDividerHeight, 0);
        }
    }
}
