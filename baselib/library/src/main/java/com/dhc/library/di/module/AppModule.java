package com.dhc.library.di.module;


import android.support.annotation.NonNull;

import com.dhc.library.base.BaseApplication;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:52
 * 描述 ：提供一些框架必须的实例的 {@link Module}
 */
@Module
public class AppModule {
    private final BaseApplication application;

    public AppModule(BaseApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
//    @ContextLife("Application")
    BaseApplication provideApplicationContext() {
        return application;
    }



    @Provides @NonNull
    @Singleton
    public Random random() {
        return new Random();
    }
}
