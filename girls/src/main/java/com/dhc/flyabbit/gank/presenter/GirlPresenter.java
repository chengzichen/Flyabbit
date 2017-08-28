package com.dhc.flyabbit.gank.presenter;


import com.dhc.flyabbit.gank.modle.GirlRemoteDataService;
import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.flyabbit.gank.presenter.contract.IGirlContract;
import com.dhc.library.base.RxPresenter;
import com.dhc.library.data.net.GankApiResponse;
import com.dhc.library.data.net.GankSubscriber;
import com.dhc.library.utils.rx.BaseSubscriberListener;

import java.util.List;

import javax.inject.Inject;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  妹纸
 */

public class GirlPresenter extends RxPresenter<IGirlContract.IView> implements IGirlContract.IPresenter {
    private int currentPage = 1;
    private GirlRemoteDataService mGirlRemoteDataService;

    @Inject
    public GirlPresenter(GirlRemoteDataService girlRemoteDataService) {
        mGirlRemoteDataService = girlRemoteDataService;
    }

    @Override
    public void getGirlData() {
        currentPage = 1;
        mGirlRemoteDataService.getGirlData(currentPage)
                .compose(getV().<GankApiResponse<List<GankItemBean>>>bindLifecycle())
                .subscribe(new GankSubscriber<GankApiResponse<List<GankItemBean>>>(new BaseSubscriberListener<List<GankItemBean>>() {
                    @Override
                    public void onSuccess(List<GankItemBean> response) {
                        getV().showContent(response);
                    }

                    @Override
                    public void onFail(String errorCode, String errorMsg) {
                        super.onFail(errorCode, errorMsg);
                        getV().showError("0",errorMsg);
                    }
                }));
    }

    @Override
    public void getMoreGirlData() {
        mGirlRemoteDataService.getGirlData(currentPage)
                .compose(getV().<GankApiResponse<List<GankItemBean>>>bindLifecycle())
                .subscribe(new GankSubscriber<GankApiResponse<List<GankItemBean>>>(new BaseSubscriberListener<List<GankItemBean>>() {
                    @Override
                    public void onSuccess(List<GankItemBean> response) {
                        getV().showMoreContent(response);
                        ++currentPage;
                    }

                    @Override
                    public void onFail(String errorCode, String errorMsg) {
                        super.onFail(errorCode, errorMsg);
                        getV().showError("0","加载更多数据失败ヽ(≧Д≦)ノ");
                    }
                }));
    }
}
