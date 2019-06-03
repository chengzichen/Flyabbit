package com.dhc.flyabbit.home.app;


import android.app.Application;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.businesscomponent.Constants;
import com.dhc.library.base.BaseChildApplication;
import com.dhc.businesscomponent.base.InitializeService;
import com.dhc.library.data.IDataHelper;
import com.dhc.businesscomponent.data.net.TokenInterceptor;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.ApplicationLike;
import com.dhc.timberhelper.TimberInitHelper;
import com.ladingwu.glidelibrary.GlideImageLocader;
import com.lasingwu.baselibrary.ImageLoaderConfig;
import com.lasingwu.baselibrary.ImageLoaderManager;
import com.lasingwu.baselibrary.LoaderEnum;

import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import timber.log.Timber;


/**
 * @author 浩宸
 * @time 2017/3/24 14:42
 * @desc  这里的application只是在作为单独App是debug时使用的,初始化一些东西,但是实际开发是还是放在主工程的app中去初始化(IM模块除外)
 */
@Route(path = "/home/application1")
public class HomeApp extends BaseChildApplication implements ApplicationLike {


    //不要对一个 Activity Context 保持长生命周期的引用。尽量在一切可以使用应用 ApplicationContext 代替 Context 的地方进行替换。

    @Override
    public void onCreateAsLibrary(Application application) {//这个方法是不管是作为APP还是modle时都会调用,用来初始化
        super.onCreateAsLibrary(application);
    }
    @Override
    public void onCreate() {//该方法只要在单独运行时作为入口APP类时才会调用
        super.onCreate();
        TimberInitHelper.init(AppUtil.isDebug(),this);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
                // 配置内存缓存，单位为Byte
                .maxMemory(40*1024*1024L)
                .build();
        ImageLoaderManager.getInstance().init(this,config);
        InitializeService.start(this);
        Timber.d("HomeApp onCreate");
    }
    /**
     * 必须重新设置BaseUrl
     * @return
     */
    @Override
    public  IDataHelper.NetConfig getNetConfig() {
        return new IDataHelper.NetConfig().configBaseURL(Constants.GANK_URL)
                .configInterceptors(new Interceptor[]{new TokenInterceptor()})//配置Token
                .configConverterFactory(new Converter.Factory[]{ProtoConverterFactory.create()});
        //配置Proto格式工厂
    }
}
