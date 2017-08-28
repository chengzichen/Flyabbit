package com.dhc.flyabbit.my;

import android.os.Bundle;
import android.view.View;

import com.dhc.library.OnBackToFirstListener;
import com.dhc.library.base.BaseFragment;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.constant.ToolBarOptions;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */

public class MyFragment extends XDaggerFragment {

    protected OnBackToFirstListener _mBackToFirstListener;


    public static MyFragment newInstance() {
        MyFragment fragment = new MyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_main;
    }

    @Override
    protected void initEventAndData(View view) {
        BaseFragment baseFragment = (BaseFragment) getParentFragment();
        if (baseFragment instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) baseFragment;
        }
        initTitle();
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
    }
    
    private void initTitle() {
        ToolBarOptions options = new ToolBarOptions()
                .isNeedNavigate(false).titleString("我的");
        setToolBar(R.id.toolbar, options);
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
            if (_mBackToFirstListener != null) {
                _mBackToFirstListener.onBackToFirstFragment();
            } else {
                _mActivity.finish();
            }
        }
        return true;
    }
}
