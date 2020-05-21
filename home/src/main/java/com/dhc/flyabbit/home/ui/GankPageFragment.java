package com.dhc.flyabbit.home.ui;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.loadmore.SimpleLoadMoreView;
import com.dhc.flyabbit.home.R;
import com.dhc.flyabbit.home.di.HomeDiHelper;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.flyabbit.home.presenter.GankTechPresenter;
import com.dhc.flyabbit.home.presenter.contract.IGankTechContract;
import com.dhc.flyabbit.home.ui.adapter.GankTechAdapter;
import com.dhc.library.base.BaseActivity;
import com.dhc.businesscomponent.base.WebViewCommonFragment;
import com.dhc.library.base.XDaggerFragment;
import com.dhc.lib.widget.util.ToastUtil;

import java.util.List;


public class GankPageFragment extends XDaggerFragment<GankTechPresenter,IGankTechContract.IView> implements IGankTechContract.IView, BaseQuickAdapter.RequestLoadMoreListener {
    public static final String TECH_TAG = "tag";
    private String mTag;
    private RecyclerView lv;
    private GankTechAdapter mGankTechAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public static GankPageFragment newInstance(String tag) {
        Bundle args = new Bundle();
        args.putString(TECH_TAG, tag);
        GankPageFragment pageFragment = new GankPageFragment();
        pageFragment.setArguments(args);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getString(TECH_TAG);
    }

    public void setBgColor(int color) {
        lv.setBackgroundColor(color);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_page;
    }

    @Override
    public void initEventAndData( Bundle savedInstanceState) {
        lv = (RecyclerView) getMRootView().findViewById(R.id.rv_daily_list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) getMRootView().findViewById(R.id.swipe_refresh);
        // 创建一个线性布局管理器

        mPresenter.getTechList(mTag);
//        mPresenter. psotTest("", Result.AppResult.newBuilder().setCode(1).setData
//                ("232132123121213123").setMessage("21213212").build());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // 设置布局管理器
        lv.setLayoutManager(layoutManager);

        mGankTechAdapter = new GankTechAdapter(null, mTag);
        mGankTechAdapter.setOnLoadMoreListener(this, lv);
        mGankTechAdapter.setLoadMoreView(new SimpleLoadMoreView());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mPresenter.getTechList(mTag);
        }
    });
        mGankTechAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            List<GankItemBean> gankItemBeen = adapter.getData();
            ((BaseActivity) _mActivity).start(WebViewCommonFragment.newInstance(gankItemBeen.get(position).getDesc(),
                    gankItemBeen.get(position).getUrl(), gankItemBeen.get(position).get_id(),
                    mTag,true));
        }
    });
        lv.setAdapter(mGankTechAdapter);
}

    @Override
    public void initInject(Bundle savedInstanceState) {
        HomeDiHelper.getFragmentComponent(getFragmentModule()).inject(this);
    }

    @Override
    public void showContent(List<GankItemBean> list) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
        }
        mGankTechAdapter.setNewData(list);
    }

    @Override
    public void showMoreContent(List<GankItemBean> list) {
        mGankTechAdapter.addData(list);
        mGankTechAdapter.loadMoreComplete();
    }

    @Override
    public void showError(String code, String msg) {
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            if (mGankTechAdapter != null)
                mGankTechAdapter.loadMoreFail();
        }
        ToastUtil.shortShow(_mActivity, msg);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getMoreTechList(mTag);
    }
}
