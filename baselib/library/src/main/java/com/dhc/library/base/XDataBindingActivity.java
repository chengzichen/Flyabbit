package com.dhc.library.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/9/18 11:02
 * 描述	     Databinding和Dagger2使用的Activity
 */

public abstract class XDataBindingActivity<P extends IBasePresenter, B extends ViewDataBinding> extends XDaggerActivity<P> {


    public B mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        View rootView = getLayoutInflater().inflate(this.getLayout(), null, false);
        mViewBinding = DataBindingUtil.bind(rootView);
        super.onCreate(savedInstanceState);
    }


}
