package com.dhc.flyabbit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.R;
import com.dhc.library.base.XDaggerActivity;
import com.dhc.library.data.SPHelper;
import com.dhc.businesscomponent.Constants;
import com.dhc.library.framework.OnShowHomeListener;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppUtil;


/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/20 10:03
 * 描述	      ${主页面}
 */
@Route(path = "/app/MainActivity")
public class MainActivity extends XDaggerActivity implements OnShowHomeListener {

    private ViewGroup mViewGroup;
    private ViewGroup mContentView;

    @Override
    protected int getLayout()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //加载主界面的Fragment
            if (SPHelper.get(AppContext.get(), Constants.VERSION, "", SPHelper.VERSION_FILE_NAME).equals(AppUtil.getVersionName(AppContext.get()))){

            loadRootFragment(R.id.fl_container,  MainFragment.newInstance());
            }else{
            loadRootFragment(R.id.fl_container,  new SplashFrament());
            }
//            loadMultipleRootFragment(R.id.fl_container,1, MainFragment.newInstance(),new SplashFrament());
        } else {
            if (SPHelper.get(AppContext.get(), Constants.VERSION, "", SPHelper.VERSION_FILE_NAME).equals(AppUtil.getVersionName(AppContext.get()))){

            loadRootFragment(R.id.fl_container, findFragment(MainFragment.class));
            }else{

            loadRootFragment(R.id.fl_container, findFragment(SplashFrament.class));
            }
        }
        getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                super.onFragmentCreated(fm, f, savedInstanceState);
                Log.i("MainActivity", "onFragmentCreated--->" + f.getClass().getSimpleName());
            }

            @Override
            public void onFragmentStopped(FragmentManager fm, Fragment f) {
                super.onFragmentStopped(fm, f);
                Log.i("MainActivity", "onFragmentStopped--->" + f.getClass().getSimpleName());
            }
        }, true);
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        //TODO 依赖注入
    }



    @Override
    public void showHome() {
//        replaceLoadRootFragment(R.id.fl_container, MainFragment.newInstance(),true);
        loadRootFragment(R.id.fl_container, MainFragment.newInstance());
    }
}
