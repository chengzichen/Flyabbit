package com.dhc.library.framework;

import android.os.Bundle;
import android.view.View;

/**
 * @creator：denghc(desoce)
 * @updateTime：2018/7/30$ 12:59$
 * @description：  expand Activity&Fragment
 */
public interface ISupportBaseView {

    /**
     * get LayoutResID
     *
     * @return layoutResId
     */
    int getLayoutId();

    /**
     * initView &  initData
     *
     * @param savedInstanceState the data most recently supplied in .
     */
    void initEventAndData(Bundle savedInstanceState);

    /**
     * replace  findViewById
     *
     * @param resId   layout resId
     * @param <T>   View
     * @return    View
     */
     <T extends View> T $(int resId) ;

}
