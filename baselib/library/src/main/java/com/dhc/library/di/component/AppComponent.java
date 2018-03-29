package com.dhc.library.di.component;


import com.dhc.library.data.DBHelper;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.di.ContextLife;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.di.module.DataModule;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Component;
import io.rx_cache2.internal.RxCache;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:53
 * 描述 ：App的注解使用
 */
@Singleton
@Component(modules = {DataModule.class,AppModule.class})
public interface AppComponent {

    BaseApplication getContext();  // 提供App的Context

    HttpHelper httpHelper();  //提供http的帮助类

    DBHelper dtabaseHelper();  //提供db的帮助类
    /**
     * {@link Random} instance from {@link AppModule}
     * which now can be injected to children
     * that depend on {@link AppComponent}.
     */
    Random random();

    RxCache getRxCache();
}
