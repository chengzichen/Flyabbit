package com.dhc.flyabbit.my;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhc.library.utils.delegate.OnBackToFirstListener;
import com.dhc.library.base.BaseActivity;
import com.dhc.library.base.BaseFragment;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.data.bean.ToolBarOptions;
import com.dhc.library.base.WebViewCommonFragment;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/30 0030 上午 10:23
 * 描述 ：TODO 请描述该类职责
 */

public class MyFragment extends XDaggerFragment implements View.OnClickListener {

    protected OnBackToFirstListener _mBackToFirstListener;
    protected  ImageView mIvLogo;
    private TextView mGithub;
    private  TextView mAuthor;
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
        initView();
        initListener();
    }
    private void initTitle() {
        ToolBarOptions options = new ToolBarOptions()
                .isNeedNavigate(false).titleString("我的");
        setToolBar(R.id.toolbar, options);
    }
    private void initView() {
        mIvLogo = $(R.id.iv_logo);
        mGithub = $(R.id.tv_github);
        mAuthor = $(R.id.tv_author);
        mIvLogo.setColorFilter(ContextCompat.getColor(_mActivity, R.color.clr_6385a7));
    }

    private void initListener() {
        mGithub.setOnClickListener(this);
        mAuthor.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tv_github){
            ((BaseActivity) _mActivity).start(WebViewCommonFragment.newInstance("Github",
                   "https://github.com/chengzichen/Flyabbit", "", ""));
        }else if (v.getId()==R.id.tv_author){
            ((BaseActivity) _mActivity).start(WebViewCommonFragment.newInstance("个人博客",
                    "http://blog.csdn.net/chengzichen_/article/details/77659318", "", ""));
        }
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
