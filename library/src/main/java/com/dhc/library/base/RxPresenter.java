package com.dhc.library.base;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:08
 * 描述 ：基于Rx的Presenter封装,控制订阅的生命周期
 */
public class RxPresenter<V extends IBaseView> implements IBasePresenter<V> {

    private V mView;

    @Override
    public void attachView(V view) {
        this.mView = view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }

    protected V getV() {
        if (mView == null) {
            throw new IllegalStateException("v can not be null");
        }
        return mView;
    }
}