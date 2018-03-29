package com.dhc.flyabbit.gank.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.gank.R;
import com.dhc.lib.widget.util.ToolbarUtil;
import com.dhc.library.base.BaseFragment;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.lib.widget.bean.ToolBarOptions;
import com.dhc.library.framework.OnBackToFirstListener;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */
@Route(path = "/gank/GankFragment")
public class GankFragment extends XDaggerFragment {

    protected OnBackToFirstListener _mBackToFirstListener;


    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gank_main_view;
    }

    @Override
    protected void initEventAndData(View view) {
        BaseFragment baseFragment = (BaseFragment) getParentFragment();
        if (baseFragment instanceof OnBackToFirstListener) {
            _mBackToFirstListener = (OnBackToFirstListener) baseFragment;
        }
        initTitle();
        loadRootFragment(R.id.fl_rootview,new GirlFragment());

    }

    private void initTitle() {
        ToolBarOptions options = new ToolBarOptions()
                .isNeedNavigate(false).titleString("干货集中营");
//        setToolBar(R.id.toolbar, options);
        new ToolbarUtil().setToolBar(_mActivity, $(R.id.toolbar),options,false);
    }


    @Override
    public void initInject(Bundle savedInstanceState) {

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
