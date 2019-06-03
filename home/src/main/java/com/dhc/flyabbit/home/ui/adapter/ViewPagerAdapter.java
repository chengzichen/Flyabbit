package com.dhc.flyabbit.home.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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

}