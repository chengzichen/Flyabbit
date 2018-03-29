package com.dhc.flyabbit.home.ui;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.home.R;
import com.dhc.flyabbit.home.di.HomeDiHelper;
import com.dhc.flyabbit.home.presenter.TopGirlPresenter;
import com.dhc.flyabbit.home.presenter.contract.ITopGirlContract;
import com.dhc.flyabbit.home.ui.adapter.ViewPagerAdapter;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.utils.AppManager;
import com.dhc.library.utils.sys.ScreenUtil;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */
@Route(path = "/home/HomeFragment")
public class HomeFragment extends XDaggerFragment<TopGirlPresenter> implements ITopGirlContract.IView {
    private TabLayout mToolbarTab;
    private ViewPager mMainVpContainer;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout mRootLayout;
    private ImageView mToolbarIvTarget;
    private ImageView mToolbarIvOutgoing;
    private AppBarLayout mAppbarlayout;
    private List<GankPageFragment> mFragments = new ArrayList<>();
    ViewPagerAdapter vpAdapter;
    PagerChangeListener mPagerChangeListener;
    private String tabTitles[] = new String[]{TECH_ANDROID, TECH_IOS, TECH_WEB};
    public static final String TECH_ANDROID = "Android";
    public static final String TECH_IOS = "iOS";
    public static final String TECH_WEB = "前端";

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    int[] mColors = {
            Color.parseColor("#6385a7"),
            Color.parseColor("#d9d0c7"),
            Color.parseColor("#d79691")
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_main;
    }

    @Override
    protected void initEventAndData(View view) {
        initView();
        initData();
        initListener();
        initAdapter();
    }

    private void initData() {
        mPresenter.getRandomGirl();
    }

    private void initAdapter() {
        mFragments.add(GankPageFragment.newInstance(TECH_ANDROID));
        mFragments.add(GankPageFragment.newInstance(TECH_IOS));
        mFragments.add(GankPageFragment.newInstance(TECH_WEB));
        vpAdapter = ViewPagerAdapter.newInstance(getChildFragmentManager(), mFragments, tabTitles);
        mMainVpContainer.setAdapter(vpAdapter);
        mMainVpContainer.setOffscreenPageLimit(3);
        mToolbarTab.setupWithViewPager(mMainVpContainer);
    }

    private void initListener() {
        mAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= 0) {
                } else {
                    float rate = (float) (ScreenUtil.dip2px(250) + verticalOffset * 2) / ScreenUtil.dip2px(250);
                    if (rate >= 0) {
                        mToolbarIvTarget.setAlpha(rate);
                        ArgbEvaluator evaluator = new ArgbEvaluator();
                        int color = (int) evaluator.evaluate(1 - rate, Color.parseColor("#ffffff"), mColors[mMainVpContainer.getCurrentItem()]);
                        (mMainVpContainer).setBackgroundColor(color);
                        mPagerChangeListener.setRate(rate);
                    }
                }
            }
        });
        mPagerChangeListener = PagerChangeListener.newInstance(
                mCollapsingToolbarLayout,
                mToolbarIvTarget,
                mToolbarIvOutgoing,
                mMainVpContainer,
                mColors);
        mMainVpContainer.addOnPageChangeListener(mPagerChangeListener);
    }

    private void initView() {
        mToolbarTab = (TabLayout) $(R.id.tabs);
        Toolbar mToolbar = (Toolbar) $(R.id.toolbar);
        mMainVpContainer = (ViewPager) $(R.id.viewpager);
        mRootLayout = (CoordinatorLayout) $(R.id.main_content);
        mAppbarlayout = (AppBarLayout) $(R.id.appbar);
        mToolbarIvTarget = (ImageView) $(R.id.toolbar_iv_target);
        mToolbarIvOutgoing = (ImageView) $(R.id.toolbar_iv_outgoing);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) $(R.id.collapsing_toolbar);
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        HomeDiHelper.getFragmentComponent(getFragmentModule()).inject(this);
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            final NormalDialog dialog = new NormalDialog(mContext);
            dialog.content("亲,真的要走吗?再看会儿吧~(●—●)")//
                    .style(NormalDialog.STYLE_TWO)//
                    .titleTextSize(23)//
                    .btnText("继续逛逛", "残忍退出")//
                    .btnTextColor(Color.parseColor("#383838"), Color.parseColor("#D4D4D4"))//
                    .btnTextSize(16f, 16f)//
                    .showAnim(new BounceTopEnter())//
                    .dismissAnim(new SlideBottomExit())//
                    .show();
            dialog.setOnBtnClickL(
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.dismiss();
                        }
                    },
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            dialog.superDismiss();
                            AppManager.getInstance()
                                    .AppExit();
                        }
                    });
        }
        return true;
    }

    @Override
    public void showContent(List<String> stringList) {
        String[] mImages = new String[4];
        if (stringList != null && !stringList.isEmpty()) {
            for (int i = 0; i < stringList.size(); i++) {
                mImages[i] = stringList.get(i);
            }
        }
        mPagerChangeListener.initImages(mImages);
    }

    @Override
    public void showError(String errorCode, String errorMsg) {
    }
}
