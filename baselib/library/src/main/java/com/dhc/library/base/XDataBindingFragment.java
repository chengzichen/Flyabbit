package com.dhc.library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/9/18 11:23
 * 描述	       Databinding和Dagger2使用的Fragment
 */

public abstract class XDataBindingFragment<P extends IBasePresenter, B extends ViewDataBinding> extends XDaggerFragment<P> {
    public B mViewBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
