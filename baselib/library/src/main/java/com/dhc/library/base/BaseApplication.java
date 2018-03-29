package com.dhc.library.base;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.component.DaggerAppComponent;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.di.module.DataModule;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppManager;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.sys.ScreenUtil;

import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/23 18:03
 * 描述	      基类app
 */
public class BaseApplication extends MultiDexApplication  {

    protected static BaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //保存appcotext的实例
        AppContext.init(this);
        AppUtil.syncIsDebug(this.getApplicationContext());//判断是否是debug模式
        // init tools
        ScreenUtil.init(this);
        if (AppUtil.isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(instance); // 尽可能早，推荐在Application中初始化
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(AppUtil.isDebug())    // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                }).install();
        //添加一个intentsetvice服务来初始化一些服务
        InitializeService.start(this);

        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .dataModule(new DataModule(getNetConfig()))
                .appModule(new AppModule(instance))
                .build();

    }

    public IDataHelper.NetConfig getNetConfig() {
        return null;
    }


    private class SwitchBackgroundCallbacks implements Application.ActivityLifecycleCallbacks {

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
