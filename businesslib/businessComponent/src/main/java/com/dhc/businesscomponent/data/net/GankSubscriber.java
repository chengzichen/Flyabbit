package com.dhc.businesscomponent.data.net;

import android.content.Context;
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

public class GankSubscriber<T extends ApiResponse> extends BaseSubscriber<T> {
    private static final String TAG =GankSubscriber.class.getSimpleName() ;

    public GankSubscriber(SubscriberListener mSubscriberOnNextListener) {
        super(mSubscriberOnNextListener);
    }

    @Override
    public void onNext(T response) {
        Log.i(TAG,"onNext");
        if (mSubscriberOnNextListener != null) {
            if (response != null && response.isSuccess()) {
                mSubscriberOnNextListener.onSuccess(response.getData());
            } else {
                if (response.checkReLogin())
                    mSubscriberOnNextListener.checkReLogin("请先登陆", "请先登陆");
                mSubscriberOnNextListener.onFail(new NetError("请先登陆", NetError.BusinessError));
            }
        }
    }
}