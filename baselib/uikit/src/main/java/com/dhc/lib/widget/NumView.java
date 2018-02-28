package com.dhc.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dhc.lib.uikit.R;


/**
 * 创建者     邓浩宸
 * 创建时间   2017/10/19 12:53
 * 描述	     期数显示
 */

public class NumView extends View {


    private float mOneSize;
    private float mAllSize;
    private int rowNum = 6;
    private
    @ColorInt
    int mRectBgColor = Color.RED;
    private
    @ColorInt
    int mTextColor = Color.WHITE;
    private float mScaleX = 0;//x缩放大小
    private float mScaleY = 0;//Y缩放大小
    private
    @ColorInt
    int[] mRectBgColors = new int[]{};
    private int mRadius = 0;
    private int textSize;
    private int mSpanNum = 1;
    private int mSpanblank = 0;//空白大小

    private float rectLeft = 0;
    private float rectRight = 0;
    private float rectTop = 0;
    private float rectBottom = 0;

    private int bgPaddingLeft = 0;
    private int bgPaddingRight = 0;
    private int bgPaddingTop = 0;
    private int bgPaddingBottom = 0;
    private int gravity;
    private int desiredWidth;
    private int desiredHeight;
    private float tPrectTop = 0;
    private float tPrectBottom = 0;

    private Paint mPaint;

    float baseX = 0;
    float baseY = 0;

    private String mText = "";

    private float w;
    private float h;

    private StringBuilder tptext;//临时文字
    private float mOneTextSize;

    public NumView(Context context) {
        super(context);
        init(context);
    }


    public NumView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NumView, 0, 0);
        bgPaddingBottom = a.getDimensionPixelSize(R.styleable.NumView_nv_bgPaddingBottom, bgPaddingBottom);
        bgPaddingLeft = a.getDimensionPixelSize(R.styleable.NumView_nv_bgPaddingLeft, bgPaddingLeft);
        bgPaddingTop = a.getDimensionPixelSize(R.styleable.NumView_nv_bgPaddingTop, bgPaddingTop);
        bgPaddingRight = a.getDimensionPixelSize(R.styleable.NumView_nv_bgPaddingRight, bgPaddingRight);


        gravity = a.getInt(R.styleable.NumView_nv_gravity, 0);


        mRadius = a.getDimensionPixelSize(R.styleable.NumView_nv_radius, 0);
        mSpanblank = a.getDimensionPixelSize(R.styleable.NumView_nv_spanblank, 0);
        mSpanNum = a.getInt(R.styleable.NumView_nv_spanNum, 1);
        mTextColor = a.getColor(R.styleable.NumView_nv_text_color, Color.WHITE);
        textSize = a.getDimensionPixelSize(R.styleable.NumView_nv_text_size, sp2px(12));
        mScaleX = a.getFloat(R.styleable.NumView_nv_scaleX, 0);
        mScaleY = a.getFloat(R.styleable.NumView_nv_scaleY, 0);
        a.recycle();
        init(context);
    }


    public NumView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }


    public void setBuilder(Builder builder) {
//        if (!TextUtils.isEmpty(builder.mText))
            mText = builder.mText;
        if (builder.mSpanNum != 1)
            mSpanNum = builder.mSpanNum;
        if (builder.rowNum != 0)
            rowNum = builder.rowNum;
        if (builder.mRectBgColor != 0)
            mRectBgColor = builder.mRectBgColor;
        if (builder.mRectBgColors.length != 0)
            mRectBgColors = builder.mRectBgColors;
        if (builder.mTextColor != 0)
            mTextColor = builder.mTextColor;
        if (builder.mTextSize != 0)
            textSize = sp2px(builder.mTextSize);
        if (builder.mRadius != 0)
            mRadius = builder.mRadius;
        if (builder.mSpanblank != 0)
            mSpanblank = builder.mSpanblank;
        if (builder.bgPaddingLeft != 0)
            bgPaddingLeft = builder.bgPaddingLeft;
        if (builder.bgPaddingRight != 0)
            bgPaddingRight = builder.bgPaddingRight;
        if (builder.bgPaddingTop != 0)
            bgPaddingTop = builder.bgPaddingTop;
        if (builder.bgPaddingBottom != 0)
            bgPaddingBottom = builder.bgPaddingBottom;
        if (builder.mScaleX != 0)
            mScaleX = builder.mScaleX;
        if (builder.mScaleY != 0)
            mScaleY = builder.mScaleY;
        mPaint.setTextSize(textSize);
        initWAndH();
        requestLayout();
        invalidate();
    }

    private void initWAndH() {

        mOneTextSize = mPaint.measureText(mText, 0, mText.length()) / mText.length();
        mOneSize = mOneTextSize * 3 / 2 * mSpanNum;
        int realRowNum = (mText.length() / mSpanNum) < rowNum ? (mText.length() / mSpanNum) : rowNum;
        mAllSize = mOneSize * realRowNum + rowPadding() * realRowNum + mSpanblank * realRowNum + getScaleXs() * realRowNum;
        rectTop = 0 + mSpanblank + getPaddingTop();
        rectBottom = 0 + mPaint.descent() + linePadding() - mPaint.ascent() + mSpanblank + getPaddingTop();
        h = rectBottom - rectTop;
        if (getOneSpan() < h) {
            mAllSize = h * realRowNum + mSpanblank * realRowNum + getScaleXs() * realRowNum;
        } else {
            h = getOneSpan();
            rectBottom = 0 + h + mSpanblank + getPaddingTop();
        }
        desiredWidth = (int) mAllSize + getPaddingRight() + getPaddingLeft();
        desiredHeight = (int) ((h + mSpanblank) * getCeil(mText.length() / mSpanNum) + mSpanblank + getPaddingTop() + getPaddingBottom());


    }

    private void initDrawConfig() {
        float x;
        float y;
        int line;
        float floorNum;
        nums = mText.length() / mSpanNum;
        lefts = new float[nums];
        tops = new float[nums];
        rights = new float[nums];
        bottoms = new float[nums];
        Xs = new float[nums];
        Ys = new float[nums];
        saveStrings = new String[nums];
        for (int i = 0; i < nums; i++) {
            line = getLine(i);
            floorNum = getFloor(i);
            saveStrings[i] = mText.substring(i * mSpanNum, (i + 1) * mSpanNum);
            rectLeft = baseX + (mOneSize + rowPadding() + mSpanblank + getScaleXs()) * line + mSpanblank + getPaddingLeft();
            rectRight = rectLeft + mOneSize + rowPadding() + getScaleXs();
            w = rectRight - rectLeft;
            if (w < h) {
                rectRight += (h - w) * (line + 1);
                rectLeft += (h - w) * line;
            }
            tPrectTop = baseY + rectTop + (h + mSpanblank + getScaleYs()) * floorNum;
            tPrectBottom = baseY + rectBottom + (h + mSpanblank + getScaleYs()) * floorNum;
            tops[i] = tPrectTop;
            lefts[i] = rectLeft;
            rights[i] = rectRight;
            bottoms[i] = tPrectBottom;
            x = rectRight - (w < h ? h : w) / 2 - mOneTextSize / 2 * mSpanNum + getPaddingLeft();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            y = tPrectTop + (tPrectBottom - tPrectTop - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
            Xs[i] = x;
            Ys[i] = y;
        }
    }

    int nums;
    String[] saveStrings;
    float[] lefts;
    float[] rights;
    float[] tops;
    float[] bottoms;
    float[] Xs;
    float[] Ys;

    private float getOneSpan() {
        return mOneSize + rowPadding() + getScaleXs();
    }


    private void init(Context context) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);// 设置画笔的锯齿效果
        mPaint.setTextSize(sp2px(12));
        tptext = new StringBuilder();
    }


    public static class Builder {

        private int[] mRectBgColors = new int[]{};
        private int rowNum = 6;
        private
        @ColorInt
        int mRectBgColor = Color.RED;
        private
        @ColorInt
        int mTextColor = Color.WHITE;
        private int mRadius = 0;
        private int mSpanNum = 1;
        private int mSpanblank = 0;//空白大小
        private int mTextSize = 0;//空白大小


        private float mScaleX = 0;//x缩放大小
        private float mScaleY = 0;//Y缩放大小

        private int bgPaddingLeft = 0;
        private int bgPaddingRight = 0;
        private int bgPaddingTop = 0;
        private int bgPaddingBottom = 0;
        private String mText = "";


        /**
         * 设置一行多少个排列
         *
         * @param rowNum 数量
         * @return
         */
        public Builder rowNum(int rowNum) {
            this.rowNum = rowNum;
            return this;
        }

        /**
         * Shape背景颜色
         *
         * @param color @Color
         * @return
         */
        public Builder rectBgColor(@ColorInt int color) {
            mRectBgColor = color;
            for (int i = 0; i < mRectBgColors.length; i++) {
                mRectBgColors[i] = mRectBgColor;
            }
            return this;
        }

        /**
         * 单个Shape背景颜色
         *
         * @param color @Color
         * @return
         */
        public Builder rectBgColor(int pos, @ColorInt int color) {
            mRectBgColors[pos] = color;
            return this;
        }

        public Builder scaleX(float scaleX) {
            mScaleX = scaleX;
            return this;
        }

        public Builder scaleY(float scaleY) {
            mScaleY = scaleY;
            return this;
        }

        /**
         * 圆角
         *
         * @param radius px
         * @return
         */
        public Builder radius(int radius) {
            mRadius = radius;
            return this;
        }

        /**
         * 一个Shape包含的字符个数
         *
         * @param spanNum 数量
         * @return
         */
        public Builder spanNum(int spanNum) {
            mSpanNum = spanNum;
            return this;
        }

        /**
         * 行列之间的间距
         *
         * @param span px
         * @return
         */
        public Builder spanblank(int span) {
            mSpanblank = span;
            return this;
        }

        /**
         * 字体设置距离Shape左边距
         *
         * @param bgPaddingLeft px
         * @return
         */
        public Builder bgPaddingLeft(int bgPaddingLeft) {
            this.bgPaddingLeft = bgPaddingLeft;
            return this;
        }

        /**
         * 字体设置距离Shape右边距
         *
         * @param bgPaddingRight px
         * @return
         */
        public Builder bgPaddingRight(int bgPaddingRight) {
            this.bgPaddingRight = bgPaddingRight;
            return this;
        }

        /**
         * 字体设置距离Shape上边距
         *
         * @param bgPaddingTop px
         * @return
         */
        public Builder bgPaddingTop(int bgPaddingTop) {
            this.bgPaddingTop = bgPaddingTop;
            return this;
        }

        /**
         * 字体设置距离Shape下边距
         *
         * @param bgPaddingBottom px
         * @return
         */
        public Builder bgPaddingBottom(int bgPaddingBottom) {
            this.bgPaddingBottom = bgPaddingBottom;
            return this;
        }

        /**
         * 设置字符
         *
         * @param text String
         * @return
         */
        public Builder text(String text) {
            this.mText = text;
            mRectBgColors = new int[(mText.length() / mSpanNum)];
            for (int i = 0; i < mRectBgColors.length; i++) {
                mRectBgColors[i] = mRectBgColor;
            }
            return this;
        }

        /**
         * 设置字符 大小
         *
         * @param textSize int
         * @return
         */
        public Builder textSize(int textSize) {
            this.mTextSize = textSize;
            return this;
        }

        /**
         * Shape字体颜色
         *
         * @param color @Color
         * @return
         */
        public Builder textColor(@ColorInt int color) {
            mTextColor = color;
            return this;
        }
    }


    /**
     * sp to px, return by int
     *
     * @param spValue value in sp
     * @return value in px
     */
    public int sp2px(float spValue) {
        return (int) (sp2pxF(getContext(), spValue) + 0.5f);
    }

    /**
     * sp to px, return by float
     *
     * @param context Context
     * @param spValue value in sp
     * @return value in px
     */
    public float sp2pxF(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }
        if (gravity == 1 || gravity == 2) {
            if (width > desiredWidth)
                baseX = (width - desiredWidth) / 2f;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }
        if (gravity == 1 || gravity == 3) {
            if (height > desiredHeight) {
                baseY = (height - desiredHeight) / 2f;
            }
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);


        initDrawConfig();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RectF oval = new RectF();
        for (int i = 0; i < nums; i++) {
            mPaint.setColor(mRectBgColors[i]);//设置背景颜色
            oval.set(lefts[i], tops[i], rights[i], bottoms[i]);
            canvas.drawRoundRect(oval, mRadius, mRadius, mPaint);
            mPaint.setColor(mTextColor);
            canvas.drawText(saveStrings[i], Xs[i], Ys[i], mPaint);//绘制文字
        }
    }

    private float getScaleXs() {
        return -mOneSize * mScaleX / mSpanNum;
    }

    private int linePadding() {
        return (int) (bgPaddingTop + bgPaddingBottom);
    }

    private float getScaleYs() {
        return -mOneSize * mScaleY / mSpanNum;
    }

    private int rowPadding() {
        return (int) (bgPaddingLeft + bgPaddingRight);
    }

    private int getLine(int i) {
        return i % rowNum;
    }

    private int getFloor(int i) {
        return (int) Math.floor((double) i / (double) rowNum);
    }

    private int getCeil(int i) {
        return (int) Math.ceil((double) i / (double) rowNum);
    }
}
