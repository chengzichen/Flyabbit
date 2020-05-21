package com.dhc.flyabbit.my.app;

import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.library.base.BaseChildApplication;
import com.dhc.businesscomponent.base.InitializeService;
import com.dhc.library.utils.AppUtil;
import com.dhc.timberhelper.TimberInitHelper;
import com.ladingwu.glidelibrary.GlideImageLocader;
import com.lasingwu.baselibrary.ImageLoaderConfig;
import com.lasingwu.baselibrary.ImageLoaderManager;
import com.lasingwu.baselibrary.LoaderEnum;


/**
 * @author 浩宸
 * @time 2017/3/24 14:42
 * @desc  这里的application只是在作为单独App是debug时使用的,初始化一些东西,但是实际开发是还是放在主工程的app中去初始化
 */
@Route(path = "/my/application2")
public class MyApp extends BaseChildApplication {


    /**
     * 不要对一个 Activity Context 保持长生命周期的引用。尽量在一切可以使用应用 ApplicationContext 代替 Context 的地方进行替换。
     * @param application
     */
    @Override
    public void onCreateAsLibrary(Application application) {//这个方法是不管是作为APP还是modle时都会调用,用来初始化
        super.onCreateAsLibrary(application);
    }

    @Override
    public void onCreate() {//该方法只要在单独运行时作为入口APP类时才会调用
        super.onCreate();
        TimberInitHelper.init(AppUtil.INSTANCE.isDebug(),this);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
                // 配置内存缓存，单位为Byte
                .maxMemory(40*1024*1024L)
                .build();
        ImageLoaderManager.getInstance().init(this,config);
        InitializeService.start(this);
    }
}
