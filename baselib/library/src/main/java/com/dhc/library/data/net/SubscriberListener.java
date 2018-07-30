package com.dhc.library.data.net;

import retrofit2.HttpException;

/**
 * @creator:denghc(desoce)
 * @updateTime:2018/7/30 13:50
 * @description:
 */
public abstract class SubscriberListener<T> {
    /**
     * 成功
     * @param response
     */
    public abstract void onSuccess(T response);

    /**
     * 最终错误处理
     * @param error
     */
    public abstract void onFail(NetError error);

    /**
     * 抛出异常,还是在onFail中处理
     * @param e
     */
    public abstract void onError(Throwable e);

    /**
     * end
     */
    public void onCompleted() {
    }

    /**
     * begin
     */
    public void onBegin() {
    }

    /**
     * 是否显示dialog
     * @return
     */
    public boolean isShowLoading(){
        return true;
    }

    /**
     * 是否需要重新登录
     * @return
     * @param httpException
     */
    public boolean isCheckReLogin(HttpException httpException) {
        return false;
    }

    /**
     * 重新登录处理
     * @param errorCode
     * @param errorMsg
     */
    public abstract void checkReLogin(String errorCode,String errorMsg);
}
