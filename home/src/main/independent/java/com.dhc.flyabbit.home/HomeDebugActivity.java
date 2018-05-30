package com.dhc.flyabbit.home;

import android.os.Bundle;

import com.dhc.flyabbit.home.ui.HomeFragment;
import com.dhc.library.base.XDaggerActivity;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class HomeDebugActivity extends XDaggerActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_debug;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        loadRootFragment(R.id.content, HomeFragment.newInstance());
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }

}
