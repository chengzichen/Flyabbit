package com.dhc.library.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.library.ApplicationLike;
import com.dhc.library.utils.logger.KLog;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/23 18:04
 * 描述 ：该Application只能放在子moudle中使用,用于moudle隔离
 */

public class BaseChildApplication extends BaseApplication implements ApplicationLike {

    @Override
    public void onCreate() {
        super.onCreate();
        onCreateAsLibrary(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onLowMemoryAsLibrary(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        onTrimMemoryAsLibrary(this, level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        onTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged(this, newConfig);
    }

    @Override
    public void onTerminate(Application application) {
        KLog.d(this.getClass().getName()+"onTerminate");
    }

    @Override
    public void onCreateAsLibrary(Application application) {
        KLog.d(this.getClass().getName()+"onCreateAsLibrary");
    }

    @Override
    public void onLowMemoryAsLibrary(Application application) {
        KLog.d(this.getClass().getName()+"onLowMemoryAsLibrary");
    }

    @Override
    public void onTrimMemoryAsLibrary(Application application, int level) {
        KLog.d(this.getClass().getName()+"onTrimMemoryAsLibrary");
    }

    @Override
    public void onConfigurationChanged(Application application, Configuration configuration) {
        KLog.d(this.getClass().getName()+"onConfigurationChanged");
    }

    /**
     * arouter定义的启动之前的初始化(可以忽略)
     */
    @Override
    public void init(Context context) {

    }


}
