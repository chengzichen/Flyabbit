package com.dhc.library.di.module;

import android.app.Activity;

import com.dhc.library.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:52
 * 描述 ：TODO 请描述该类职责
 */
@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
