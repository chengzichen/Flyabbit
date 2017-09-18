package com.dhc.flyabbit.home.presenter;


import com.dhc.flyabbit.home.modle.GankTechRemoteDataService;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.flyabbit.home.presenter.contract.IGankTechContract;
import com.dhc.library.base.XPresenter;
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

public class GankTechPresenter extends XPresenter<IGankTechContract.IView> implements IGankTechContract.IPresenter {
    private int currentPage = 1;
    private GankTechRemoteDataService mGankTechRemoteDataService;

    @Inject
    public GankTechPresenter(GankTechRemoteDataService gankTechRemoteDataService) {
        mGankTechRemoteDataService = gankTechRemoteDataService;
    }

    @Override
    public void getTechList(String tag) {
        currentPage = 1;
        mGankTechRemoteDataService.getTechList(tag,currentPage)
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
    public void getMoreTechList(String tag) {
        mGankTechRemoteDataService.getTechList(tag,currentPage)
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
