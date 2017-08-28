package com.dhc.flyabbit.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dhc.flyabbit.R;
import com.dhc.flyabbit.ui.adapter.SplashAdapter;
import com.dhc.library.OnShowHomeListener;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.data.SPHelper;
import com.dhc.library.data.net.Constants;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.sys.ScreenUtil;

/**
 * @author dhc
 * @time 2017/7/18 19:46
 * @desc TODO
 */

public class SplashFrament extends XDaggerFragment implements View.OnClickListener {
    private boolean mIsShowGuid;
    private FrameLayout mLinearLayout;
    private TextView mTvJoin;

    private int[] guides = {R.mipmap.page1, R.mipmap.page2, R.mipmap.page3, R.mipmap.page4};
    private ViewPager viewPager;

    @Override
    public void initInject(Bundle savedInstanceState) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    public void showGuid() {
        viewPager = $(R.id.viewPager);
        mTvJoin = $(R.id.iv_join);
        final ViewGroup indicator = $(R.id.actions_page_indicator);
        indicator.setVisibility(View.VISIBLE);
        setIndicator(indicator, guides.length, 0);
        mTvJoin.setVisibility(View.GONE);
        if (viewPager != null) {

            viewPager.setAdapter(new SplashAdapter(guides));

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (position == guides.length - 1) {
                        mTvJoin.setVisibility(View.VISIBLE);
                        indicator.setVisibility(View.GONE);
                    } else {
                        mTvJoin.setVisibility(View.GONE);
                        indicator.setVisibility(View.VISIBLE);
                    }
                    setIndicator(indicator, guides.length, position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        mTvJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayout.setVisibility(View.GONE);
                SPHelper.put(AppContext.get(), Constants.VERSION, AppUtil.getVersionName(AppContext.get()), SPHelper.VERSION_FILE_NAME);
                ((OnShowHomeListener)_mActivity
                ).showHome();
            }
        });


    }


    /**
     * 设置页码
     */
    private void setIndicator(ViewGroup indicator, int total, int current) {
        if (total <= 1) {
            indicator.removeAllViews();
        } else {
            indicator.removeAllViews();
            for (int i = 0; i < total; i++) {
                ImageView imgCur = new ImageView(indicator.getContext());
                imgCur.setId(i);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        ScreenUtil.dip2px(7),
                        ScreenUtil.dip2px(7));
                layoutParams.leftMargin = ScreenUtil.dip2px(5);
                layoutParams.rightMargin = ScreenUtil.dip2px(5);
                imgCur.setLayoutParams(layoutParams);
                // 判断当前页码来更新
                if (i == current) {
                    imgCur.setBackgroundResource(R.mipmap.ic_blue_yuan_dian);
                } else {
                    imgCur.setBackgroundResource(R.mipmap.ic_white_yuan_dian);
                }
                indicator.addView(imgCur);
            }
        }
    }

    @Override
    protected void initEventAndData(View view) {
        initView();
        if (!SPHelper.get(AppContext.get(), Constants.VERSION, "", SPHelper.VERSION_FILE_NAME).equals(AppUtil.getVersionName(AppContext.get()))) {
            mIsShowGuid = true;
        } else {
            mIsShowGuid = false;
        }
        if (mIsShowGuid) {
            mLinearLayout.setVisibility(View.VISIBLE);
            showGuid();
        } else {
            mLinearLayout.setVisibility(View.GONE);
        }

    }



    private void initView() {
        mLinearLayout= $(R.id.actionsLayout);
    }

    @Override
    public void onClick(View view) {

    }
}
