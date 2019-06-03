package com.dhc.flyabbit.app;

import android.content.Context;
import androidx.multidex.MultiDex;
import androidx.appcompat.app.AppCompatDelegate;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.businesscomponent.Constants;
import com.dhc.businesscomponent.data.LoginInfoBean;
import com.dhc.businesscomponent.data.account.AccountProvider;
import com.dhc.library.base.BaseApplication;
import com.dhc.businesscomponent.base.InitializeService;
import com.dhc.library.data.IDataHelper;
import com.dhc.businesscomponent.data.net.TokenInterceptor;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.ApplicationLike;
import com.dhc.library.utils.AsLibUtil;
import com.dhc.timberhelper.TimberInitHelper;
import com.google.gson.GsonBuilder;
import com.ladingwu.glidelibrary.GlideImageLocader;
import com.lasingwu.baselibrary.ImageLoaderConfig;
import com.lasingwu.baselibrary.ImageLoaderManager;
import com.lasingwu.baselibrary.LoaderEnum;

import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.converter.protobuf.ProtoConverterFactory;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:51
 * 描述 ：app 初始化
 */
public class App extends BaseApplication  implements AccountProvider<LoginInfoBean> {

    @Autowired(name = "/home/application1")
    ApplicationLike mApplicationLikeMoudle1;
    @Autowired(name = "/my/application2")
    ApplicationLike mApplicationLikeMoudle2;
    @Autowired(name = "/girls/application3")
    ApplicationLike mApplicationLikeMoudle3;

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.getInstance().inject(this);
        if (mApplicationLikeMoudle1!=null) {
            AsLibUtil.addAsLIbChild(mApplicationLikeMoudle1);
        }
        if (mApplicationLikeMoudle2!=null) {
            AsLibUtil.addAsLIbChild(mApplicationLikeMoudle2);
        }
        if (mApplicationLikeMoudle3!=null) {
            AsLibUtil.addAsLIbChild(mApplicationLikeMoudle3);
        }
        AsLibUtil.doCreateAsLibrary(this);
        TimberInitHelper.init(AppUtil.isDebug(),this);
        ImageLoaderConfig config = new ImageLoaderConfig.Builder(LoaderEnum.GLIDE,new GlideImageLocader())
                // 配置内存缓存，单位为Byte
                .maxMemory(40*1024*1024L)
                .build();
        ImageLoaderManager.getInstance().init(this,config);
        InitializeService.start(this);
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
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoaderManager.getInstance().cleanMemory(this);
    }

    @Override
    public LoginInfoBean provideAccount(String accountJson) {
        return new GsonBuilder().create().fromJson(accountJson, LoginInfoBean.class);
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
