package com.dhc.flyabbit.home.presenter;


import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dhc.businesscomponent.data.net.GankApiResponse;
import com.dhc.businesscomponent.data.net.GankSubscriber;
import com.dhc.businesscomponent.data.net.GankSubscriberListener;
import com.dhc.flyabbit.home.modle.GankRemoteDataService;
import com.dhc.flyabbit.home.modle.bean.GankItemBean;
import com.dhc.flyabbit.home.presenter.contract.ITopGirlContract;
import com.dhc.library.base.XPresenter;
import com.dhc.library.data.net.NetError;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.sys.ScreenUtil;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author 邓浩宸
 * @date 2017/8/26 17:37
 * @description TODO
 */
public class TopGirlPresenter extends XPresenter<ITopGirlContract.IView> implements ITopGirlContract.IPresenter {

    private GankRemoteDataService mZhiHuRemoteDataService;

    @Inject
    public TopGirlPresenter(GankRemoteDataService zhiHuRemoteDataService) {
        this.mZhiHuRemoteDataService = zhiHuRemoteDataService;
    }

    @Override
    public void getRandomGirl() {
        mZhiHuRemoteDataService.getRandomGirl()
                .compose(getV().<GankApiResponse<List<GankItemBean>>>bindLifecycle())
                .subscribe(new GankSubscriber<GankApiResponse<List<GankItemBean>>>(new GankSubscriberListener<List<GankItemBean>>() {
                    @Override
                    public void onSuccess(List<GankItemBean> response) {
                        Flowable.fromIterable(response).map(new Function<GankItemBean, String>() {
                            @Override
                            public String apply(GankItemBean itemBean) throws Exception {
                                Glide.with(AppContext.get().getApplicationContext()).load(itemBean.getUrl()).asBitmap()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .fitCenter().listener(new RequestListener<String, Bitmap>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        return false;
                                    }

                                }).preload(ScreenUtil.getDisplayWidth(), 250);
                                return itemBean.getUrl();
                            }
                        }).toList().observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> strings) throws Exception {
                                getV().showContent(strings);
                            }

                        });

                    }

                    @Override
                    public void onFail(NetError errorMsg) {
                        super.onFail(errorMsg);
                        getV().showError("-1", errorMsg.getMessage());
                    }
                }));
    }
}
