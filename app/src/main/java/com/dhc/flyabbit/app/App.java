package com.dhc.flyabbit.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.dhc.library.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:51
 * 描述 ：app 初始化
 */
public class App extends BaseApplication {

    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }

    public void onCreate() {

        super.onCreate();
        LeakCanary.install(this);

    }


    @NonNull public static App app(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }
}
