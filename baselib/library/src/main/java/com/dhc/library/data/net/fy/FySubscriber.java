package com.dhc.library.data.net.fy;

import android.content.Context;

import com.dhc.library.data.net.NetError;
import com.dhc.library.data.net.SubscriberListener;
import com.dhc.library.base.BaseSubscriber;
import com.dhc.library.utils.logger.KLog;


/**
 * 创建者     邓浩宸
 * 创建时间   2017/8/26 16:26
 * 描述	      ${干货统一处理,自定义的Subscriber}
 */

public class FySubscriber<T extends FyResponse> extends BaseSubscriber<T> {
    public FySubscriber(SubscriberListener mSubscriberOnNextListener, Context aContext) {
        super(mSubscriberOnNextListener, aContext);
    }

    public FySubscriber(SubscriberListener mSubscriberOnNextListener) {
        super(mSubscriberOnNextListener);
    }


    @Override
    public void onNext(T response) {
        KLog.d("onNext");
        if (mSubscriberOnNextListener != null) {
            if (response != null
                    && "0".equals(((FyResponse) response).getState())
                    && "ok".equals(((FyResponse) response).getMessage().toLowerCase())) {
                mSubscriberOnNextListener.onSuccess(((FyResponse) response).getData()
                );
            } else {
                if ("请先登陆".equals(((FyResponse) response).error_code))
                    mSubscriberOnNextListener.checkReLogin("请先登陆", "请先登陆");
                mSubscriberOnNextListener.onFail(new NetError(((FyResponse) response).getMessage(), NetError.BusinessError));
            }
        }
    }
}
