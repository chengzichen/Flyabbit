package com.dhc.library.framework;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.component.DaggerAppComponent;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.di.module.DataModule;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppManager;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.AsLibUtil;
import com.dhc.library.utils.sys.ScreenUtil;

/**
 * @creator：denghc(desoce)
 * @updateTime： 2018/6/20 10:43
 * @description： Application delegate
 */
public interface XAppDelegate {

    void onCreate();

    void onTerminate();

    void onConfigurationChanged(Configuration newConfig);

    void onLowMemory();

    void onTrimMemory(int level);

    AppComponent getAppComponent();

    DaggerAppComponent.Builder getAppComponentBuilder();

     XAppDelegate netConfig(IDataHelper.NetConfig netConfig) ;



    public class DefaultAppDelegate implements XAppDelegate {

        private Application application;

        private DaggerAppComponent.Builder builder;

        private IDataHelper.NetConfig mNetConfig;

        public DefaultAppDelegate(Application application) {
            this.application = application;
        }

        @Override
        public void onCreate() {
            AppContext.init(application);       //保存appcotext的实例
            AppUtil.syncIsDebug(application.getApplicationContext());//判断是否是debug模式
            ScreenUtil.init(application);    // init tools
            if (AppUtil.isDebug()) {
                ARouter.openLog();     // 打印日志
                ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            }
            ARouter.init(application); // 尽可能早，推荐在Application中初始化
            application.registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
        }

        @Override
        public void onTerminate() {
            AsLibUtil.onTerminate(application);
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
            AsLibUtil.onConfigurationChanged(application, newConfig);
        }

        @Override
        public void onLowMemory() {
            AsLibUtil.onLowMemoryAsLibrary(application);
        }

        @Override
        public void onTrimMemory(int level) {
            AsLibUtil.onTrimMemoryAsLibrary(application, level);
        }

        public DaggerAppComponent.Builder getAppComponentBuilder() {
            return builder = DaggerAppComponent.builder()
                    .dataModule(new DataModule(mNetConfig))
                    .appModule(new AppModule((BaseApplication) application));
        }

        public AppComponent getAppComponent() {
            if (builder == null)
                builder = getAppComponentBuilder();
            return builder.build();
        }

        public DefaultAppDelegate netConfig(IDataHelper.NetConfig netConfig) {
            mNetConfig=netConfig;
            return this;
        }
    }


    public class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            AppManager.getInstance().addActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getInstance().removeActivity(activity);
        }
    }
}
