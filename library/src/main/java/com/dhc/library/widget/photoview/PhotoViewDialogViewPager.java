package com.dhc.library.widget.photoview;

/**
 * @author YK
 * @time 2017/6/22 19:29
 * @desc TODO 为了解决photoview嵌套在部分父控件时闪退的bug，github上提供的解决方案
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
public class PhotoViewDialogViewPager extends ViewPager {
    public PhotoViewDialogViewPager(Context context) {
        this(context,null);
    }

    public PhotoViewDialogViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            //uncomment if you really want to see these errors
            //e.printStackTrace();
            return false;
        }
    }
}
