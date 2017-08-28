package com.dhc.flyabbit.home.ui;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.dhc.flyabbit.home.util.ColorHelper;

/**
 * @author 邓浩宸
 * @date 2017/8/26 17:39
 * @description ViewPager滑动页面监听
 */
public class PagerChangeListener implements ViewPager.OnPageChangeListener {
    private ColorHelper mColorHelper;

    private int mCurrentPosition;

    private int mFinalPosition;

    private boolean mIsScrolling = false;

    private  ViewPager mViewPager;

    private  float mRate=1;

    public PagerChangeListener(ColorHelper colorHelper, ViewPager viewPager) {
        mColorHelper = colorHelper;
        mViewPager=viewPager;
    }

    public static PagerChangeListener newInstance(CollapsingToolbarLayout collapsingToolbar, ImageView originImage, ImageView outgoingImage, ViewPager viewPager, int[] colors) {
        ColorHelper colorHelper = new ColorHelper(collapsingToolbar, originImage, outgoingImage,colors);
        return new PagerChangeListener(colorHelper,viewPager);
    }
    public  void initImages(String[] images){
        mColorHelper.setImages(images);
    }

    public void initColors(int[] colors) {
        mColorHelper.setColors(colors);
    }


    public void setRate(float rate) {
        mRate = rate;
    }

    /**
     * 滑动监听
     *
     * @param position             当前位置
     * @param positionOffset       偏移[当前值+-1]
     * @param positionOffsetPixels 偏移像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        // 以前滑动, 现在终止
        if (isFinishedScrolling(position, positionOffset)) {
            finishScroll(position);
        }

        // 判断前后滑动
        if (isStartingScrollToPrevious(position, positionOffset)) {
            startScroll(position);
        } else if (isStartingScrollToNext(position, positionOffset)) {
            startScroll(position + 1); // 向后滚动需要加1
        }

        // 向后滚动
        if (isScrollingToNext(position, positionOffset)) {
            mColorHelper.forward(position, positionOffset);
        } else if (isScrollingToPrevious(position, positionOffset)) { // 向前滚动
            mColorHelper.backwards(position, positionOffset);
        }
        ArgbEvaluator evaluator = new ArgbEvaluator(); // ARGB求值器
        int evaluate; // 初始默认颜色（透明白）
        int bcolor;
        int acolor;
        if (position == 0) {
            bcolor = (int) evaluator.evaluate(1 - mRate, Color.parseColor("#ffffff"), mColorHelper.mColors[0]);
            acolor = (int) evaluator.evaluate(1 - mRate, Color.parseColor("#ffffff"),mColorHelper.mColors[1]);
            evaluate = (Integer) evaluator.evaluate(positionOffset,bcolor, acolor); // 根据positionOffset和第0页~第1页的颜色转换范围取颜色值
        }else if(position == 1){
           bcolor = (int) evaluator.evaluate(1 - mRate, Color.parseColor("#ffffff"), mColorHelper.mColors[1]);
            acolor = (int) evaluator.evaluate(1 - mRate, Color.parseColor("#ffffff"),  mColorHelper.mColors[2]);
            evaluate = (Integer) evaluator.evaluate(positionOffset, bcolor,acolor); // 根据positionOffset和第1页~第2页的颜色转换范围取颜色值
        } else{
            acolor = (int) evaluator.evaluate(1 - mRate, Color.parseColor("#ffffff"),mColorHelper.mColors[2]);
            evaluate =acolor; // 最终第3页的颜色
        }
        (mViewPager).setBackgroundColor(evaluate); // 为ViewPager的父容器设置背景色
    }

    /**
     * 终止滑动
     * 滑动 && [偏移是0&&滑动终点] || 动画之中
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 终止滑动
     */
    public boolean isFinishedScrolling(int position, float positionOffset) {
        return mIsScrolling && (positionOffset == 0f && position == mFinalPosition) || !mColorHelper.isWithin(position);
    }

    /**
     * 从静止到开始滑动, 下一个
     * 未滑动 && 位置是当前位置 && 偏移量不是0
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToNext(int position, float positionOffset) {
        return !mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 从静止到开始滑动, 前一个[position会-1]
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToPrevious(int position, float positionOffset) {
        return !mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向后
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToNext(int position, float positionOffset) {
        return mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向前
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToPrevious(int position, float positionOffset) {
        return mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滑动
     * 滚动开始, 结束位置是position[前滚时position会自动减一], 动画从当前位置到结束位置.
     *
     * @param position 滚动结束之后的位置
     */
    private void startScroll(int position) {
        mIsScrolling = true;
        mFinalPosition = position;

        // 开始滚动动画
        mColorHelper.start(mCurrentPosition, position);
    }

    /**
     * 如果正在滚动, 结束时, 固定position位置, 停止滚动, 调动截止动画
     *
     * @param position 位置
     */
    private void finishScroll(int position) {
        if (mIsScrolling) {
            mCurrentPosition = position;
            mIsScrolling = false;
            mColorHelper.end(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //NO-OP
    }

    @Override
    public void onPageSelected(int position) {
        if (!mIsScrolling) {
            mIsScrolling = true;
            mFinalPosition = position;
            mColorHelper.start(mCurrentPosition, position);
        }
    }
}
