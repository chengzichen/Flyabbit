package com.dhc.library.framework;

import com.dhc.library.base.XDaggerActivity;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ：Presenter基类
 */
public interface IBasePresenter<V extends IBaseView>{

    /**
     *   lifecycle attachView
     * @param view
     */
    void attachView(V view);

    /**
     *  lifecycle detachView{@link XDaggerActivity#onDestroy()}
     */
    void detachView();

}
