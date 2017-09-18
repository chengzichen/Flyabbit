package com.dhc.library.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dhc.library.R;
import com.dhc.library.data.bean.ToolBarOptions;
import com.dhc.library.widget.ProgressWebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class WebViewCommonActivity extends XDaggerActivity {

    private ProgressWebView mProgressWebView;

    private String mUrl;

    public static final String TAG_URL = "TAG_URL";
    public static final String TITLE_INTENT  ="TITLE_INTENT";

    public static void openThis(BaseActivity mActivity, String urlStr,String title_intent) {
        Intent intent = new Intent(mActivity, WebViewCommonActivity.class);
        intent.putExtra(TAG_URL, urlStr);
        mActivity.startActivity(intent);
        intent.putExtra(TITLE_INTENT,title_intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_web_view_common;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {
        ToolBarOptions options = new ToolBarOptions();
        options.titleString = getIntent().getStringExtra(TITLE_INTENT);
        options.isNeedNavigate = true;
        setToolBar(R.id.toolbar, options);
    }

    @Override
    public void initInject(Bundle savedInstanceState) {

        mUrl = getIntent().getStringExtra(TAG_URL);

        mProgressWebView = (ProgressWebView) findViewById(R.id.webview_progress);
        WebSettings ws = mProgressWebView.getSettings();
        ws.setJavaScriptEnabled(true);
        //Don't use the cache, layer_load from the network.不设置缓存,暂时
        ws.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //ws.setAppCachePath(SystemUtility.getAppCachePath() + "web");// 设置应用缓存的路径
        //提高渲染优先级
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
                m3.invoke(ws, "/data/data/" + getPackageName() + "/databases/");

                Method m4 = WebSettings.class.getMethod("setAppCacheMaxSize", new Class[]{Long.TYPE});
                m4.invoke(ws, 1024 * 1024 * 8);

                Method m5 = WebSettings.class.getMethod("setAppCachePath", new Class[]{String.class});
                m5.invoke(ws, "/data/data/" + getPackageName() + "/cache/");

                Method m6 = WebSettings.class.getMethod("setAppCacheEnabled", new Class[]{Boolean.TYPE});
                m6.invoke(ws, Boolean.TRUE);

            } catch (NoSuchMethodException e) {

            } catch (InvocationTargetException e) {

            } catch (IllegalAccessException e) {

            }
        }

        mProgressWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (!TextUtils.isEmpty(mUrl)) {
            mProgressWebView.loadUrl(mUrl);
        }
    }
    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(mProgressWebView.canGoBack()){
                mProgressWebView.goBack();//返回上一页面
                return true;
            }else{
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
