package com.dhc.flyabbit.grils;

import android.os.Bundle;

import com.dhc.flyabbit.gank.R;
import com.dhc.flyabbit.gank.ui.GankFragment;
import com.dhc.library.base.XDaggerActivity;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class GirlsDebugActivity extends XDaggerActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_debug;
    }

    @Override
    public void initEventAndData(Bundle savedInstanceState) {
        loadRootFragment(R.id.content, GankFragment.newInstance());
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }

}
