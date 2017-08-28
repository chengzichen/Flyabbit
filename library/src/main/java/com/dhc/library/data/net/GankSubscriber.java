package com.dhc.library.data.net;

import android.content.Context;

import com.dhc.library.base.BaseSubscriber;


/**
 * 创建者     邓浩宸
 * 创建时间   2017/8/26 16:26
 * 描述	      ${干货统一处理,自定义的Subscriber}
 */

public class GankSubscriber<T extends ApiResponse> extends BaseSubscriber<T>{
    public GankSubscriber(SubscriberListener mSubscriberOnNextListener, Context aContext) {
        super(mSubscriberOnNextListener, aContext);
    }

    public GankSubscriber(SubscriberListener mSubscriberOnNextListener) {
        super(mSubscriberOnNextListener);
    }

    @Override
    public void onNext(T response) {
            if (mSubscriberOnNextListener != null) {
                if (response!=null&&response instanceof GankApiResponse&&!((GankApiResponse) response).isError()) {
                    mSubscriberOnNextListener.onSuccess(((GankApiResponse) response).getData()
                    );
                }
                else {
                    mSubscriberOnNextListener.onFail("-1", "数据为空");
                }
        }
    }
}
