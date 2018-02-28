package com.dhc.flyabbit.gank.modle;

import com.dhc.flyabbit.gank.modle.bean.GankItemBean;
import com.dhc.flyabbit.gank.presenter.contract.IGirlContract;
import com.dhc.library.data.HttpHelper;
import com.dhc.library.data.net.GankApiResponse;
import com.dhc.library.utils.rx.RxUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class GirlRemoteDataService  implements IGirlContract.IModle {

    private HttpHelper mHttpHelper;
    public static final int NUM_OF_PAGE = 20;
    @Inject
    public GirlRemoteDataService(HttpHelper httpHelper) {
        this.mHttpHelper = httpHelper;
    }


    @Override
    public Flowable<GankApiResponse<List<GankItemBean>>> getGirlData(int page) {
        return mHttpHelper.createApi(IGirlApi.class).getGirlList(NUM_OF_PAGE,page)
                .compose(RxUtil.<GankApiResponse<List<GankItemBean>>>rxSchedulerHelper());
    }

}
