package com.dhc.library.framework;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ：View的基类
 */
public interface IBaseView  {

    /**
     * Retrofit bind View Lifecycle
     * @param <T>   Response data
     * @return  Response data
     */
    <T> LifecycleTransformer<T> bindLifecycle() ;

    /**
     * get  View context
     * @return
     */
    Context getAContext() ;

}
