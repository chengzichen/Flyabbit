package com.dhc.library.base;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ：Presenter基类
 */
public interface IBasePresenter<V extends IBaseView>{

    void attachView(V view);

    void detachView();
}
