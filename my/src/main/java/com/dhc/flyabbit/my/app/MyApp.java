package com.dhc.flyabbit.my.app;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.lib.imageload.ImageLoaderManager;
import com.dhc.library.base.BaseChildApplication;
import com.dhc.library.utils.AppUtil;
import com.dhc.timberhelper.TimberInitHelper;


/**
 * @author 浩宸
 * @time 2017/3/24 14:42
 * @desc  这里的application只是在作为单独App是debug时使用的,初始化一些东西,但是实际开发是还是放在主工程的app中去初始化
 */
@Route(path = "/my/application2")
public class MyApp extends BaseChildApplication {


    //不要对一个 Activity Context 保持长生命周期的引用。尽量在一切可以使用应用 ApplicationContext 代替 Context 的地方进行替换。
    @Override
    public void onCreateAsLibrary(Application application) {//这个方法是不管是作为APP还是modle时都会调用,用来初始化
        super.onCreateAsLibrary(application);
    }

    @Override
    public void onCreate() {//该方法只要在单独运行时作为入口APP类时才会调用
        super.onCreate();
        TimberInitHelper.init(AppUtil.isDebug(),this);
        ImageLoaderManager.getInstance().init(this);
    }
}
