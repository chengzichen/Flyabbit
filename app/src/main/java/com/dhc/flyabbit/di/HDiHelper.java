package com.dhc.flyabbit.di;

import com.dhc.flyabbit.di.component.DaggerHActivityComponent;
import com.dhc.flyabbit.di.component.DaggerHFragmentComponent;
import com.dhc.flyabbit.di.component.HActivityComponent;
import com.dhc.flyabbit.di.component.HFragmentComponent;
import com.dhc.library.base.BaseApplication;
import com.dhc.library.di.module.ActivityModule;
import com.dhc.library.di.module.FragmentModule;

/**
 * 创建者     邓浩宸
 * 创建时间   2017/3/24 8:54
 * 描述	      ${TODO}
 */
public class HDiHelper {

        public static HActivityComponent getActivityComponent(ActivityModule activityModule) {
            return DaggerHActivityComponent.builder()
                    .appComponent(BaseApplication.getAppComponent())
                    .activityModule(activityModule)
                    .build();
        }


    public static HFragmentComponent getFragmentComponent(FragmentModule fragmentModule){
            return DaggerHFragmentComponent.builder()
                    .appComponent(BaseApplication.getAppComponent())
                    .fragmentModule(fragmentModule)
                    .build();
        }


}
