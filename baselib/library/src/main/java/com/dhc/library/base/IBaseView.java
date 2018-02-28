package com.dhc.library.base;

import android.content.Context;

import com.trello.rxlifecycle2.LifecycleTransformer;

/**
 * 创建者：邓浩宸
 * 时间 ：2016/11/15 16:07
 * 描述 ：View的基类
 */
public interface IBaseView  {

    <T> LifecycleTransformer<T> bindLifecycle() ;

    Context getAContext() ;

}
