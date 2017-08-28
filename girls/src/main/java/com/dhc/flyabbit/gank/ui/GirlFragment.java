package com.dhc.flyabbit.gank.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dhc.flyabbit.gank.R;
import com.dhc.flyabbit.gank.di.GankDiHelper;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.flyabbit.gank.presenter.GirlPresenter;
import com.dhc.flyabbit.gank.presenter.contract.IGirlContract;
import com.dhc.flyabbit.gank.ui.adapter.GirlAdapter;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.library.utils.ToastUtil;
import com.dhc.library.widget.CustomLoadMoreView;

import java.util.ArrayList;
import java.util.List;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/3/21 9:11
 * 描述 ：
 */
public class GirlFragment extends XDaggerFragment<GirlPresenter> implements IGirlContract.IView, BaseQuickAdapter.RequestLoadMoreListener {

    RecyclerView rvGirlContent;
    SwipeRefreshLayout swipeRefresh;

    private static final int SPAN_COUNT = 2;

    GirlAdapter mAdapter;
    List<GankItemBean> mList;
    StaggeredGridLayoutManager mStaggeredGridLayoutManager;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_girl;
    }

    @Override
    protected void initEventAndData(View view) {
        initView();

        mList = new ArrayList<>();
        mAdapter = new GirlAdapter(mList);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mStaggeredGridLayoutManager.invalidateSpanAssignments();
        rvGirlContent.setLayoutManager(mStaggeredGridLayoutManager);
        rvGirlContent.setAdapter(mAdapter);
        mAdapter.setOnLoadMoreListener(this, rvGirlContent);
        mAdapter.setLoadMoreView(new CustomLoadMoreView());
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getGirlData();
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //// TODO: 2017/8/28 查看图片
            }
        });
        mPresenter.getGirlData();
    }

    private void initView() {
        rvGirlContent = $(R.id.rv_girl_content);
        swipeRefresh = $(R.id.swipe_refresh);
    }


    @Override
    public void showContent(List<GankItemBean> list) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
        }
        mAdapter.setNewData(list);
    }

    @Override
    public void showMoreContent(List<GankItemBean> list) {
        mAdapter.addData(list);
        mAdapter.loadMoreComplete();
    }

    @Override
    public void initInject(Bundle savedInstanceState) {
        GankDiHelper.getFragmentComponent(getFragmentModule()).inject(this);
    }

    @Override
    public void showError(String code, String msg) {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        } else {
        }
        ToastUtil.shortShow(_mActivity, msg);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreGirlData();
    }
}
