package com.dhc.library.utils;

import android.app.Application;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.shell.MainReactPackage;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/9/20 16:26
 * 描述	      ${TODO}
 */

public class ReactManagerFactory {
    private static ReactInstanceManager instance;

    private ReactManagerFactory() {

    }

    public static synchronized ReactInstanceManager getReactInstanceManager() {
        if (instance == null) {
            instance = ReactInstanceManager.builder()
                    .setApplication((Application) AppContext.get())
                    .setBundleAssetName("index.android.bundle")
                    .setJSMainModuleName("index.android")
                    .addPackage(new MainReactPackage())
                    .setUseDeveloperSupport(true)
                    .setInitialLifecycleState(LifecycleState.RESUMED)
                    .build();
            ;
        }
        return instance;
    }

}
