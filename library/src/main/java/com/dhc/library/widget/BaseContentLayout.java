package com.dhc.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dhc.library.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * 创建者：yqlee
 * 时间 ：2016-09-07 下午 3:50
 * 描述 ：统一界面管理、包含状态：showContent、Loading、empty、Error、NoNet
 */
public class BaseContentLayout extends FrameLayout {

    private Context context;
    private ViewMode currentView = ViewMode.CONTENT;
    public Map<ViewMode, View> viewMap = new HashMap<ViewMode, View>();

    private OnRetryCallback mOnRetryCallback;

    public static enum ViewMode {
        CONTENT, LOADING, ERROR, EMPTY, NONET
    }

    public BaseContentLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    public BaseContentLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public BaseContentLayout(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public void init() {
        addLayerView(ViewMode.NONET,
                inflate(context, R.layout.layout_nonet_view, null));
        addLayerView(ViewMode.ERROR,
                inflate(context, R.layout.layout_error_view, null));
        addLayerView(ViewMode.EMPTY,
                inflate(context, R.layout.layout_empty_view, null));
        addLayerView(ViewMode.LOADING,
                inflate(context, R.layout.layout_loading_view, null));
        viewMap.get(ViewMode.ERROR).setOnClickListener(defultClick);
        viewMap.get(ViewMode.NONET).setOnClickListener(defultClick);
        viewMap.get(ViewMode.EMPTY).setOnClickListener(defultClick);

    }

    public BaseContentLayout addLayerView(ViewMode key, View view) {
        viewMap.put(key, view);
        return this;
    }

    public void setOnRetryCallback(OnRetryCallback mOnRetryCallback) {
        this.mOnRetryCallback = mOnRetryCallback;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int c = getChildCount();
        if (c > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ViewGroup) {
                viewMap.put(ViewMode.CONTENT, childView);
            } else {
            }
        } else {

        }

    }

    /**
     * 显示页面内容
     */
    public void showContent() {
        show(ViewMode.CONTENT);
    }

    /**
     * 显示无网络页面
     */
    public void showNonet() {
        show(ViewMode.NONET);
    }

    /**
     * 显示错误页面
     */
    public void showError() {
        show(ViewMode.ERROR);
    }

    public void showError(String tip) {
        setTextByViewMode(ViewMode.ERROR, tip);
        show(ViewMode.ERROR);
    }

    public void showError(View v) {
        FrameLayout mFrameLayout = (FrameLayout) viewMap.get(ViewMode.ERROR);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(v);
        show(ViewMode.ERROR);
    }

    /**
     * 显示无数据页面
     */
    public void showEmpty() {
        show(ViewMode.EMPTY);
    }

    public void showEmpty(String tip) {
        setTextByViewMode(ViewMode.EMPTY, tip);
        show(ViewMode.EMPTY);
    }

    public void showEmpty(View v) {
        FrameLayout mFrameLayout = (FrameLayout) viewMap.get(ViewMode.EMPTY);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(v);
        show(ViewMode.EMPTY);
    }

    /**
     * 显示数据加载中页面
     */
    public void showLoading() {
        show(ViewMode.LOADING);
    }

    public void showViewWithText(ViewMode m, String tip) {
        show(m);
        setTextByViewMode(m, tip);
    }

    public void showViewByMode(ViewMode key) {
        show(key);
    }

    private void setTextByViewMode(ViewMode m, String tip) {
        TextView tv_tip = (TextView) viewMap.get(m).findViewById(R.id.tip);
        if (tip != null)
            tv_tip.setText(tip);
    }

    public void showContentWithLoading() {
        View view = viewMap.get(ViewMode.LOADING);
        View contentView = viewMap.get(ViewMode.CONTENT);

        if (!isAdded(view)) {
            addView(view);
        }
        hideView(currentView);
        contentView.setVisibility(View.VISIBLE);
        view.setVisibility(View.VISIBLE);
        currentView = ViewMode.LOADING;
    }

    public void hideView(ViewMode key) {
        View v_hide = viewMap.get(key);
        v_hide.setVisibility(View.GONE);
    }

    public void showView(ViewMode key) {
        View v_shown = viewMap.get(key);
        v_shown.setVisibility(View.VISIBLE);
    }

    private void show(ViewMode key) {
        View view = viewMap.get(key);
        if (!isAdded(view)) {
            addView(view);
        }
        Set<Map.Entry<ViewMode, View>> entries = viewMap.entrySet();
        for (Map.Entry mEntry : entries) {
            View v = (View) mEntry.getValue();
            v.setVisibility(mEntry.getKey().toString().equals(key.name()) ? VISIBLE : GONE);
        }
        currentView = key;
    }

    public boolean isAdded(View view) {
        boolean isAdded = false;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if (child == view) {
                isAdded = true;
                break;
            } else {
            }
        }
        return isAdded;
    }

    public ViewMode getShownViewMode() {
        return currentView;
    }

    public OnClickListener defultClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (mOnRetryCallback != null) {
                showLoading();
                mOnRetryCallback.OnRetry();
            }
        }
    };

    public void setDefultClick(OnClickListener defultClick) {
        this.defultClick = defultClick;
    }

    /**
     * 页面无法获取到数据时的回调接口
     */
    public interface OnRetryCallback {
        /**
         * 重新加载页面的回调函数
         */
        void OnRetry();
    }

}
