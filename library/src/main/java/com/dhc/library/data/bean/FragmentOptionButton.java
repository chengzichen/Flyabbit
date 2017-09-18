package com.dhc.library.data.bean;

import android.content.Context;
import android.view.View;

import com.dhc.library.base.BaseFragment;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public abstract class FragmentOptionButton extends OptionsButton{
    @Override
    public void onClick(Context context, View view, String sessionId) {

    }

    public abstract void onClick(BaseFragment baseFragment, View view, String sessionId);
}
