package com.dhc.library.base;

import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.framework.IBaseView;

/**
 * @creator：denghc(desoce)
 * @updateTime：2018/7/30 13:31
 * @description： Used to attachView and detachView
 */
public class XPresenter<V extends IBaseView> implements IBasePresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    /**
     *  getViewModel ViewModel  is Activity or Fragment
     *
     * @return  IBaseView
     */
    protected V getV() {
        if (mView == null) {
            throw new IllegalStateException("view can not be null");
        }
        return mView;
    }
}