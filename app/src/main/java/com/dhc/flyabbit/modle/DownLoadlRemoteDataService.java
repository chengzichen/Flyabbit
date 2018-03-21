package com.dhc.flyabbit.modle;

import com.dhc.flyabbit.presenter.contract.IDownLoadContract;
import com.dhc.library.data.HttpHelper;
import javax.inject.Inject;
import io.reactivex.Flowable;
import okhttp3.ResponseBody;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  TODO
 */

public class DownLoadlRemoteDataService implements IDownLoadContract.IModle {

    private HttpHelper mHttpHelper;
    public static final int NUM_OF_PAGE = 20;
    @Inject
    public DownLoadlRemoteDataService(HttpHelper httpHelper) {
        this.mHttpHelper = httpHelper;
    }



    @Override
    public Flowable<ResponseBody> download( String url) {
        return mHttpHelper.createApi(IDownLoadApi.class).download(url);
    }
}
