package com.dhc.flyabbit.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.taobao.atlas.bundleInfo.AtlasBundleInfoManager;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.framework.BundleImpl;
import android.taobao.atlas.framework.BundleInstaller;
import android.taobao.atlas.runtime.ActivityTaskMgr;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.library.utils.ApplicationLike;
import com.dhc.library.utils.AsLibUtil;
import com.dhc.library.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;

import org.osgi.framework.BundleException;

import java.io.File;

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

        Atlas.getInstance().setClassNotFoundInterceptorCallback(new ClassNotFoundInterceptorCallback() {
            @Override
            public Intent returnIntent(Intent intent) {
                final String className = intent.getComponent().getClassName();
                final String bundleName = AtlasBundleInfoManager.instance().getBundleForComponet(className);

                if (!TextUtils.isEmpty(bundleName) && !AtlasBundleInfoManager.instance().isInternalBundle(bundleName)) {

                    //远程bundle
                    Activity activity = ActivityTaskMgr.getInstance().peekTopActivity();
                    File remoteBundleFile = new File(activity.getExternalCacheDir(),"lib" + bundleName.replace(".","_") + ".so");

                    String path = "";
                    if (remoteBundleFile.exists()){
                        path = remoteBundleFile.getAbsolutePath();
                    }else {
                        Toast.makeText(activity, " 远程bundle不存在，请确定 : " + remoteBundleFile.getAbsolutePath() , Toast.LENGTH_LONG).show();
                        return intent;
                    }


                    PackageInfo info = activity.getPackageManager().getPackageArchiveInfo(path, 0);
                    try {
                        Atlas.getInstance().installBundle(info.packageName, new File(path));
                    } catch (BundleException e) {
                        Toast.makeText(activity, " 远程bundle 安装失败，" + e.getMessage() , Toast.LENGTH_LONG).show();

                        e.printStackTrace();
                    }

                    activity.startActivities(new Intent[]{intent});

                }

                return intent;
            }
        });
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
