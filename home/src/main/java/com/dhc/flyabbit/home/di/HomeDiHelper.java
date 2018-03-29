package com.dhc.flyabbit.home.di;


import com.dhc.flyabbit.home.di.component.DaggerHomeActivityComponent;
import com.dhc.flyabbit.home.di.component.DaggerHomeFragmentComponent;
import com.dhc.flyabbit.home.di.component.HomeActivityComponent;
import com.dhc.flyabbit.home.di.component.HomeFragmentComponent;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.di.module.ActivityModule;
import com.dhc.library.di.module.FragmentModule;
import com.dhc.library.utils.AppContext;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/24 8:54
 * 描述	      ${TODO}
 */
public class HomeDiHelper {

        public static HomeActivityComponent getActivityComponent(ActivityModule activityModule) {
            return DaggerHomeActivityComponent.builder()
                    .appComponent(((BaseApplication) AppContext.get()).getAppComponent())
                    .activityModule(activityModule)
                    .build();
        }


    public static HomeFragmentComponent getFragmentComponent(FragmentModule fragmentModule){
            return DaggerHomeFragmentComponent.builder()
                    .appComponent(((BaseApplication)AppContext.get()).getAppComponent())
                    .fragmentModule(fragmentModule)
                    .build();
        }


}
