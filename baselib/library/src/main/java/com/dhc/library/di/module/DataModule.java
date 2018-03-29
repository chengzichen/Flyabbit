package com.dhc.library.di.module;


import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.DBHelper;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.utils.file.FileUtil;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * 创建者     邓浩宸
 * 创建时间   2018/3/29 12:51
 * 描述	     数据工具提供者
 */
@Module
public class DataModule {

    IDataHelper.NetConfig netConfig;


    public DataModule(IDataHelper.NetConfig netConfig) {
        this.netConfig = netConfig;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(BaseApplication application) {
        HttpHelper httpHelper = new HttpHelper(application);
        if (netConfig == null)
            netConfig = new IDataHelper.NetConfig();
        httpHelper.initConfig(netConfig);
        return httpHelper;
    }
    @Provides
    @Singleton
    DBHelper provideDatabaseHelper(BaseApplication application) {
        return new DBHelper(application);
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
    File provideRxCacheDirectory(BaseApplication application) {
        File cacheDirectory = new File(FileUtil.getCacheDirectory(application), "RxCache");
        return FileUtil.makeDirs(cacheDirectory);
    }

}
