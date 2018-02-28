package com.dhc.library.di.module;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.dhc.library.di.FragmentScope;

import dagger.Module;
import dagger.Provides;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 10:53
 * 描述 ：TODO 请描述该类职责
 */
@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
