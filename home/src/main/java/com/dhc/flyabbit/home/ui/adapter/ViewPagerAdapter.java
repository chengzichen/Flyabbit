package com.dhc.flyabbit.home.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.dhc.flyabbit.home.ui.GankPageFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<GankPageFragment> mFragments;
    private String[] mTitles;

    public static ViewPagerAdapter newInstance(FragmentManager fm, List<GankPageFragment> fragments, String[] titles) {
        ViewPagerAdapter mFragmentAdapter = new ViewPagerAdapter(fm);
        mFragmentAdapter.mFragments = fragments;
        mFragmentAdapter.mTitles = titles;
        return mFragmentAdapter;
    }
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public void setBgColor(float rate){

    }
}