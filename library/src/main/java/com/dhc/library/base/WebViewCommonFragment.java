package com.dhc.library.base;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.dhc.library.R;
import com.dhc.library.constant.ToolBarOptions;
import com.dhc.library.widget.ProgressWebView;

import java.lang.reflect.Method;

import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 创建者：dhc
 * 时间 ：2017-06-23 上午 10:29
 * 描述 ：TODO 请描述该类职责
 */
public class WebViewCommonFragment extends XDaggerFragment {

    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String ID = "id";
    public static final String TECH = "tech";
    public static final String INJECT_STR = "INJECT_STR";


    /**
     * @param title        页面的标题
     * @param url          URL地址
     * @param access_token 如果没有，可以传入null或者""
     * @param injectStr    给js注入的对象名称，如果没有，可以传入null或者""
     * @return
     */
    public static WebViewCommonFragment newInstance(String title, String url, String id, String tech) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putString(URL, url);
        bundle.putString(TECH, tech);
        bundle.putString(ID, id);
        WebViewCommonFragment fragment = new WebViewCommonFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_web_view_common;
    }

    private Toolbar mToolBar;
    private LinearLayout mLlRoot;
    private String mTitle;
    private String mUrl;
    private ProgressWebView mProgressWebView;
    private String id;
    private String tech;
    private String injectStr;
    private WebViewJS mWebViewJs;

    @Override
    protected void initEventAndData(View view) {

        Bundle bundle = getArguments();
        mTitle = bundle.getString(TITLE);
        mUrl = bundle.getString(URL);
        id = bundle.getString(ID);
        tech = bundle.getString(TECH);
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = mTitle;
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
        getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mProgressWebView.canGoBack()) {
                    mProgressWebView.goBack();
                    return;
                }
                pop();
            }
        });
        mLlRoot = $(R.id.ll_root);
        mProgressWebView = $(R.id.webview_progress);

        WebSettings ws = mProgressWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        if (!TextUtils.isEmpty(injectStr)) {
            mWebViewJs = new WebViewJS(_mActivity, this);
            mProgressWebView.addJavascriptInterface(mWebViewJs, injectStr);
        }

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
                if (url.startsWith("tel:")){
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
                    startActivity(intent);
                } else if(url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                }
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        if (mUrl != null) {
            mProgressWebView.loadUrl(mUrl);
        }

    }

    @Override
    public void initInject(Bundle savedInstanceState) {

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
        mLlRoot.removeView(mProgressWebView);
        mProgressWebView.destroy();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return null;
    }
}
