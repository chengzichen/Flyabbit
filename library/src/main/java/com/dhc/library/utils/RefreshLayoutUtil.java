package com.dhc.library.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dhc.library.utils.sys.ScreenUtil;
import com.dhc.library.widget.CustomLoadMoreView;
import com.dhc.library.widget.KairuHouseHeader;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class RefreshLayoutUtil {

    /**
     * 设置PtrClassicFrameLayout下拉监听和下拉头的布局
     *
     * @param mPtrClassicFrameLayout
     */
    public static void setPtrClassicFrameLayout( Context context,PtrClassicFrameLayout mPtrClassicFrameLayout,PtrHandlerDelegate ptrDefaultHandler) {
       setPtrClassicFrameLayout(context,mPtrClassicFrameLayout,new PtrHandler() {
           @Override
           public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
               return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
           }

           @Override
           public void onRefreshBegin(PtrFrameLayout frame) {
               ptrDefaultHandler.onRefreshBegin(frame);
           }
       });
    }
    /**
     * 设置PtrClassicFrameLayout下拉监听和下拉头的布局
     *
     * @param mPtrClassicFrameLayout
     */
    public static void setPtrClassicFrameLayout( Context context,PtrClassicFrameLayout mPtrClassicFrameLayout,PtrHandler ptrHandler) {
        mPtrClassicFrameLayout.setPtrHandler(ptrHandler);
        // header,添加刷新头部
        final KairuHouseHeader header = new KairuHouseHeader(context);
        header.setPadding(0, ScreenUtil.dip2px(15), 0, 0);
        mPtrClassicFrameLayout.setHeaderView(header);
        mPtrClassicFrameLayout.addPtrUIHandler(header);
    }

    /**
     * 设置adapter代理
     * @param adapter
     * @param loadMoreListener
     * @param mRecyclerView
     */
    public  static  void setAdapterDelegate(BaseQuickAdapter adapter, BaseQuickAdapter.RequestLoadMoreListener loadMoreListener, RecyclerView mRecyclerView){
        setAdapterDelegate(adapter,loadMoreListener,mRecyclerView,0);
    }
    /**
     * 设置adapter代理
     * @param adapter
     * @param loadMoreListener
     * @param mRecyclerView
     */
    public  static  void setAdapterDelegate(BaseQuickAdapter adapter, BaseQuickAdapter.RequestLoadMoreListener loadMoreListener, RecyclerView mRecyclerView,@BaseQuickAdapter.AnimationType int animationType){
        adapter.setOnLoadMoreListener(loadMoreListener, mRecyclerView);
        if (animationType!=0)
        adapter.openLoadAnimation(animationType);
        adapter.setLoadMoreView(new CustomLoadMoreView());
    }


}
