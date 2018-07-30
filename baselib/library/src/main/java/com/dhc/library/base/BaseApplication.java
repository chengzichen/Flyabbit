package com.dhc.library.base;


import android.app.Application;
import android.content.res.Configuration;

import com.dhc.library.data.IDataHelper;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.component.DaggerAppComponent;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.di.module.DataModule;
import com.dhc.library.framework.ISupportApplication;
import com.dhc.library.framework.XAppDelegate;

/**
 * @creator：denghc(desoce)
 * @updateTime：2018/7/30 12:00
 * @description： BaseApplication
 */
public class BaseApplication extends Application implements ISupportApplication {
    private XAppDelegate xAppDelegate;


    @Override
    public void onCreate() {
        super.onCreate();
        xAppDelegate = new XAppDelegate.DefaultAppDelegate(this).netConfig(getNetConfig());
        xAppDelegate.onCreate();
    }

    public XAppDelegate getXAppDelegate() {
        return xAppDelegate;
    }


    @Override
    public AppComponent getAppComponent() {
        return xAppDelegate.getAppComponent();
    }

    @Override
    public DaggerAppComponent.Builder getAppComponentBuilder() {
        return xAppDelegate.getAppComponentBuilder();
    }

    @Override
    public IDataHelper.NetConfig getNetConfig() {
        return null;
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        xAppDelegate.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        xAppDelegate.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        xAppDelegate.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        xAppDelegate.onConfigurationChanged(newConfig);
    }



}
