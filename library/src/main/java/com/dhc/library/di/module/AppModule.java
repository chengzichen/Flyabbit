package com.dhc.library.di.module;


import android.support.annotation.NonNull;

import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.DatabaseHelper;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.di.ContextLife;
import com.dhc.library.utils.AppUtil;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:52
 * 描述 ：TODO 请描述该类职责
 */
@Module
public class AppModule {
    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    @ContextLife("Application")
    BaseApplication provideApplicationContext() {
        AppUtil.syncIsDebug(application.getApplicationContext());
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper() {
        HttpHelper httpHelper=  new HttpHelper(application);
        IDataHelper.NetConfig netConfig= new IDataHelper.NetConfig();
        httpHelper .initConfig(netConfig);
        return httpHelper;
    }

    @Provides
    @Singleton
    DatabaseHelper provideDatabaseHelper() {
        return new DatabaseHelper(application);
    }

    @Provides @NonNull
    @Singleton
    public Random random() {
        return new Random();
    }


}
