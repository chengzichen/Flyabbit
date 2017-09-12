package com.dhc.flyabbit.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dhc.flyabbit.R;
import com.dhc.library.OnShowHomeListener;
import com.dhc.library.base.XDaggerActivity;
import com.dhc.library.data.SPHelper;
import com.dhc.library.data.net.Constants;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.AppUtil;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/20 10:03
 * 描述	      ${主页面}
 * <View
 * android:id="@+id/status_bar_view"
 * android:layout_width="match_parent"
 * android:layout_height="0dp"
 * android:background="@color/colorPrimary" />
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
    }



    @Override
    public void showHome() {
        replaceLoadRootFragment(R.id.fl_container, MainFragment.newInstance(),true);
    }
}
