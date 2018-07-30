package com.dhc.library.base;

import android.content.Context;
import android.util.Log;

import com.dhc.library.data.net.ApiResponse;
import com.dhc.library.data.net.NetError;
import com.dhc.library.framework.ProgressCancelListener;
import com.dhc.library.data.net.SubscriberListener;

import io.reactivex.subscribers.ResourceSubscriber;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/20 12:16
 * 描述 ：用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束是，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class BaseSubscriber<T extends ApiResponse> extends ResourceSubscriber<T> implements ProgressCancelListener {

    private static final String TAG = "BaseSubscriber";
    protected SubscriberListener mSubscriberOnNextListener;
//    private ProgressDialogHandler mHandler;
    Context aContext;

    /**
     * 该构造会出现一个自动弹出和消失的dialog,一般使用与通用情况,特殊情况请自行处理,也可以通过{@link SubscriberListener#isShowLoading()}
     *
     * @param mSubscriberOnNextListener
     * @param aContext
     */
    public BaseSubscriber(SubscriberListener mSubscriberOnNextListener, Context aContext) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
//        mHandler = new ProgressDialogHandler(aContext, this, false);
        this.aContext = aContext;
    }

    /**
     * 使用该构造方法没有LoadingDialog
     *
     * @param mSubscriberOnNextListener
     */
    public BaseSubscriber(SubscriberListener mSubscriberOnNextListener) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mSubscriberOnNextListener != null && mSubscriberOnNextListener.isShowLoading())
//            showProgressDialog();
        onBegin();
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    public void onBegin() {
        Log.i(TAG, "onBegin");
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onBegin();
        }
    }

//    private void showProgressDialog() {
//        if (mHandler != null) {
//            mHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
//        }
//    }

//    private void dismissProgressDialog() {
//        if (mHandler != null) {
//            mHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
//            mHandler = null;
//        }
//    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        Log.i(TAG, "onError:" + e.toString());
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onError(e);
        }
        onComplete();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onComplete() {
        Log.i(TAG, "onCompleted");
        if (mSubscriberOnNextListener != null && mSubscriberOnNextListener.isShowLoading())
//            dismissProgressDialog();
        if (mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onCompleted();
        }
        if (!this.isDisposed()) {
            this.dispose();
        }
    }


    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理,可以根据实际情况再封装
     *
     * @param response 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T response) {
        Log.i(TAG, "onNext");
        if (mSubscriberOnNextListener != null) {
            if (response!=null) {
                mSubscriberOnNextListener.onSuccess(response);
            }
            else {
                mSubscriberOnNextListener.onFail(new NetError("数据为空",NetError.NoDataError));
            }
        }
    }


    @Override
    public void onCancelProgress() {
        if (isDisposed())
            this.dispose();
//        if (mHandler != null)
//            mHandler = null;
    }
}