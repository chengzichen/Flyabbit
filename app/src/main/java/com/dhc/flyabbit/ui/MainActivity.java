package com.dhc.flyabbit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.R;
import com.dhc.flyabbit.di.HDiHelper;
import com.dhc.library.base.BaseActivity;
import com.dhc.library.data.SPHelper;
import com.dhc.library.data.net.Constants;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppUtil;
import com.dhc.library.utils.ReactManagerFactory;
import com.dhc.library.utils.delegate.OnShowHomeListener;
import com.facebook.react.ReactInstanceManager;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/20 10:03
 * 描述	      ${主页面}
 */
@Route(path = "/app/MainActivity")
public class MainActivity extends BaseActivity implements OnShowHomeListener {
    private static final int REDBOX_PERMISSION_MESSAGE =112 ;
    private int OVERLAY_PERMISSION_REQ_CODE = 111;
    protected ReactInstanceManager mReactInstanceManager;
    @Override
    protected int getLayout()
    {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(this)) {
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                this.startActivity(serviceIntent);
            }
        }
        mReactInstanceManager= ReactManagerFactory.getReactInstanceManager();
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
        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
                super.onFragmentCreated(fragment, savedInstanceState);
            }
            // 省略其余生命周期方法

        });
    }



    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        //TODO 依赖注入
        HDiHelper.getActivityComponent(getActivityModule()).inject(this);
    }



    @Override
    public void showHome() {
        replaceLoadRootFragment(R.id.fl_container, MainFragment.newInstance(),true);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                }
            }
        }
    }
}
