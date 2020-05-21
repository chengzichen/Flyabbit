package com.dhc.businesscomponent.base;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.dhc.businesscomponent.R;
import com.dhc.lib.widget.BaseContentLayout;
import com.dhc.lib.widget.ProgressWebView;
import com.dhc.lib.widget.util.ToolbarUtil;
import com.dhc.library.framework.IBasePresenter;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.lib.widget.bean.ToolBarOptions;
import com.dhc.library.framework.IBaseView;

import java.lang.reflect.Method;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 创建者：dhc
 * 时间 ：2017-06-23 上午 10:29
 * 描述 ：TODO 请描述该类职责
 */
public class WebViewCommonFragment<T extends IBasePresenter<V>,V extends IBaseView > extends XDaggerFragment<T,
        V> {

    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String ID = "id";
    public static final String TECH = "tech";
    public static final String LOADFIRST = "loadFirst";
    ProgressWebView.ScrollInterface mScrollInterface;

    /**
     * @param title        页面的标题
     * @param url          URL地址
     * @return
     */
    public static WebViewCommonFragment newInstance(String title, String url, String id, String tech, boolean loadFirst) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(URL, url);
        bundle.putString(TECH, tech);
        bundle.putString(ID, id);
        bundle.putBoolean(LOADFIRST, loadFirst);
        WebViewCommonFragment fragment = new WebViewCommonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_web_view_common;
    }

    protected LinearLayout mLlRoot;
    protected String mTitle;
    protected String mUrl;
    protected ProgressWebView mProgressWebView;
    protected BaseContentLayout mBaseContentLayout;
    protected String id;
    protected String tech;
    protected boolean loadFirstBoolean = true;

    @Override
    public void initEventAndData( Bundle savedInstanceState) {
        initView();
        initTitle();
        initWebView();
        if (Build.VERSION.SDK_INT >= 14) {// 4.0 需打开硬件加速
            _mActivity.getWindow().setFlags(0x1000000, 0x1000000);
        }
        load(loadFirstBoolean);
    }

    public void setScrollInterface(ProgressWebView.ScrollInterface scrollInterface) {
        mScrollInterface = scrollInterface;
    }

    private void initWebView() {
        WebSettings ws = mProgressWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);

        ws.setRenderPriority(WebSettings.RenderPriority.HIGH);
        //支持中文，否则页面中中文显示乱码
        ws.setDefaultTextEncodingName("GBK");
        mProgressWebView.clearCache(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            try {
                Method m1 = WebSettings.class.getMethod("setDomStorageEnabled", new Class[]{Boolean.TYPE});
                m1.invoke(ws, Boolean.TRUE);

                Method m2 = WebSettings.class.getMethod("setDatabaseEnabled", new Class[]{Boolean.TYPE});
                m2.invoke(ws, Boolean.TRUE);

                Method m3 = WebSettings.class.getMethod("setDatabasePath", new Class[]{String.class});
                m3.invoke(ws, "/data/data/" + getAContext().getPackageName() + "/databases/");

                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
                m4.invoke(ws, 1024 * 1024 * 8);

                Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
                m5.invoke(ws, "/data/data/" + getAContext().getPackageName() + "/cache/");

                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});

                m6.invoke(ws, Boolean.TRUE);

            } catch (Exception e) {

            }
        }


        mProgressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        if (mScrollInterface != null)
            mProgressWebView.setOnCustomScroolChangeListener(mScrollInterface);
        //        load(loadFirstBoolean);
    }


    private void initTitle() {
        if (mTitle != null && !TextUtils.isEmpty(mTitle)) {
            ToolBarOptions options = new ToolBarOptions();
            options.titleString = mTitle;
            options.isNeedNavigate = true;
            ToolbarUtil toolbarUtil=    new ToolbarUtil();
            toolbarUtil.setToolBar(_mActivity, (Toolbar) $(R.id.toolbar),options,false);
            toolbarUtil.getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mProgressWebView.canGoBack()) {
                        mProgressWebView.goBack();
                        return;
                    }
                    pop();
                }
            });
            $(R.id.toolbar).setVisibility(View.VISIBLE);
        } else {
            $(R.id.toolbar).setVisibility(View.GONE);
        }
    }

    private void initView() {
        mLlRoot = $(R.id.ll_root);
        mProgressWebView = $(R.id.webview_progress);
        mBaseContentLayout = $(R.id.bcl_type_layout);
    }

    public void load(boolean isLoadFirst) {
        if (isLoadFirst) {
            if (mUrl != null) {
                mProgressWebView.loadUrl(mUrl);
            }
        }
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mTitle = bundle.getString(TITLE);
        mUrl = bundle.getString(URL);
        id = bundle.getString(ID);
        tech = bundle.getString(TECH);
        loadFirstBoolean = bundle.getBoolean(LOADFIRST);
    }

    @Override
    public boolean onBackPressedSupport() {
        if (mProgressWebView.canGoBack()) {
            mProgressWebView.goBack();
            return true;
        }
        return super.onBackPressedSupport();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mProgressWebView != null) {
            mProgressWebView.setWebViewClient(null);
            mProgressWebView.setWebChromeClient(null);
            mProgressWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mProgressWebView.clearHistory();
            ((ViewGroup) mProgressWebView.getParent()).removeView(mProgressWebView);
            mProgressWebView.destroy();
            mProgressWebView = null;
        }
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }
}
