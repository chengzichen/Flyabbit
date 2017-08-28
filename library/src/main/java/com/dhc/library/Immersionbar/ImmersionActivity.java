package com.dhc.library.Immersionbar;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dhc.library.base.XDaggerActivity;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public abstract class ImmersionActivity extends XDaggerActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }
}
