package com.dhc.businesscomponent.data.net;

import android.util.Log;

import com.dhc.library.base.BaseSubscriber;
import com.dhc.library.data.net.ApiResponse;
import com.dhc.library.data.net.NetError;
import com.dhc.library.data.net.SubscriberListener;


/**
 * 创建者     邓浩宸
 * 创建时间   2017/8/26 16:26
 * 描述	      ${业务异常干货统一处理,自定义的Subscriber}
 */

public class GankSubscriber<T extends ApiResponse<D>,D> extends BaseSubscriber<T,D> {
    private static final String TAG =GankSubscriber.class.getSimpleName() ;

    public GankSubscriber(SubscriberListener<D> mSubscriberOnNextListener) {
        super(mSubscriberOnNextListener);
    }

    @Override
    public void onNext(T response) {
        Log.i(TAG,"onNext");
        if (getMSubscriberOnNextListener() != null) {
            if (response != null && response.isSuccess()) {
                getMSubscriberOnNextListener() .onSuccess((D)response.getData());
            } else {
                if (response.checkReLogin())
                    getMSubscriberOnNextListener() .checkReLogin("请先登陆", "请先登陆");
                getMSubscriberOnNextListener() .onFail(new NetError("请先登陆",
                        NetError.Companion.getBusinessError()));
            }
        }
    }
}