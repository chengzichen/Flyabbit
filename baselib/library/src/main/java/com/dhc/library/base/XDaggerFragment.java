package com.dhc.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.dhc.library.di.module.FragmentModule;
import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.framework.IBaseView;
import com.dhc.library.framework.ISupportDagger;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ： XDaggerFragment is  MVP by Dagger2
 */
public abstract class XDaggerFragment<T extends IBasePresenter> extends BaseFragment implements IBaseView,ISupportDagger {

    @Inject
    protected T mPresenter;
    public boolean isShowView=false;


    @Override
    public <E> LifecycleTransformer<E> bindLifecycle() {
        return this.<E>bindToLifecycle();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject(savedInstanceState);
        if (mPresenter != null) {
            isShowView = true;
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public Context getAContext() {
        return _mActivity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }

}