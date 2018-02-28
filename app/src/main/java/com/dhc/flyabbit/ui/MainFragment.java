package com.dhc.flyabbit.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.R;
import com.dhc.flyabbit.gank.ui.GankFragment;
import com.dhc.flyabbit.home.ui.HomeFragment;
import com.dhc.flyabbit.my.MyFragment;
import com.dhc.lib.widget.bottombar.BottomBar;
import com.dhc.lib.widget.bottombar.BottomBarTab;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.data.account.AccountManager;
import com.dhc.library.framework.OnBackToFirstListener;

import me.yokeyword.fragmentation.SupportFragment;



/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 9:11
 * 描述 ：主界面的主Fragment的容器
 */
@Route(path = "/app/MainFragment")
public class MainFragment extends XDaggerFragment implements OnBackToFirstListener {

    private BottomBar mBottomBar;
    private String[] mTitles = {"干货", "妹子", "关于"};
    private int[] mIconUnselectIds = {
            R.mipmap.ic_drawer_gank,
            R.mipmap.ic_drawer_meizi, R.mipmap.ic_drawer_setting};
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public int current = -1;
    private SupportFragment[] mFragments = new SupportFragment[4];

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void initView(View view) {
        mBottomBar = (BottomBar) view.findViewById(R.id.bottomBar);
        mBottomBar.addItem(new BottomBarTab(_mActivity, mIconUnselectIds[0], mTitles[0]))
                .addItem(new BottomBarTab(_mActivity, mIconUnselectIds[1], mTitles[1]))
                .addItem(new BottomBarTab(_mActivity, mIconUnselectIds[2], mTitles[2]));


        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, int prePosition) {//tab切换
                boolean isLogin = AccountManager.INSTANCE.isLogin();
                if ((position != 0) && !isLogin) {
                    current = position;
                    // TODO: 2017/8/21  这里适用于条件触发式登录
                    showHideFragment(mFragments[position], mFragments[prePosition]);
                    return false;
                } else {
                    showHideFragment(mFragments[position], mFragments[prePosition]);
                    return false;
                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
            }
        });

    }



    @Override
    public void initInject(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[FIRST] = HomeFragment.newInstance();
            mFragments[SECOND] = GankFragment.newInstance();
            mFragments[THIRD] = MyFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        } else {
            mFragments[FIRST] = findChildFragment(HomeFragment.class);
            mFragments[SECOND] = findChildFragment(GankFragment.class);
            mFragments[THIRD] = findChildFragment(MyFragment.class);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initEventAndData(View view) {
        initView(view);
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
}
