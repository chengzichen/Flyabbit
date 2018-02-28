package com.dhc.flyabbit.gank.di;

import com.dhc.flyabbit.gank.di.component.DaggerGankActivityComponent;
import com.dhc.flyabbit.gank.di.component.DaggerGankFragmentComponent;
import com.dhc.flyabbit.gank.di.component.GankActivityComponent;
import com.dhc.flyabbit.gank.di.component.GankFragmentComponent;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.di.module.ActivityModule;
import com.dhc.library.di.module.FragmentModule;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/24 8:54
 * 描述	      ${TODO}
 */
public class GankDiHelper {

        public static GankActivityComponent getActivityComponent(ActivityModule activityModule) {
            return DaggerGankActivityComponent.builder()
                    .appComponent(BaseApplication.getAppComponent())
                    .activityModule(activityModule)
                    .build();
        }


    public static GankFragmentComponent getFragmentComponent(FragmentModule fragmentModule){
            return DaggerGankFragmentComponent.builder()
                    .appComponent(BaseApplication.getAppComponent())
                    .fragmentModule(fragmentModule)
                    .build();
        }


}
