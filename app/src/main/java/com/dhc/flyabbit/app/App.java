package com.dhc.flyabbit.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.library.ApplicationLike;
import com.dhc.library.AsLibUtil;
import com.dhc.library.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:51
 * 描述 ：app 初始化
 */
public class App extends BaseApplication {

    @Autowired(name = "/home/application1")
    ApplicationLike mApplicationLikeMoudle1;
    @Autowired(name = "/my/application2")
    ApplicationLike mApplicationLikeMoudle2;
    @Autowired(name = "/girls/application3")
    ApplicationLike mApplicationLikeMoudle3;
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
        ARouter.getInstance().inject(this);
        if (mApplicationLikeMoudle1!=null)
        AsLibUtil.addAsLIbChild(mApplicationLikeMoudle1);
        if (mApplicationLikeMoudle2!=null)
        AsLibUtil.addAsLIbChild(mApplicationLikeMoudle2);
        if (mApplicationLikeMoudle3!=null)
        AsLibUtil.addAsLIbChild(mApplicationLikeMoudle3);
        AsLibUtil.doCreateAsLibrary(this);
    }


    @NonNull public static App app(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        AsLibUtil.onLowMemoryAsLibrary(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        AsLibUtil. onTrimMemoryAsLibrary(this, level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AsLibUtil. onTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        AsLibUtil. onConfigurationChanged(this, newConfig);
    }

}
