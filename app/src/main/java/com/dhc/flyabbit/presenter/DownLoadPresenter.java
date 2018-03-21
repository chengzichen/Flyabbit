package com.dhc.flyabbit.presenter;


import com.dhc.flyabbit.DownLoadManager;
import com.dhc.flyabbit.modle.DownLoadlRemoteDataService;
import com.dhc.flyabbit.presenter.contract.IDownLoadContract;
import com.dhc.library.base.XPresenter;
import com.dhc.library.data.net.NetError;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;

/**
 * 创建者     邓浩宸
 * 创建时间   2016/12/9 14:17
 * 描述	  妹纸
 */

public class DownLoadPresenter extends XPresenter<IDownLoadContract.IView> implements IDownLoadContract.IPresenter {
    private DownLoadlRemoteDataService mDownLoadlRemoteDataService;

    @Inject
    public DownLoadPresenter(DownLoadlRemoteDataService downLoadlRemoteDataService) {
        mDownLoadlRemoteDataService = downLoadlRemoteDataService;
    }


    @Override
    public void download(String url, final String fileName) {
        mDownLoadlRemoteDataService.download(url)
                .map(new Function<ResponseBody, Boolean>() {
                    @Override
                    public Boolean apply(ResponseBody responseBodyResponse) throws Exception {
                        return DownLoadManager.writeResponseBodyToDisk(getV().getAContext(),
                                responseBodyResponse,fileName);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResourceSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                    getV().showContent(aBoolean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        getV().showError("-1",t.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
