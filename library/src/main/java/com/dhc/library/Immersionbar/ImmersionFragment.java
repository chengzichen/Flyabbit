package com.dhc.library.Immersionbar;

import com.dhc.library.base.XDaggerFragment;

/**
 * 创建者：邓浩宸
 * 时间 ：2017/5/26 13:50
 * 描述 ：ImmersionFragment沉浸式基类，因为fragment是基于activity之上的，
 * 为了能够在fragment使用沉浸式而fragment之间又相互不影响，必须实现immersionInit方法，
 * 原理是当用户可见才执行沉浸式初始化
 */
public abstract class ImmersionFragment extends XDaggerFragment {
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if ((isVisibleToUser && isResumed())) {
            onResume();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            immersionInit();
        }
    }

    protected abstract void immersionInit();
}
