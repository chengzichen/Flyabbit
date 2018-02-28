package com.dhc.library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dhc.library.di.module.ActivityModule;
import com.trello.rxlifecycle2.LifecycleTransformer;

import javax.inject.Inject;


/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:06
 * 描述 ：MVP activity基类
 */
public abstract class XDaggerActivity<T extends IBasePresenter> extends BaseActivity implements IBaseView  {
    @Inject
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        if (mPresenter != null)
            mPresenter.attachView(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    public <E> LifecycleTransformer<E> bindLifecycle() {
        return this.<E>bindToLifecycle();
    }


    @Override
    public Context getAContext() {
        return this;
    }



    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

}