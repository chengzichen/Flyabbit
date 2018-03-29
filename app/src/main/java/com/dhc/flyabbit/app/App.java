package com.dhc.flyabbit.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.taobao.atlas.bundleInfo.AtlasBundleInfoManager;
import android.taobao.atlas.framework.Atlas;
import android.taobao.atlas.runtime.ActivityTaskMgr;
import android.taobao.atlas.runtime.ClassNotFoundInterceptorCallback;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.dhc.businesscomponent.Constants;
import com.dhc.businesscomponent.data.LoginInfoBean;
import com.dhc.flyabbit.presenter.DownLoadPresenter;
import com.dhc.flyabbit.presenter.contract.IDownLoadContract;
import com.dhc.lib.imageload.ImageLoaderManager;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.data.IDataHelper;
import com.dhc.library.data.account.AccountProvider;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.ApplicationLike;
import com.dhc.library.utils.AsLibUtil;
import com.dhc.timberhelper.TimberInitHelper;
import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.google.gson.GsonBuilder;
import com.squareup.leakcanary.LeakCanary;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.osgi.framework.BundleException;

import java.io.File;

import javax.inject.Inject;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:51
 * 描述 ：app 初始化
 */
public class App extends BaseApplication implements IDownLoadContract.IView ,AccountProvider<LoginInfoBean> {

    @Autowired(name = "/home/application1")
    ApplicationLike mApplicationLikeMoudle1;
    @Autowired(name = "/my/application2")
    ApplicationLike mApplicationLikeMoudle2;
    @Autowired(name = "/girls/application3")
    ApplicationLike mApplicationLikeMoudle3;
    public static synchronized BaseApplication getInstance() {
        return instance;
    }

    @Inject
    DownLoadPresenter downLoadPresenter;
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
        if (downLoadPresenter != null)
            downLoadPresenter.attachView(this);
//        https://bundle-1253245619.cos.ap-guangzhou.myqcloud.com/libcom_dhc_filyabbit.so
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
                        if (("lib"+bundleName.replace(".", "_") + ".so").equals("libcom_dhc_filyabbit.so")) {
                            final NormalDialog dialog = new NormalDialog(activity);
                            dialog.content("图片查看器是在服务器下载的,是否下载?")//
                                    .style(NormalDialog.STYLE_TWO)//
                                    .titleTextSize(23)//
                                    .btnText("残忍拒绝", "下载插件")//
                                    .btnTextColor(Color.parseColor("#D4D4D4"), Color.parseColor("#383838"))//
                                    .btnTextSize(16f, 16f)//
                                    .showAnim(new BounceTopEnter())//
                                    .dismissAnim(new SlideBottomExit())//
                                    .show();
                            dialog.setOnBtnClickL(
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            dialog.dismiss();
                                        }
                                    },
                                    new OnBtnClickL() {
                                        @Override
                                        public void onBtnClick() {
                                            dialog.superDismiss();
                                            downLoadPresenter.download(" https://bundle-1253245619.cos.ap-guangzhou.myqcloud.com/libcom_dhc_filyabbit.so", "libcom_dhc_filyabbit.so");
                                        }
                                    });
                            dialog.show();

                        }
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
        TimberInitHelper.init(AppUtil.isDebug(),this);
        ImageLoaderManager.getInstance().init(this);
    }


    @NonNull public static App app(@NonNull Context context) {
        return (App) context.getApplicationContext();
    }

    /**
     * 必须重新设置BaseUrl
     * @return
     */
    @Override
    public  IDataHelper.NetConfig getNetConfig() {
        return new IDataHelper.NetConfig().configBaseURL(Constants.GANK_URL);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        AsLibUtil.onLowMemoryAsLibrary(this);
        ImageLoaderManager.getInstance().cleanMemory(this);
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

    @Override
    public <T> LifecycleTransformer<T> bindLifecycle() {
        return null;
    }

    @Override
    public Context getAContext() {
        return this;
    }

    @Override
    public void showContent(Boolean downLoad) {
        Toast.makeText(this, "下载成功", Toast
                .LENGTH_LONG).show();
    }

    @Override
    public void showError(String code, String msg) {

    }
    @Override
    public LoginInfoBean provideAccount(String accountJson) {
        return new GsonBuilder().create().fromJson(accountJson, LoginInfoBean.class);
    }

}
