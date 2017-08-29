package com.dhc.flyabbit.my.debug;

import android.os.Bundle;

import com.dhc.flyabbit.my.MyFragment;
import com.dhc.flyabbit.my.R;
import com.dhc.library.base.XDaggerActivity;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class MyDebugActivity extends XDaggerActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_debug;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        loadRootFragment(R.id.content, new MyFragment());
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

    }

}
