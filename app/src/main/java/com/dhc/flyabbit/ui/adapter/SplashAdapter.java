package com.dhc.flyabbit.ui.adapter;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/7/17 15:24
 * 描述 ：导航页面
 */
public class SplashAdapter extends PagerAdapter {
    int[] guides;
    public SplashAdapter(int[] guides) {
        this.guides = guides;
    }

    @Override
    public int getCount() {
        return guides.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView view = new ImageView(container.getContext());
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setImageResource(guides[position]);
        container.addView(view);

        return view;
    }
}
