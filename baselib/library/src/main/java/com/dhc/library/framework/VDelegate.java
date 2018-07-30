package com.dhc.library.framework;

import android.view.View;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/28 16:44
 * 描述 ：View的代理
 */
public interface VDelegate {

    void resume();

    void pause();

    void destory();

    void visible(boolean flag, View view);

    void gone(boolean flag, View view);

    void inVisible(View view);

    void toastShort(String msg);

    void toastLong(String msg);

}
