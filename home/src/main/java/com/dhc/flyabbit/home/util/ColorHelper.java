package com.dhc.flyabbit.home.util;

import android.animation.ArgbEvaluator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.dhc.flyabbit.home.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.lasingwu.baselibrary.ImageLoaderManager;

/**
 * @author 邓浩宸
 * @date 2017/8/26 17:39
 * @description TODO
 */
public class ColorHelper {
    public String[] mImages = {
    };


   public int[] mColors = {

    };

    private static final float FACTOR = 0.1f;

    private final ImageView mTargetImage; // 原始图片
    private final ImageView mOutgoingImage; // 渐变图片

    private int mActualStart; // 实际起始位置
    ArgbEvaluator evaluator;
    private int mStart;
    private int mEnd;

    private boolean isSkip = false;//是否跳页
    CollapsingToolbarLayout collapsingToolbar;

    public ColorHelper(CollapsingToolbarLayout collapsingToolbar, ImageView targetImage, ImageView outgoingImage, int[] colors) {
        this.collapsingToolbar = collapsingToolbar;
        mColors = colors;
        mTargetImage = targetImage;
        mOutgoingImage = outgoingImage;
        if (mImages.length != 0) {
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
                    (mTargetImage, mImages[0]));
        } else {
            mTargetImage.setBackgroundColor(mColors[0]);
        }
        collapsingToolbar.setContentScrimColor(mColors[0]);
        collapsingToolbar.setStatusBarScrimColor(mColors[0]);
        evaluator = new ArgbEvaluator();
    }


    public void setImages(String[] images) {
        mImages = images;
//        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
//                (mTargetImage, mImages[mActualStart]));
        ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
                (mTargetImage, mImages[mActualStart]));
    }


    public void setColors(int[] colors) {
        mColors = colors;
    }

    /**
     * 启动动画, 之后选择向前或向后滑动
     *
     * @param startPosition 起始位置
     * @param endPosition   终止位置
     */
    public void start(int startPosition, int endPosition) {
        if (Math.abs(endPosition - startPosition) > 1) {
            isSkip = true;
        }
        mActualStart = startPosition;
        Log.e("DEBUG", "startPosition: " + startPosition + ", endPosition: " + endPosition);
        // 终止位置的图片

        //@DrawableRes int incomeId = ids[endPosition % ids.length];

        // 原始图片
        mOutgoingImage.setImageDrawable(mTargetImage.getDrawable()); // 原始的图片

        // 起始图片
        mOutgoingImage.setTranslationX(0f);

        mOutgoingImage.setVisibility(View.VISIBLE);
        mOutgoingImage.setAlpha(1.0f);

        // 目标图片
        //   mTargetImage.setImageResource(incomeId);
        if (mImages.length != 0) {
            ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
                    (mTargetImage, mImages[endPosition]));
        } else {
            mTargetImage.setBackgroundColor(mColors[endPosition]);
        }
        mStart = Math.min(startPosition, endPosition);
        mEnd = Math.max(startPosition, endPosition);
    }

    /**
     * 滑动结束的动画效果
     *
     * @param endPosition 滑动位置
     */
    public void end(int endPosition) {
        isSkip = false;
        //@DrawableRes int incomeId = ids[endPosition % ids.length];
        mTargetImage.setTranslationX(0f);

        // 设置原始图片
        if (endPosition == mActualStart) {
            mTargetImage.setImageDrawable(mOutgoingImage.getDrawable());
        } else {
            if (mImages.length != 0) {
                ImageLoaderManager.getInstance().showImage(ImageLoaderManager.getDefaultOptions
                        (mTargetImage, mImages[endPosition]));
            } else {
                mTargetImage.setBackgroundColor(mColors[endPosition]);
            }
            collapsingToolbar.setContentScrimColor(mColors[endPosition]);
            collapsingToolbar.setStatusBarScrimColor(mColors[endPosition]);
            //mTargetImage.setImageResource(incomeId);
            mTargetImage.setAlpha(1f);
            mOutgoingImage.setVisibility(View.GONE);
        }
    }

    // 向前滚动, 比如0->1, offset滚动的距离(0->1), 目标渐渐淡出
    public void forward(int position, float positionOffset) {
        if (isSkip)
            return;
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX(-positionOffset * (FACTOR * width));
        mTargetImage.setTranslationX((1 - positionOffset) * (FACTOR * width));
        int color = (int) evaluator.evaluate(positionOffset, mColors[position], mColors[position + 1]);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        mTargetImage.setAlpha(positionOffset);
    }

    public void backwards(int position, float positionOffset) {
        if (isSkip)
            return;
        // Log.e("DEBUG-WCL", "backwards-positionOffset: " + positionOffset);
        int width = mTargetImage.getWidth();
        mOutgoingImage.setTranslationX((1 - positionOffset) * (FACTOR * width));
        mTargetImage.setTranslationX(-(positionOffset) * (FACTOR * width));

        int color = (int) evaluator.evaluate(1 - positionOffset, mColors[position + 1], mColors[position]);
        collapsingToolbar.setContentScrimColor(color);
        collapsingToolbar.setStatusBarScrimColor(color);
        mTargetImage.setAlpha(1 - positionOffset);
    }


    // 判断停止
    public boolean isWithin(int position) {
        return position >= mStart && position < mEnd;
    }
}
