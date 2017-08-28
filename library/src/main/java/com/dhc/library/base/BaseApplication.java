package com.dhc.library.base;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.library.AppManager;
import com.dhc.library.data.account.Account;
import com.dhc.library.data.account.AccountProvider;
import com.dhc.library.data.bean.LoginBean;
import com.dhc.library.di.component.AppComponent;
import com.dhc.library.di.module.AppModule;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.storage.StorageUtil;
import com.dhc.library.utils.sys.ScreenUtil;
import com.google.gson.GsonBuilder;

import com.dhc.library.di.component.DaggerAppComponent;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.helper.ExceptionHandler;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/23 18:03
 * 描述	      基类app
 */
public class BaseApplication extends MultiDexApplication  implements AccountProvider{

    protected static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //LeakCanary
//        LeakCanary.install(this);
        //保存appcotext的实例
        AppContext.init(this);
        AppUtil.syncIsDebug(this.getApplicationContext());//判断是否是debug模式

        // init tools
        StorageUtil.init(this, null);
        ScreenUtil.init(this);

        if (AppUtil.isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(instance); // 尽可能早，推荐在Application中初始化
        Fragmentation.builder()
                // 设置 栈视图 模式为 悬浮球模式   SHAKE: 摇一摇唤出   NONE：隐藏
                .stackViewMode(Fragmentation.BUBBLE)
                // ture时，遇到异常："Can not perform this action after onSaveInstanceState!"时，会抛出
                // false时，不会抛出，会捕获，可以在handleException()里监听到
                .debug(AppUtil.isDebug())
                // 线上环境时，可能会遇到上述异常，此时debug=false，不会抛出该异常（避免crash），会捕获
                // 建议在回调处上传至我们的Crash检测服务器
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        // 以Bugtags为例子: 手动把捕获到的 Exception 传到 Bugtags 后台。
                        // Bugtags.sendException(e);
                    }
                })
                .install();

        //添加一个intentsetvice服务来初始化一些服务
        InitializeService.start(this);

        registerActivityLifecycleCallbacks(new SwitchBackgroundCallbacks());
    }

    public static AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(instance))
                .build();

    }

    @Override
    public Account provideAccount(String accountJson) {
        return new GsonBuilder().create().fromJson(accountJson, LoginBean.class);
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
