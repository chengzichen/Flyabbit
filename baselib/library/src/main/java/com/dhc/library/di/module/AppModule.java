package com.dhc.library.di.module;


import android.support.annotation.NonNull;

import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.di.ContextLife;
import com.dhc.library.utils.file.FileUtil;

import java.io.File;
import java.util.Random;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

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
    @ContextLife("Application")
    BaseApplication provideApplicationContext() {
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

//    @Provides
//    @Singleton
//    DatabaseHelper provideDatabaseHelper() {
//        return new DatabaseHelper(application);
//    }

    @Provides @NonNull
    @Singleton
    public Random random() {
        return new Random();
    }

    /**
     * 提供 {@link RxCache}
     *
     * @param cacheDirectory RxCache缓存路径
     * @return
     */
    @Singleton
    @Provides
    RxCache provideRxCache(@Named("RxCacheDirectory") File cacheDirectory) {
        RxCache.Builder builder = new RxCache.Builder();
        return builder
                .persistence(cacheDirectory, new GsonSpeaker());
    }

    /**
     * 需要单独给 {@link RxCache} 提供缓存路径
     *
     * @return
     */
    @Singleton
    @Provides
    @Named("RxCacheDirectory")
    File provideRxCacheDirectory() {
        File cacheDirectory = new File(FileUtil.getCacheDirectory(application), "RxCache");
        return FileUtil.makeDirs(cacheDirectory);
    }


}
