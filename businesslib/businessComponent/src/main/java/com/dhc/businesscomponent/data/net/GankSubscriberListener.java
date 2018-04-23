package com.dhc.businesscomponent.data.net;

import com.dhc.businesscomponent.R;
import com.dhc.library.utils.AppContext;
import com.dhc.library.utils.rx.BaseSubscriberListener;
import com.dhc.rxbus2.Events;
import com.dhc.rxbus2.RxBus;

import retrofit2.HttpException;

import static com.dhc.businesscomponent.Constants.GO_LOGIN;

/**
 * 创建者     邓浩宸
 * 创建时间   2018/4/23 10:29
 * 描述	        请求异常统一处理回调
 */
public abstract class GankSubscriberListener<T>  extends BaseSubscriberListener<T> {
    //对应HTTP的状态码
    private static final int ERROR = 400;
    private static final int UNAUTHORIZED = 401;//没有权限
    private static final int FORBIDDEN = 403;//没有权限
    private static final int NOT_FOUND = 404;//
    private static final int INTERNAL_SERVER_ERROR = 500;//服务器错误
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;


    @Override
    public void checkReLogin(String errorCode, String errorMsg) {
        super.checkReLogin(errorCode, errorMsg);
        RxBus.getDefault().post(new Events<String>(GO_LOGIN, AppContext.get().getString(R.string.GO_LOGIN)));
    }

    @Override
    public boolean isCheckReLogin(HttpException httpException) {
        return httpException.code() == UNAUTHORIZED;
    }
}
