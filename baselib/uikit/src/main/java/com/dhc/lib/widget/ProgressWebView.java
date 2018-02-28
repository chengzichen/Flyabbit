package com.dhc.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class ProgressWebView extends WebView {

    private ProgressBar progressBar;
    ScrollInterface web;
    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 10, 0, 0));
        addView(progressBar);
//        setWebViewClient(new MyWebViewClient());
        setWebChromeClient(new WebChromeClient());

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        if (web!=null)
        web.onSChanged(l, t, oldl, oldt);
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //页面加载中进度条的变化
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

    }

    public void setOnCustomScroolChangeListener(ScrollInterface t) {
        this.web = t;
    }
    /**
     * 定义滑动接口
     */
    public interface ScrollInterface {

        public void onSChanged(int l, int t, int oldl, int oldt);
    }
}
