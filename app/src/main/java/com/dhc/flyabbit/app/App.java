package com.dhc.flyabbit.app;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.businesscomponent.Constants;
import com.dhc.businesscomponent.data.LoginInfoBean;
import com.dhc.lib.imageload.ImageLoaderManager;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.data.account.AccountProvider;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.ApplicationLike;
import com.dhc.library.utils.AsLibUtil;
import com.dhc.timberhelper.TimberInitHelper;
import com.google.gson.GsonBuilder;
import com.squareup.leakcanary.LeakCanary;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:51
 * 描述 ：app 初始化
 */
public class App extends BaseApplication  implements AccountProvider<LoginInfoBean> {

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
        TimberInitHelper.init(AppUtil.isDebug(),this);
        ImageLoaderManager.getInstance().init(this);
    }


    @NonNull public static App app(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    /**
     * 必须重新设置BaseUrl
     * @return
     */
    @Override
    public  IDataHelper.NetConfig getNetConfig() {
        return new IDataHelper.NetConfig().configBaseURL(Constants.GANK_URL);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        AsLibUtil.onLowMemoryAsLibrary(this);
        ImageLoaderManager.getInstance().cleanMemory(this);
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

    @Override
    public LoginInfoBean provideAccount(String accountJson) {
        return new GsonBuilder().create().fromJson(accountJson, LoginInfoBean.class);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
