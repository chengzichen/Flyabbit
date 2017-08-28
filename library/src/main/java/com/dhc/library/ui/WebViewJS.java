package com.dhc.library.ui;

import android.content.Context;
import android.webkit.JavascriptInterface;

/**
 * 创建者：dhc
 * 时间 ：2017-06-28 下午 4:09
 * 描述 ：给Js注入对象的方法，js调用的方法在这个类里面写
 */
public class WebViewJS {

    private Context mContext;
    private WebViewCommonFragment mFragment;

    public WebViewJS(Context context, WebViewCommonFragment fragment) {
        mContext = context;
        mFragment = fragment;
    }

    /**
     * 救助险确认书 回调方法，js调用，专用
     */
    @JavascriptInterface
    public void toHome() {
    }

    /***
     * 登录
     **/
    @JavascriptInterface
    public void toLogin() {
    }

}
