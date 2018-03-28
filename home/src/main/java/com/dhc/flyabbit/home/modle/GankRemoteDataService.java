package com.dhc.flyabbit.home.modle;

import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.flyabbit.home.presenter.contract.ITopGirlContract;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.utils.rx.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class GankRemoteDataService implements ITopGirlContract.IModle {

    private HttpHelper mHttpHelper;
    @Inject
    public GankRemoteDataService(HttpHelper httpHelper) {
        this.mHttpHelper = httpHelper;
    }


    @Override
    public Flowable<GankApiResponse<List<GankItemBean>>> getRandomGirl() {
        return mHttpHelper.createApi(IGankApi.class).getRandomGirl(4)
                .compose(RxUtil.<GankApiResponse<List<GankItemBean>>>rxSchedulerHelper());
    }

}
